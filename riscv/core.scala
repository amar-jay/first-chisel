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
	val rs1_data = Reg(UInt(32.W)) // output from the register file
	val rs2_data = Reg(UInt(32.W)) // output from the register file
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

		when (regfile.io.read0.ready) {
			rs1_data := regfile.io.read0.data.asUInt
		}

		regfile.io.read1.en   := true.B
		regfile.io.read1.addr := rs2_addr

		when (regfile.io.read1.ready) {	
			rs2_data := regfile.io.read1.data.asUInt
		}
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
	// print a and b values
	rd_data := alu.io.result

	printf(cf"ALU Operation: op = ${inst_fetcher.io._op} a = ${rs1_data} b = ${rs2_data}\n")

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

	// Connect output to IO
   io.rddata <> rs1_data.asSInt
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
		gen = new RISCVCore,
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}

class RISCVCoreTest extends AnyFlatSpec with ChiselScalatestTester {
	behavior of "RISCVCore"

	// Helper functions to create different instruction types
	def makeRType(opcode: Int, funct3: Int, funct7: Int, rs1: Int, rs2: Int, rd: Int): Int =  {
		((funct7 & 0x7F) << 25) | ((rs2 & 0x1F) << 20) | ((rs1 & 0x1F) << 15) | 
		((funct3 & 0x7) << 12) | ((rd & 0x1F) << 7) | (opcode & 0x7F)
	}
	
	def makeIType(opcode: Int, funct3: Int, imm: Int, rs1: Int, rd: Int): Int = {
		((imm & 0xFFF) << 20) | ((rs1 & 0x1F) << 15) | ((funct3 & 0x7) << 12) | 
		((rd & 0x1F) << 7) | (opcode & 0x7F)
	}
	
	def makeSType(opcode: Int, funct3: Int, imm: Int, rs1: Int, rs2: Int): Int = {
		(((imm >> 5) & 0x7F) << 25) | ((rs2 & 0x1F) << 20) | ((rs1 & 0x1F) << 15) | 
		((funct3 & 0x7) << 12) | ((imm & 0x1F) << 7) | (opcode & 0x7F)
	}
	
	def makeBType(opcode: Int, funct3: Int, imm: Int, rs1: Int, rs2: Int): Int = {
		(((imm >> 12) & 0x1) << 31) | (((imm >> 5) & 0x3F) << 25) | ((rs2 & 0x1F) << 20) | 
		((rs1 & 0x1F) << 15) | ((funct3 & 0x7) << 12) | (((imm >> 1) & 0xF) << 8) | 
		(((imm >> 11) & 0x1) << 7) | (opcode & 0x7F)
	}
	
	def makeUType(opcode: Int, imm: Int, rd: Int): Int = {
		((imm & 0xFFFFF) << 12) | ((rd & 0x1F) << 7) | (opcode & 0x7F)
	}
	
	def makeJType(opcode: Int, imm: Int, rd: Int): Int = {
		(((imm >> 20) & 0x1) << 31) | (((imm >> 1) & 0x3FF) << 21) | 
		(((imm >> 11) & 0x1) << 20) | (((imm >> 12) & 0xFF) << 12) | 
		((rd & 0x1F) << 7) | (opcode & 0x7F)
	}

	// Test R-type instructions
	it should "correctly execute ADD instruction" in {
		test(new RISCVCore()) { dut =>
			// First load values into registers
			// ADDI x1, x0, 10 (x1 = 10)
			val addi_x1:Int= makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 10, 0, 1)
			dut.io.inst.poke(addi_x1.U)
			dut.clock.step(1)
			dut.io.rddata.expect(10.S)
			
			// ADDI x2, x0, 20 (x2 = 20)
			val addi_x2 = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 20, 0, 2)
			dut.io.inst.poke(addi_x2.U)
			dut.clock.step(1)
			
