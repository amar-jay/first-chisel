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

// if X is an input, ready is an output, and valid and input
// but if X is an output, ready is an output and 

object ChannelStates extends ChiselEnum {
	val IDLE, READING, WRITING, RELAY_READ, RELAY_WRITE = Value
}

class ConsumerIO(dataWidth:Int, addrWidth: Int) extends Bundle {
	val read = Flipped(Decoupled(new Bundle {
		val address = Input(UInt(addrWidth.W))
		val data = Output(UInt(dataWidth.W))
	}))

	val write = Flipped(Decoupled(new Bundle {
		val address = Input(UInt(addrWidth.W))
		val data = Input(UInt(dataWidth.W))
	}))
}

class ChannelIO(dataWidth:Int, addrWidth: Int) extends Bundle {
	val read = Decoupled(new Bundle {
		val address = Output(UInt(addrWidth.W))
		val data = Input(UInt(dataWidth.W))
	})

	val write = Valid(new Bundle {
		val address = Output(UInt(addrWidth.W))
		val data = Output(UInt(dataWidth.W))
	})
}

/***
 * The GPU contains many cores. Interface of b/n core and controller is consumer
 * Interface between memory and controller is channelschannels
 * the consumer is based on number of cores
 * the channel is based on the bandwidth of memory
 */
class Dispatcher(
	numConsumers: Int, // number of cores
	numChannels: Int, // number of memory channels
	dataWidth:Int,
	addrWidth: Int,
	) extends Module {
  val io = IO(new Bundle {
    val consumers = Vec(numConsumers, new ConsumerIO(addrWidth, dataWidth))
    val channels = Vec(numChannels, new ChannelIO(addrWidth, dataWidth))
  })

  private val channelState = RegInit(VecInit(Seq.fill(numChannels){ChannelStates.IDLE}))

  for (i <- 0 until numChannels) {
	 io.channels(i).read.bits.address := 0.U
	 io.channels(i).read.bits.data := 0.U
	 io.channels(i).write.bits.address := 0.U
	 io.channels(i).write.bits.data := 0.U
	 io.channels(i).read.valid := false.B
	 io.channels(i).write.valid := true.B 
  }

  for (i <- 0 until numConsumers) {
	 io.consumers(i).read.ready := false.B
	 io.consumers(i).write.ready := false.B
  }
  when(io.consumers(0).read.valid) {
	 io.consumers(0).read.ready := true.B
	 io.consumers(0).write.ready := true.B
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