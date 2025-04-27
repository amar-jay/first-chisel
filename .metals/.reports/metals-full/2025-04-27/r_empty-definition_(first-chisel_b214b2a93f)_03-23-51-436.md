error id: address
file://<WORKSPACE>/mini-gpu/dispatch.scala
empty definition using pc, found symbol in pc: 
semanticdb not found

found definition using fallback; symbol address
offset: 2433
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

   private val channelState = RegInit(VecInit.fill(numChannels){ChannelStates.IDLE})
	val currentConsumer = Vec(numChannels, UInt(log2Ceil(numConsumers).W))
	val channelServingConsumer = UInt(numChannels.W) // Equivalent to reg [NUM_CONSUMERS-1:0] channel_serving_consumer

//   private val current_consumer = RegInit(Vec())
  withReset (reset.asBool) {
	// clear the channel state
	 for (i <- 0 until numChannels) {
		channels(i).read.valid := false.B
		channels(i).read.bits.addres@@s := 0.U
		channels.

		channels(i).write.valid := true.B
		channels(i).write.bits.address := 0.U
		channels(i).write.bits.data := 0.U
	 }

	 // clear the consumer ready signals
	 for (i <- 0 until numConsumers) {
		io.consumers(i).read.ready := false.B
		io.consumers(i).read.bits.data := 0.U
		io.consumers(i).write.ready := false.B
	 }

	 // clear the current consumer
	 channelState.foreach(_ := ChannelStates.IDLE)
	 currentConsumer.foreach(_ := 0.U)
	 channelServingConsumer := 0.U
  }

  // hardware loop through the channels and check thier states.
  for (i <- 0 until numChannels) {
	// check the channel state
	channelState(i) match {
	  case ChannelStates.IDLE =>
		// if the channel is idle, check if there is a consumer ready to read
		for (j <- 0 until numConsumers) {
		  when(io.consumers(j).read.valid) {
			channelState(i) := ChannelStates.RELAY_READ
			currentConsumer(i) := j.U
			io.consumers(j).read.ready := true.B
		  }
		}
	  case ChannelStates.RELAY_READ =>
		// if the channel is relaying a read request, check if the channel is ready to read
		when(io.channels(i).read.ready) {
		  io.channels(i).read.bits.address := io.consumers(currentConsumer(i)).read.bits.address
		  io.channels(i).read.valid := true.B
		  channelState(i) := ChannelStates.READING
		}
	  case ChannelStates.READING =>
		// if the channel is reading, check if the read data is valid
		when(io.channels(i).read.valid) {
		  io.consumers(currentConsumer(i)).read.bits.data := io.channels(i).read.bits.data
		  io.consumers(currentConsumer(i)).read.valid := true.B
		  channelState(i) := ChannelStates.IDLE
		}
	  case _ => // do nothing
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