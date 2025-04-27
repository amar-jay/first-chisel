error id: file://<WORKSPACE>/mini-gpu/dispatch.scala:77
file://<WORKSPACE>/mini-gpu/dispatch.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 2727
uri: file://<WORKSPACE>/mini-gpu/dispatch.scala
text:
```scala
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

class ConsumerIO(dataWidth: Int, addrWidth: Int) extends Bundle {
  val read = new Bundle {
    val ready = Output(Bool())
    val valid = Input(Bool())
    val bits = new Bundle {
      val address = Input(UInt(addrWidth.W))
      val data = Output(UInt(dataWidth.W))
    }
  }
  val write = Flipped(Decoupled(new Bundle {
    val address = UInt(addrWidth.W)
    val data = UInt(dataWidth.W)
  }))
}

/***
 * The GPU contains many cores. Interface of b/n core and controller is consumer
 * Interface between memory and controller is channelschannels
 * the consumer is based on number of cores
 * the channel is based on the bandwidth of memory
 */

class ChannelIO(dataWidth: Int, addrWidth: Int) extends Bundle {
  val read = new Bundle {
    val ready = Input(Bool())
    val valid = Output(Bool())
    val bits = new Bundle {
      val address = Output(UInt(addrWidth.W))
      val data = Input(UInt(dataWidth.W))
    }
  }
  val write = Decoupled(new Bundle {
    val address = UInt(addrWidth.W)
    val data = UInt(dataWidth.W)
  })
}

class Dispatcher(
    numConsumers: Int,
    numChannels: Int,
    dataWidth: Int,
    addrWidth: Int
) extends Module {
  val io = IO(new Bundle {
    val consumers = Vec(numConsumers, new ConsumerIO(dataWidth, addrWidth))
    val channels = Vec(numChannels, new ChannelIO(dataWidth, addrWidth))
  })

  val channelState = RegInit(VecInit(Seq.fill(numChannels){ChannelStates.IDLE}))
  val currentConsumer = RegInit(VecInit(Seq.fill(numChannels)(0.U(log2Ceil(numConsumers).W))))
  val readAddressReg = RegInit(VecInit(Seq.fill(numChannels)(0.U(addrWidth.W))))
  val writeAddressReg = RegInit(VecInit(Seq.fill(numChannels)(0.U(addrWidth.W))))
  val writeDataReg = RegInit(VecInit(Seq.fill(numChannels)(0.U(dataWidth.W))))

  // Default values for consumer and channel interfaces
  io.consumers.foreach { consumer =>
    consumer.read.ready := false.B
    consumer.read.bits.data := DontCare
    consumer.write.ready := false@@.B
  }

  io.channels.foreach { channel =>
    channel.read.valid := false.B
    channel.read.bits.address := 0.U
    channel.write.valid := false.B
    channel.write.bits.address := 0.U
    channel.write.bits.data := 0.U
  }

  for (i <- 0 until numChannels) {
    switch(channelState(i)) {
      is(ChannelStates.IDLE) {
        var requestFound = false.B
        for (j <- 0 until numConsumers) {
          // Check for read request first
          when(!requestFound && io.consumers(j).read.valid) {
            requestFound := true.B
            currentConsumer(i) := j.U
            readAddressReg(i) := io.consumers(j).read.bits.address
            io.consumers(j).read.valid := true.B
            channelState(i) := ChannelStates.RELAY_READ
          // If no read request, check for write
          }.elsewhen(!requestFound && io.consumers(j).write.valid) {
            requestFound := true.B
            currentConsumer(i) := j.U
            writeAddressReg(i) := io.consumers(j).write.bits.address
            writeDataReg(i) := io.consumers(j).write.bits.data
            io.consumers(j).write.ready := true.B
            channelState(i) := ChannelStates.RELAY_WRITE
          }
        }
      }
      is(ChannelStates.RELAY_READ) {
        io.channels(i).read.valid := true.B
        io.channels(i).read.bits.address := readAddressReg(i)
        when(io.channels(i).read.ready) {
          channelState(i) := ChannelStates.READING
        }
      }
      is(ChannelStates.READING) {
        when(io.channels(i).read.valid) {
          io.consumers(currentConsumer(i)).read.bits.data := io.channels(i).read.bits.data
          channelState(i) := ChannelStates.IDLE
        }
      }
      is(ChannelStates.RELAY_WRITE) {
        io.channels(i).write.valid := true.B
        io.channels(i).write.bits.address := writeAddressReg(i)
        io.channels(i).write.bits.data := writeDataReg(i)
        when(io.channels(i).write.ready) {
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
```


#### Short summary: 

empty definition using pc, found symbol in pc: 