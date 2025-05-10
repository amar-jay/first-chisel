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
	val rs1_data = Wire(UInt(32.W)) // output from the register file
	val rs2_data = Wire(UInt(32.W)) // output from the register file
	val rd_data = Reg(UInt(32.W)) // input to the register file


   // Default register file read/write settings
   regfile.io.read0.en := false.B
   regfile.io.read1.en := false.B
   regfile.io.write.en := false.B
	regfile.io.write.addr := 0.U
	regfile.io.write.data := 0.S(32.W)
	regfile.io.read0.addr := 0.U
	regfile.io.read1.addr := 0.U
   rs1_data := 0.U
   rs2_data := 0.U
    
	// Memory interface defaults
   mem_addr := 0.U
   mem_write_data := 0.S(32.W)
   mem_write_en := false.B
	mem_read_data := 0.S(32.W)

	// Default next PC is PC + 4 (normal sequential execution)
   pc_next := pc + 4.U

	// when we have a I, R, S, B type instruction and I type instruction
	when (inst_fetcher.io.inst_type === InstructionTypes.R_TYPE ||
		inst_fetcher.io.inst_type === InstructionTypes.S_TYPE ||
		inst_fetcher.io.inst_type === InstructionTypes.B_TYPE) {
		regfile.io.read0.en   := true.B
		regfile.io.read0.addr := rs1_addr

		rs1_data := regfile.io.read0.data.asUInt

		regfile.io.read1.en   := true.B
		regfile.io.read1.addr := rs2_addr

		rs2_data := regfile.io.read1.data.asUInt
	}

	// when we have a I, R, S, B type instruction and I type instruction
	when (inst_fetcher.io.inst_type === InstructionTypes.I_TYPE) {
		regfile.io.read0.en   := true.B
		regfile.io.read0.addr := rs1_addr

		rs1_data := regfile.io.read0.data.asUInt

		// imm is 12 bits but rs2 is 32 bits
		rs2_data := inst_fetcher.io.imm
	}


	when (regfile.io.write.en) {
		regfile.io.write.addr := rd_addr
		regfile.io.write.data := rd_data.asSInt
	} 

	alu.io.a := rs1_data
	alu.io.b := rs2_data
	alu.io.op := InstructionToALU.getALUOp(inst_fetcher.io._op)

	rd_data := alu.io.result
   // Connect output to IO
   io.rddata := rd_data.asSInt

    // Instruction-specific handling
    switch(inst_fetcher.io.inst_type) {
      is(InstructionTypes.R_TYPE) {
      	// R-type: Register arithmetic/logical operations		
			regfile.io.write.en := true.B
			regfile.io.write.addr := rd_addr
			regfile.io.write.data := rd_data.asSInt
		}

		is(InstructionTypes.I_TYPE) {
			when(inst_fetcher.io._op === Instructions.I_I_JALR) {
			}.elsewhen(inst_fetcher.io.mem_size =/= 0.U) {
				   // Load instructions
            	regfile.io.write.en := true.B
               regfile.io.write.addr := rd_addr
               mem_addr := (rs1_data + inst_fetcher.io.imm)

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
                     regfile.io.write.data := mem_read_data
                  }
                  is(Instructions.I_I_LBU) {
                  	// Zero-extended byte
                     regfile.io.write.data := Cat(0.U(24.W), mem_read_data(7, 0)).asSInt
                  }
                  is(Instructions.I_I_LHU) {
                     // Zero-extended halfword
                     regfile.io.write.data := Cat(0.U(16.W), mem_read_data(15, 0)).asSInt
                  }
                }
			}.otherwise{                
				// Other I-type instructions (arithmetic/logical with immediate)
            regfile.io.write.en := true.B
            regfile.io.write.addr := rd_addr
				regfile.io.write.data := rd_data.asSInt

			}
		}
		is(InstructionTypes.S_TYPE) {
            // Store instructions
            mem_addr := (rs1_data + inst_fetcher.io.imm)
            mem_write_en := true.B
            
            switch(inst_fetcher.io._op) {
                is(Instructions.S_I_SB) {
                    // Store byte
                    mem_write_data := (rs2_data & 0xFF.U).asSInt
                }
                is(Instructions.S_I_SH) {
                    // Store halfword
                    mem_write_data := (rs2_data & 0xFFFF.U).asSInt
                }
                is(Instructions.S_I_SW) {
                    // Store word
                    mem_write_data := rs2_data.asSInt
                }
            }
        }
		is(InstructionTypes.B_TYPE) {
            // Branch instructions
            val branch_taken = Wire(Bool())
            branch_taken := false.B
            
            switch(inst_fetcher.io._op) {
                is(Instructions.B_I_BEQ) { branch_taken := rs1_data === rs2_data }
                is(Instructions.B_I_BNE) { branch_taken := rs1_data =/= rs2_data }
                is(Instructions.B_I_BLT) { branch_taken := rs1_data < rs2_data }
                is(Instructions.B_I_BGE) { branch_taken := rs1_data >= rs2_data }
                is(Instructions.B_I_BLTU) { branch_taken := rs1_data < rs1_data }
                is(Instructions.B_I_BGEU) { branch_taken := rs1_data >= rs1_data }
            }
            
            when(branch_taken) {
                pc_next := pc + inst_fetcher.io.imm
            }
      }
		is(InstructionTypes.U_TYPE) {
            regfile.io.write.en := true.B
            regfile.io.write.addr := rd_addr
            
            when(inst_fetcher.io._op === Instructions.U_I_LUI) {
                // Load Upper Immediate
                regfile.io.write.data := inst_fetcher.io.imm.asSInt
            }.elsewhen(inst_fetcher.io._op === Instructions.U_I_AUIPC) {
                // Add Upper Immediate to PC
                regfile.io.write.data := (pc + inst_fetcher.io.imm).asSInt
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
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
		gen = new RISCVCore,
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}