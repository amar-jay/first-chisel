error id: file://<WORKSPACE>/riscv/core.scala:74
file://<WORKSPACE>/riscv/core.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -chisel3/regfile/io/write/en.
	 -chisel3/regfile/io/write/en#
	 -chisel3/regfile/io/write/en().
	 -chisel3/util/regfile/io/write/en.
	 -chisel3/util/regfile/io/write/en#
	 -chisel3/util/regfile/io/write/en().
	 -chiseltest/regfile/io/write/en.
	 -chiseltest/regfile/io/write/en#
	 -chiseltest/regfile/io/write/en().
	 -riscv/Instructions.regfile.io.write.en.
	 -riscv/Instructions.regfile.io.write.en#
	 -riscv/Instructions.regfile.io.write.en().
	 -regfile/io/write/en.
	 -regfile/io/write/en#
	 -regfile/io/write/en().
	 -scala/Predef.regfile.io.write.en.
	 -scala/Predef.regfile.io.write.en#
	 -scala/Predef.regfile.io.write.en().
offset: 2489
uri: file://<WORKSPACE>/riscv/core.scala
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
import riscv.{Opcodes, InstructionTypes, Instructions, InstructionFetcher, RegisterFile, ALU, InstructionToALU}

import riscv.Instructions._

// instruction decoder for RISC-V
import chisel3.util.Fill
import chisel3.util.is
import chisel3.util.switch
import chisel3.when
import firrtl2.backends.experimental.smt.Op


class RISCVCore extends Module {
	val io = IO(new Bundle {
		val inst = Input(UInt(32.W))
		// val pc = Input(UInt(32.W)) // TODO: Program Counter. what is its size? 32 bits? is it necessary in chisel?
  		val rddata = Output(SInt(32.W))
	})

	val inst_fetcher = Module(new InstructionFetcher())	
	val regfile = Module(new RegisterFile())
	val alu = Module(new ALU())

	// Connect the instruction fetcher to the register file
	// inst_fetcher.io.clk <> io.clk
	inst_fetcher.io.inst := io.inst


	val rd_addr = inst_fetcher.io.rd_addr
	val rs1_addr = inst_fetcher.io.rs1_addr
	val rs2_addr = inst_fetcher.io.rs2_addr
	val rs1_data = Wire(SInt(32.W)) // output from the register file
	val rs2_data = Wire(SInt(32.W)) // output from the register file
	val rd_data = RegInit(SInt(32.W)) // input to the register file

	// when we have a I, R, S, B type instruction and I type instruction
	when (inst_fetcher.io.inst_type === InstructionTypes.R_TYPE ||
		inst_fetcher.io.inst_type === InstructionTypes.S_TYPE ||
		inst_fetcher.io.inst_type === InstructionTypes.B_TYPE) {
		regfile.io.read0.en   := true.B
		regfile.io.read0.addr := rs1_addr

		rs1_data := regfile.io.read0.data

		regfile.io.read1.en   := true.B
		regfile.io.read1.addr := rs2_addr

		rs2_data := regfile.io.read1.data
	}

	// when we have a I, R, S, B type instruction and I type instruction
	when (inst_fetcher.io.inst_type === InstructionTypes.I_TYPE) {
		regfile.io.read0.en   := true.B
		regfile.io.read0.addr := rs1_addr

		rs1_data := regfile.io.read0.data

		// imm is 12 bits but rs2 is 32 bits
		rs2_data := inst_fetcher.io.imm.asSInt
	}


	when (regfile.io.write.en@@) {
		regfile.io.write.addr := rd_addr
		regfile.io.write.data := rd_data
	} 

	val jal_en = Wire(Bool())
	jal_en := (inst_fetcher.io.inst_type === InstructionTypes.J_TYPE && inst_fetcher.io._op === Instructions.J_I_JAL)

	val jalr_en = Wire(Bool())
	jalr_en := (inst_fetcher.io.inst_type === InstructionTypes.I_TYPE && inst_fetcher.io._op === Instructions.I_I_JALR)


	alu.io.a := rs1_data
	alu.io.b := rs2_data
	alu.io.op := InstructionToALU.mapping(inst_fetcher.io._op)

	rd_data := alu.io.result

	when (rd_data =/= 0.S) {
		regfile.io.write.en := true.B
		regfile.io.write.addr := rd_addr
		regfile.io.write.data := rd_data
	} 

	when (jal_en) {
		regfile.io.write.en := true.B
	} .elsewhen (jalr_en) {
		regfile.io.write.en := true.B
	} .otherwise {
		regfile.io.write.en := false.B
	}

}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 