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
// if write is an input, ready is an output, and valid and input
// but if write is an output, ready is an output and 

class CSR(bitWidth: Int) extends Module {
	val io = IO(new Bundle {
		val reset = Input(Bool())
		val write = Flipped(Decoupled(UInt(bitWidth.W)))
		val out = Output(UInt(bitWidth.W)) // read output
	})

	val reg = RegInit(0.U(bitWidth.W))
	io.write.ready := true.B
	io.out := reg

	when (io.reset) {
		reg := 0.U
	} .elsewhen(io.write.valid) {
		reg := io.write.bits
	}
}




object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
		gen = new CSR(8),
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}