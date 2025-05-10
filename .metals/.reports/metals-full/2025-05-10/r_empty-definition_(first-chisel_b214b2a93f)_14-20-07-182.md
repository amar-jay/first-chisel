error id: ALU_SLT
file://<WORKSPACE>/riscv/alu.scala
empty definition using pc, found symbol in pc: 
semanticdb not found

found definition using fallback; symbol ALU_SLT
offset: 1257
uri: file://<WORKSPACE>/riscv/alu.scala
text:
```scala
//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using dep "edu.berkeley.cs::chiseltest:6.0.0"  // ✅ Chisel test lib
//> using plugin "org.chipsalliance:::chisel-plugin:6.7.0"
//> using options "-unchecked","-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations"

package riscv

import chisel3._
import chisel3.util._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import _root_.circt.stage.ChiselStage

import riscv.{Constants, Instructions}
import firrtl2.backends.experimental.smt.Op

class ALU extends Module {

	val io = IO(new Bundle {
		val a = Input(UInt(Constants.DATA_WIDTH.W))
		val b = Input(UInt(Constants.DATA_WIDTH.W))
		val op = Input(Instructions()) // specifically ALU operations
		val imm = Input(Bool())
		val result = Output(UInt(Constants.DATA_WIDTH.W))
		val err = Output(Bool()) // zero division error and overflow error
	})


	// pre-initialization
	io.result := 0.U


	switch (io.op) {
		is(Instructions.R_I_ADD) {
			io.result := io.a + io.b
		}
		is(Instructions.R_I_SUB) {
			io.result := io.a - io.b
		}
		is(Instructions.R_I_SLL) {
			io.result := io.a << io.b(4, 0)
		}
		is(Instructions.ALU_S@@LT) {
			io.result := (io.a.asSInt < io.b.asSInt).asUInt
		}
		is(Instructions.ALU_SLTU) {
			io.result := (io.a < io.b).asUInt
		}
		is(Instructions.ALU_XOR) {
			io.result := io.a ^ io.b
		}
		is(Instructions.ALU_SRL) {
			io.result := io.a >> io.b(4, 0)
		}
		is(Instructions.ALU_SRA) {
			io.result := (io.a.asSInt >> io.b(4, 0)).asUInt
		}
		is(Instructions.ALU_OR) {
			io.result := io.a | io.b
		}
		is(Instructions.ALU_AND) {
			io.result := io.a & io.b
		}

		// I dont know why so... but lets leave it like this
		is(Instructions.ALU_COPY1) {
			io.result := io.a
		}
		
		is(Instructions.ALU_COPY2) {
			io.result := io.b
		}
	}
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
      gen = new ALU(8, 8, Instructions.ALU_ADD),
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 