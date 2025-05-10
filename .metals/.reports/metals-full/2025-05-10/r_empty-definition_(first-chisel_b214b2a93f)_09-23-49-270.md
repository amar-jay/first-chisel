error id: file://<WORKSPACE>/riscv/alu.scala:22
file://<WORKSPACE>/riscv/alu.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -chisel3/Instrutions.
	 -chisel3/Instrutions#
	 -chisel3/Instrutions().
	 -chisel3/util/Instrutions.
	 -chisel3/util/Instrutions#
	 -chisel3/util/Instrutions().
	 -chiseltest/Instrutions.
	 -chiseltest/Instrutions#
	 -chiseltest/Instrutions().
	 -riscv/Instrutions.
	 -riscv/Instrutions#
	 -riscv/Instrutions().
	 -Instrutions.
	 -Instrutions#
	 -Instrutions().
	 -scala/Predef.Instrutions.
	 -scala/Predef.Instrutions#
	 -scala/Predef.Instrutions().
offset: 785
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

import riscv.{Constants, Instrutions}
import firrtl2.backends.experimental.smt.Op

class ALU extends Module {

	val io = IO(new Bundle {
		val a = Input(UInt(Constants.DATA_WIDTH.W))
		val b = Input(UInt(Constants.DATA_WIDTH.W))
		val op = Input(Ins@@trutions()) // specifically ALU operations
		val result = Output(UInt(Constants.DATA_WIDTH.W))
		val err = Output(Bool())
	})


	// pre-initialization
	io.result := 0.U


	switch (io.op) {
		is(Instrutions.I_ADD) {
			io.result := io.a + io.b
		}
		is(Instrutions.ALU_SUB) {
			io.result := io.a - io.b
		}
		is(Instrutions.ALU_SLL) {
			io.result := io.a << io.b(4, 0)
		}
		is(Instrutions.ALU_SLT) {
			io.result := (io.a.asSInt < io.b.asSInt).asUInt
		}
		is(Instrutions.ALU_SLTU) {
			io.result := (io.a < io.b).asUInt
		}
		is(Instrutions.ALU_XOR) {
			io.result := io.a ^ io.b
		}
		is(Instrutions.ALU_SRL) {
			io.result := io.a >> io.b(4, 0)
		}
		is(Instrutions.ALU_SRA) {
			io.result := (io.a.asSInt >> io.b(4, 0)).asUInt
		}
		is(Instrutions.ALU_OR) {
			io.result := io.a | io.b
		}
		is(Instrutions.ALU_AND) {
			io.result := io.a & io.b
		}

		// I dont know why so... but lets leave it like this
		is(Instrutions.ALU_COPY1) {
			io.result := io.a
		}
		
		is(Instrutions.ALU_COPY2) {
			io.result := io.b
		}
	}
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
      gen = new ALU(8, 8, Instrutions.ALU_ADD),
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 