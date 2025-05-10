//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using dep "edu.berkeley.cs::chiseltest:6.0.0"  // ✅ Chisel test lib
//> using plugin "org.chipsalliance:::chisel-plugin:6.7.0"
//> using options "-unchecked","-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations"

import chisel3._
import chisel3.util._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import _root_.circt.stage.ChiselStage
import dataclass.data

// if X is an input, ready is an output, and valid and input
// but if X is an output, ready is an output and 
object ChannelStates extends ChiselEnum {
  val IDLE, READING, WRITING, RELAY_READ, RELAY_WRITE = Value
}

class ConsumerIO(numConsumers:Int, dataWidth: Int, addrWidth: Int) extends Bundle {
  val read_ = new Bundle {
	 val ready = Output(Vec(numConsumers, Bool()))
	 val valid = Input(Vec(numConsumers, Bool()))
	 val address = Input(Vec(numConsumers, UInt(addrWidth.W)))
	 val data = Output(Vec(numConsumers, UInt(dataWidth.W)))
  }

  val write_ = new Bundle {
	 val ready = Output(Vec(numConsumers, Bool()))
	 val valid = Input(Vec(numConsumers, Bool()))
	 val address = Input(Vec(numConsumers, UInt(addrWidth.W)))
	 val data = Input(Vec(numConsumers, UInt(dataWidth.W)))
  }

}

/***
 * The GPU contains many cores. Interface of b/n core and controller is consumer
 * Interface between memory and controller is channelschannels
 * the consumer is based on number of cores
 * the channel is based on the bandwidth of memory
 */

class ChannelIO(numChannels:Int, dataWidth: Int, addrWidth: Int) extends Bundle {

  val read_ = new Bundle {
	val ready = Input(Vec(numChannels, Bool()))
  	val valid = Output(Vec(numChannels, Bool())) // use a much more efficient method
	val address = Output(Vec(numChannels, UInt(addrWidth.W)))
	val data = Input(Vec(numChannels, UInt(dataWidth.W)))
  }

  val write_ = new Bundle {
	val ready = Input(Vec(numChannels, Bool()))
  	val valid = Output(Vec(numChannels, Bool()))
	val address = Output(Vec(numChannels, UInt(addrWidth.W)))
	val data = Output(Vec(numChannels, UInt(dataWidth.W)))
  }
}

class Dispatcher(
    numConsumers: Int,
    numChannels: Int,
    dataWidth: Int,
    addrWidth: Int
) extends Module {
  val io = IO(new Bundle {
    val consumers = new ConsumerIO(numConsumers, dataWidth, addrWidth)
    val channels =  new ChannelIO(numChannels, dataWidth, addrWidth)
  })

  val channelState = RegInit(VecInit(Seq.fill(numChannels){ChannelStates.IDLE}))
  val currentConsumer = RegInit(VecInit(Seq.fill(numChannels)(0.U(log2Ceil(numConsumers).W))))
  val readAddressReg = RegInit(VecInit(Seq.fill(numChannels)(0.U(addrWidth.W))))
  val writeAddressReg = RegInit(VecInit(Seq.fill(numChannels)(0.U(addrWidth.W))))
  val writeDataReg = RegInit(VecInit(Seq.fill(numChannels)(0.U(dataWidth.W))))

  // Default values for consumer and channel interfaces
  for (i <- 0 until numConsumers) {
	io.consumers.read_.ready(i) := false.B
	io.consumers.write_.ready(i) := false.B
	io.consumers.read_.data(i) := DontCare
  }
  for (i <- 0 until numChannels) {
	 io.channels.read_.valid(i) := false.B
	 io.channels.read_.address(i) := DontCare
	 io.channels.write_.valid(i) := false.B
	 io.channels.write_.address(i) := 0.U
	 io.channels.write_.data(i) := 0.U
  }

  var requestFound = Reg(Bool())
  for (i <- 0 until numChannels) {
    switch(channelState(i)) {
      is(ChannelStates.IDLE) {
        requestFound := false.B
        for (j <- 0 until numConsumers) {
          // Check for read request first
          when(!requestFound && io.consumers.read_.valid(j)) {
            requestFound := true.B
            currentConsumer(i) := j.U
            readAddressReg(i) := io.consumers.read_.address(j)
            io.consumers.read_.ready(j) := true.B
            channelState(i) := ChannelStates.RELAY_READ
          // If no read request, check for write
          }.elsewhen(!requestFound && io.consumers.write_.valid(j)) {
            requestFound := true.B
            currentConsumer(i) := j.U
            writeAddressReg(i) := io.consumers.write_.address(j)
            writeDataReg(i) := io.consumers.write_.data(j)
            io.consumers.write_.ready(j) := true.B
            channelState(i) := ChannelStates.RELAY_WRITE
          }
        }
      }
      is(ChannelStates.RELAY_READ) {
        io.channels.read_.valid(i) := true.B
        io.channels.read_.address(i) := readAddressReg(i)
        when(io.channels.read_.ready(i)) {
          channelState(i) := ChannelStates.READING
        }
      }
      is(ChannelStates.READING) {
        when(io.channels.read_.valid(i)) {
          io.consumers.read_.data(currentConsumer(i)) := io.channels.read_.data(i)
          channelState(i) := ChannelStates.IDLE
        }
      }
      is(ChannelStates.RELAY_WRITE) {
        io.channels.write_.valid(i) := true.B
        io.channels.write_.address(i) := writeAddressReg(i)
        io.channels.write_.data(i) := writeDataReg(i)
        when(io.channels.write_.ready(i)) {
          channelState(i) := ChannelStates.IDLE
        }
      }
    }
  }
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
		gen = new Dispatcher(4, 2, 8, 32), // 4 cores, 2 channels, 8 bit data, 32 bit address
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}