			// ADD x3, x1, x2 (x3 = x1 + x2 = 30)
			val add = makeRType(Opcodes.R_TYPE.litValue.toInt, 0, 0, 1, 2, 3)
			dut.io.inst.poke(add.U)
			dut.clock.step(1)
			dut.io.rddata.expect(30.S)
		}
	}

	it should "correctly execute SUB instruction" in {
		test(new RISCVCore()) { dut =>
			// First load values into registers
			// ADDI x1, x0, 30 (x1 = 30)
			val addi_x1 = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 30, 0, 1)
			dut.io.inst.poke(addi_x1.U)
			dut.clock.step(1)
			
			// ADDI x2, x0, 12 (x2 = 12)
			val addi_x2 = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 12, 0, 2)
			dut.io.inst.poke(addi_x2.U)
			dut.clock.step(1)
			
			// SUB x3, x1, x2 (x3 = x1 - x2 = 18)
			val sub = makeRType(Opcodes.R_TYPE.litValue.toInt, 0, 0x20, 1, 2, 3)
			dut.io.inst.poke(sub.U)
			dut.clock.step(1)
			dut.io.rddata.expect(18.S)
		}
	}

	// Test I-type instructions
	it should "correctly execute ADDI instruction" in {
		test(new RISCVCore()) { dut =>
			// ADDI x1, x0, 42 (x1 = 42)
			val addi = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 42, 0, 1)
			dut.io.inst.poke(addi.U)
			dut.clock.step(1)
			dut.io.rddata.expect(42.S)
		}
	}

	// Test immediate with sign extension
	it should "handle negative immediates correctly" in {
		test(new RISCVCore()) { dut =>
			// ADDI x1, x0, -15 (x1 = -15)
			val addi = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 0xFFF1, 0, 1) // 0xFFF1 is -15 in 12-bit two's complement
			dut.io.inst.poke(addi.U)
			dut.clock.step(1)
			dut.io.rddata.expect(-15.S)
		}
	}

	// Test logical operations
	it should "correctly execute AND, OR, XOR instructions" in {
		test(new RISCVCore()) { dut =>
			// Load test values
			// ADDI x1, x0, 0x0F (x1 = 0x0F)
			val addi_x1 = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 0x0F, 0, 1)
			dut.io.inst.poke(addi_x1.U)
			dut.clock.step(1)
			
			// ADDI x2, x0, 0xF0 (x2 = 0xF0)
			val addi_x2 = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 0xF0, 0, 2)
			dut.io.inst.poke(addi_x2.U)
			dut.clock.step(1)
			
			// AND x3, x1, x2 (x3 = 0x0F & 0xF0 = 0)
			val and = makeRType(Opcodes.R_TYPE.litValue.toInt, 7, 0, 1, 2, 3)
			dut.io.inst.poke(and.U)
			dut.clock.step(1)
			dut.io.rddata.expect(0.S)
			
			// OR x3, x1, x2 (x3 = 0x0F | 0xF0 = 0xFF)
			val or = makeRType(Opcodes.R_TYPE.litValue.toInt, 6, 0, 1, 2, 3)
			dut.io.inst.poke(or.U)
			dut.clock.step(1)
			dut.io.rddata.expect(0xFF.S)
			
			// XOR x3, x1, x2 (x3 = 0x0F ^ 0xF0 = 0xFF)
			val xor = makeRType(Opcodes.R_TYPE.litValue.toInt, 4, 0, 1, 2, 3)
			dut.io.inst.poke(xor.U)
			dut.clock.step(1)
			dut.io.rddata.expect(0xFF.S)
		}
	}

	// Test U-type instructions
	it should "correctly execute LUI instruction" in {
		test(new RISCVCore()) { dut =>
			// LUI x1, 0x12345 (x1 = 0x12345000)
			val lui = makeUType(Opcodes.U_TYPE1.litValue.toInt, 0x12345, 1)
			dut.io.inst.poke(lui.U)
			dut.clock.step(1)
			dut.io.rddata.expect(0x12345000.S)
		}
	}

	// Test J-type instructions (JAL)
	it should "correctly execute JAL instruction" in {
		test(new RISCVCore()) { dut =>
			// Initial PC = 0
			// JAL x1, 16 (x1 = 4, PC = 16)
			val jal = makeJType(Opcodes.J_TYPE.litValue.toInt, 16, 1)
			dut.io.inst.poke(jal.U)
			dut.clock.step(1)
			dut.io.rddata.expect(4.S) // Return address should be PC+4 (0+4=4)
		}
	}

	// Test branch instructions
	it should "correctly execute BEQ instruction" in {
		test(new RISCVCore()) { dut =>
			// Load identical values in two registers
			// ADDI x1, x0, 5 (x1 = 5)
			val addi_x1 = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 5, 0, 1)
			dut.io.inst.poke(addi_x1.U)
			dut.clock.step(1)
			
			// ADDI x2, x0, 5 (x2 = 5)
			val addi_x2 = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 5, 0, 2)
			dut.io.inst.poke(addi_x2.U)
			dut.clock.step(1)
			
			// BEQ x1, x2, 8 (Branch if x1 == x2, which is true)
			val beq = makeBType(Opcodes.B_TYPE.litValue.toInt, 0, 8, 1, 2)
			dut.io.inst.poke(beq.U)
			dut.clock.step(1)
			
			// JAL x1, 0 (This just captures PC value in x1 to verify branch was taken)
			val jal = makeJType(Opcodes.I_TYPE3.litValue.toInt, 0, 1)
			dut.io.inst.poke(jal.U)
			dut.clock.step(1)
			
			// If branch was taken, PC should be 12 (4 + 8), and x1 should equal 16
			dut.io.rddata.expect(16.S)
		}
	}

	// Test factorial calculation
	it should "calculate factorial correctly" in {
		test(new RISCVCore()) { dut =>
			// This is a more complex test that calculates 5! using our core
			
			// Initialize x1 = 5 (n)
			val addi_x1 = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 5, 0, 1)
			dut.io.inst.poke(addi_x1.U)
			dut.clock.step(1)
			
			// Initialize x2 = 1 (result)
			val addi_x2 = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 1, 0, 2)
			dut.io.inst.poke(addi_x2.U)
			dut.clock.step(1)
			
			// Loop:
			// MUL x2, x2, x1 (result = result * n)
			val mul = makeRType(Opcodes.R_TYPE.litValue.toInt, 0, 1, 2, 1, 2)
			dut.io.inst.poke(mul.U)
			dut.clock.step(1)
			
			// ADDI x1, x1, -1 (n--)
			val dec_x1 = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, -1, 1, 1) 
			dut.io.inst.poke(dec_x1.U)
			dut.clock.step(1)
			
			// MUL x2, x2, x1 (result = result * (n-1))
			dut.io.inst.poke(mul.U)
			dut.clock.step(1)
			
			// ADDI x1, x1, -1 (n--)
			dut.io.inst.poke(dec_x1.U)
			dut.clock.step(1)
			
			// MUL x2, x2, x1 (result = result * (n-2))
			dut.io.inst.poke(mul.U)
			dut.clock.step(1)
			
			// ADDI x1, x1, -1 (n--)
			dut.io.inst.poke(dec_x1.U)
			dut.clock.step(1)
			
			// MUL x2, x2, x1 (result = result * (n-3))
			dut.io.inst.poke(mul.U)
			dut.clock.step(1)
			
			// ADDI x1, x1, -1 (n--)
			dut.io.inst.poke(dec_x1.U)
			dut.clock.step(1)
			
			// MUL x2, x2, x1 (result = result * (n-4) = 5!)
			dut.io.inst.poke(mul.U)
			dut.clock.step(1)
			
			// ADDI x3, x2, 0 (Copy result to x3 for output)
			val addi_x3 = makeIType(Opcodes.I_TYPE1.litValue.toInt, 0, 0, 2, 3)
			dut.io.inst.poke(addi_x3.U)
			dut.clock.step(1)
			
			// 5! = 120
			dut.io.rddata.expect(120.S)
		}
	}
}