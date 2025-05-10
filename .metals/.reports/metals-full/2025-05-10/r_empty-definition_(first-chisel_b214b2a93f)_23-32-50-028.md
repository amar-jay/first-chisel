error id: file://<WORKSPACE>/riscv/core.scala:159
file://<WORKSPACE>/riscv/core.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -chisel3/io/mem_read_data.
	 -chisel3/io/mem_read_data#
	 -chisel3/io/mem_read_data().
	 -chisel3/util/io/mem_read_data.
	 -chisel3/util/io/mem_read_data#
	 -chisel3/util/io/mem_read_data().
	 -chiseltest/io/mem_read_data.
	 -chiseltest/io/mem_read_data#
	 -chiseltest/io/mem_read_data().
	 -riscv/Instructions.io.mem_read_data.
	 -riscv/Instructions.io.mem_read_data#
	 -riscv/Instructions.io.mem_read_data().
	 -io/mem_read_data.
	 -io/mem_read_data#
	 -io/mem_read_data().
	 -scala/Predef.io.mem_read_data.
	 -scala/Predef.io.mem_read_data#
	 -scala/Predef.io.mem_read_data().
offset: 5406
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

	val pc = RegInit(0.U(32.W))
   val pc_next = Wire(UInt(32.W))
   pc := pc_next

	val inst_fetcher = Module(new InstructionFetcher())	
	val regfile = Module(new RegisterFile())
	val alu = Module(new ALU())

	// Connect the instruction fetcher to the register file
	inst_fetcher.io.inst := io.inst

	// memory interface and variables
	// TODO: implement memory interface
	val mem_addr = Wire(UInt(32.W))
	val mem_read_data = Wire(SInt(32.W))
	val mem_write_data = Wire(SInt(32.W))
	val mem_write_en = Wire(Bool())


	// Extract register addresses
	val rd_addr = inst_fetcher.io.rd_addr
	val rs1_addr = inst_fetcher.io.rs1_addr
	val rs2_addr = inst_fetcher.io.rs2_addr
	val rs1_data = Wire(SInt(32.W)) // output from the register file
	val rs2_data = Wire(SInt(32.W)) // output from the register file
	val rd_data = RegInit(SInt(32.W)) // input to the register file


   // Default register file read/write settings
   regfile.io.read0.en := false.B
   regfile.io.read1.en := false.B
   regfile.io.write.en := false.B
   rs1_data := 0.S
   rs2_data := 0.S
    
	// Memory interface defaults
   mem_addr := 0.U
   mem_write_data := 0.U
   mem_write_en := false.B

	// Default next PC is PC + 4 (normal sequential execution)
   pc_next := pc + 4.U

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


	when (regfile.io.write.en) {
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
   // Connect output to IO
   io.rddata := rd_data

    // Instruction-specific handling
    switch(inst_fetcher.io.inst_type) {
      is(InstructionTypes.R_TYPE) {
      	// R-type: Register arithmetic/logical operations		
			regfile.io.write.en := true.B
			regfile.io.write.addr := rd_addr
			regfile.io.write.data := rd_data
		}

		is(InstructionTypes.I_TYPE) {
			when(inst_fetcher.io._op === Instructions.I_I_JALR) {
			}.elsewhen(inst_fetcher.io.mem_size =/= 0.U) {
				   // Load instructions
            	regfile.io.write.en := true.B
               regfile.io.write.addr := rd_addr
               mem_addr := (rs1_data.asUInt + inst_fetcher.io.imm)

					// write data to memory based on the instruction type
					// Handle different load types
               switch(inst_fetcher.io._op) {
                  is(Instructions.I_I_LB) {
                     // Sign-extended byte
                     regfile.io.write.data := Cat(Fill(24, mem_read_data(7)), mem_read_data(7, 0)).asSInt
                  }
                  is(Instructions.I_I_LH) {
                     // Sign-extended halfword
                     regfile.io.write.data := Cat(Fill(16, mem_read_data(15)), mem_read_data(15, 0)).asSInt
                  }
                  is(Instructions.I_I_LW) {
                     // Word
                     regfile.io.write.data := mem_read_data.asSInt
                  }
                  is(Instructions.I_I_LBU) {
                  	// Zero-extended byte
                     regfile.io.write.data := Cat(0.U(24.W), mem_read_data(7, 0)).asSInt
                  }
                  is(Instructions.I_I_LHU) {
                     // Zero-extended halfword
                     regfile.io.write.data := Cat(0.U(16.W), io.@@mem_read_data(15, 0)).asSInt
                  }
                }
			}.otherwise{                
				// Other I-type instructions (arithmetic/logical with immediate)
            regfile.io.write.en := true.B
            regfile.io.write.addr := rd_addr
				regfile.io.write.data := rd_data

			}
		}

		is(InstructionTypes.J_TYPE) {
			when (inst_fetcher.io._op === Instructions.J_I_JAL) {
				// JAL instruction
				pc_next := pc + inst_fetcher.io.imm
				regfile.io.write.en := true.B
				regfile.io.write.addr := rd_addr
				regfile.io.write.data := (pc + 4.U).asSInt
			}
		}
	} 

	when (jal_en) {
		regfile.io.write.en := true.B
	} .elsewhen (jalr_en) {
		regfile.io.write.en := true.B
	} .otherwise {
		regfile.io.write.en := false.B
	}

}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
		gen = new RISCVCore,
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 