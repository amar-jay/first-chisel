error id: file://<WORKSPACE>/riscv/decoder.scala:51
file://<WORKSPACE>/riscv/decoder.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 1673
uri: file://<WORKSPACE>/riscv/decoder.scala
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
import riscv.{Constants, Opcodes, InstructionTypes}
import riscv.Instructions._

// instruction decoder for RISC-V
import chisel3.util.Fill
import chisel3.util.is
import chisel3.util.switch
import chisel3.when
import firrtl2.backends.experimental.smt.Op


class InstructionDecoder extends Module {
  print("Instruction Decoder\n", Constants)
  val io = IO(new Bundle {
    val inst = Input(UInt(32.W))
    val pc = Input(UInt(7.W))

    val rd_addr   = Output(UInt(5.W))
    val rs1_addr  = Output(UInt(5.W))
    val rs2_addr  = Output(UInt(5.W))
    val imm       = Output(UInt(32.W))
    // Renamed _type to _op to match usage in code
    val _op    = Output(Instructions())
    val br_type   = Output(UInt(3.W))
    val mem_size  = Output(UInt(3.W)) 
    val wb_sel    = Output(Bool()) 
    val rf_wen    = Output(Bool()) 
    val csr_cmd   = Output(UInt(3.W))
    val illegal   = Output(Bool())
    val inst_type = Output(InstructionTypes())
  })

  // Default values
  io.rd_addr := io.inst(11, 7)
  io.rs1_addr := io.inst(19, 15)
  io.rs2_addr := io.inst(24, 20)
  io.imm := 0.U
  io._op := 0.U
  io.br_type := 0.U
  io.mem_size := 0@@.U
  io.wb_sel := false.B
  io.rf_wen := false.B
  io.csr_cmd := 0.U
  io.illegal := false.B
  io.inst_type := InstructionTypes.NOP
  val opcode = io.inst(6, 0)
  val funct3 = io.inst(14, 12)
  val funct7 = io.inst(31, 25)

  // Immediate value extraction
  val i_imm = Cat(Fill(20, io.inst(31)), io.inst(31, 20)) // Sign-extended
  val s_imm = Cat(Fill(20, io.inst(31)), io.inst(31, 25), io.inst(11, 7)) // Sign-extended
  val b_imm = Cat(Fill(19, io.inst(31)), io.inst(31), io.inst(7), io.inst(30, 25), io.inst(11, 8), 0.U(1.W)) // Sign-extended
  val u_imm = Cat(io.inst(31, 12), 0.U(12.W))
  val j_imm = Cat(Fill(11, io.inst(31)), io.inst(31), io.inst(19, 12), io.inst(20), io.inst(30, 21), 0.U(1.W)) // Sign-extended

  // Decode the instruction based on the opcode
   when (opcode === Opcodes.R_TYPE) {
      io.inst_type := InstructionTypes.R_TYPE
      io.rf_wen := true.B

      when (funct3 === "b000".U) { 
          // add or sub
          when (funct7(5)) {
            io._op := R_I_SUB
          }.otherwise {
            io._op := R_I_ADD
          }
      }.elsewhen(funct3 === "h4".U) { io._op := R_I_XOR 
		}.elsewhen(funct3 === "h6".U) { io._op := R_I_OR 
		}.elsewhen(funct3 === "h7".U) { io._op := R_I_AND 
		}.elsewhen(funct3 === "h1".U) { io._op := R_I_SLL 
		}.elsewhen(funct3 === "h5".U) {
          when (funct7(5)) {
            io._op := R_I_SRA
          }.otherwise {
            io._op := R_I_SRL
          }
        
      }.elsewhen(funct3 === "h2".U) { io._op := R_I_SLT 
		}.elsewhen(funct3 === "h3".U) { io._op := R_I_SLTU 

    }.elsewhen (opcode === Opcodes.I_TYPE1) { // ALU immediate operations
      io.imm := i_imm
      io.inst_type := InstructionTypes.I_TYPE
      io.rf_wen := true.B

      when (funct3 === "h0".U) { 
			io._op := I_I_ADDI 
		}.elsewhen (funct3 === "h4".U) { 
			io._op := I_I_XORI 
		}.elsewhen (funct3 === "h6".U) { 
			io._op := I_I_ORI 
		}.elsewhen(funct3 === "h7".U) { 
			io._op := I_I_ANDI 
		}.elsewhen(funct3 === "h1".U) { 
			io._op := I_I_SLLI 
		}.elsewhen (funct3 === "h5".U) {
          when (funct7(5)) {
            io._op := I_I_SRAI
          }.otherwise {
            io._op := I_I_SRLI
          }
      }.elsewhen (funct3 === "h2".U) { 
			io._op := I_I_SLTI 
		}.elsewhen (funct3 === "h3".U) { 
			io._op := I_I_SLTIU
		}.otherwise {
		  io.illegal := true.B
      }

	}.elsewhen (opcode === Opcodes.I_TYPE2) { // LOAD operations
      io.imm := i_imm
      io.inst_type := InstructionTypes.I_TYPE
      io.rf_wen := true.B
      io.wb_sel := true.B
      io.mem_size := funct3

      when (funct3 === "h0".U) { io._op := I_I_LB 
		}.elsewhen (funct3 === "h1".U) { io._op := I_I_LH 
      }.elsewhen  (funct3 === "h2".U) { io._op := I_I_LW 
     	}.elsewhen  (funct3 === "h4".U) { io._op := I_I_LBU 
     	}.elsewhen  (funct3 === "h5".U) { io._op := I_I_LHU 
      }.otherwise {
		  io.illegal := true.B
      }

   }.elsewhen (opcode === Opcodes.I_TYPE3) { // JALR operations
      io.imm := i_imm
      io.inst_type := InstructionTypes.I_TYPE
      io.rf_wen := true.B // JALR writes to rd

      switch (funct3) {
        is ("h0".U) { io._op := I_I_JALR }
      }

   }.elsewhen (opcode === Opcodes.I_TYPE4) { // CSR operations
      io.imm := i_imm
      io.inst_type := InstructionTypes.I_TYPE
      // Fixed CSR handling
      when (funct3 === "b000".U) {
        when (io.imm === 0.U) {
          io.csr_cmd := I_I_ECALL
        }.elsewhen (io.imm === 1.U) {
          io.csr_cmd := I_I_EBREAK
        }.otherwise {
          io.illegal := true.B
        }
      }.otherwise {
        io.csr_cmd := funct3
        io.rf_wen := true.B
      }

   }.elsewhen (opcode === Opcodes.S_TYPE) { // STORE operations

      io.imm := s_imm
      io.inst_type := InstructionTypes.S_TYPE
      io.mem_size := funct3
      
      when (funct3 === f"h0".U) { io._op := S_I_SB 
   	}.elsewhen (funct3 === f"h1".U) { io._op := S_I_SH 
		}.elsewhen (funct3 === f"h2".U) { io._op := S_I_SW 
	   }.otherwise { io.illegal := true.B 
      }

   }.elsewhen (opcode === Opcodes.B_TYPE) { // BRANCH operations
		      

      io.imm := b_imm
      io.inst_type := InstructionTypes.B_TYPE
      io.br_type := funct3
      
      when (funct3 === "h0".U) { io._op := B_I_BEQ 
		}.elsewhen (funct3 === "h1".U) { io._op := B_I_BNE 
		}.elsewhen (funct3 === "h4".U) { io._op := B_I_BLT
		}.elsewhen (funct3 === "h5".U) { io._op := B_I_BGE 
		}.elsewhen (funct3 === "h6".U) { io._op := B_I_BLTU 
		}.elsewhen (funct3 === "h7".U) { io._op := B_I_BGEU 
		}.otherwise {io.illegal := true.B 
      }


   }.elsewhen (opcode === Opcodes.U_TYPE1) { // LUI operations
      io.imm := u_imm
      io.inst_type := InstructionTypes.U_TYPE
      io.rf_wen := true.B
      io._op := U_I_LUI
    
   }.elsewhen (opcode === Opcodes.U_TYPE2) { // AUIPC operations
      io.imm := u_imm
      io.inst_type := InstructionTypes.U_TYPE
      io.rf_wen := true.B
      io._op := U_I_AUIPC
    
   }.elsewhen (opcode === Opcodes.J_TYPE) { // JAL operations
      io.imm := j_imm
      io.inst_type := InstructionTypes.J_TYPE
      io.rf_wen := true.B
      io._op := J_I_JAL

   }.otherwise{
      io.illegal := true.B
    }
  }
}


class InstructionDecoderTest extends AnyFlatSpec with ChiselScalatestTester {
	"InstructionDecoder" should "decode R_TYPE instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: ADD instruction (opcode = R_TYPE, funct3 = 0, funct7 = 0)
			dut.io.inst.poke("b00000000000100001000000010110011".U) // ADD x1, x2, x3
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.rs1_addr.expect(2.U) // rs1 = x2
			dut.io.rs2_addr.expect(3.U) // rs2 = x3
			dut.io._op.expect(R_I_ADD)
			dut.io.rf_wen.expect(true.B)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "decode I_TYPE1 (ALU immediate) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: ADDI instruction (opcode = I_TYPE1, funct3 = 0)
			dut.io.inst.poke("b00000000000100001000000010010011".U) // ADDI x1, x2, 1
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.rs1_addr.expect(2.U) // rs1 = x2
			dut.io._op.expect(I_I_ADDI)
			dut.io.rf_wen.expect(true.B)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "decode I_TYPE2 (LOAD) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: LW instruction (opcode = I_TYPE2, funct3 = 2)
			dut.io.inst.poke("b00000000000100001010000000000011".U) // LW x1, 1(x2)
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.rs1_addr.expect(2.U) // rs1 = x2
			dut.io._op.expect(I_I_LW)
			dut.io.mem_size.expect(2.U) // funct3 = 2
			dut.io.rf_wen.expect(true.B)
			dut.io.wb_sel.expect(true.B)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "decode S_TYPE (STORE) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: SW instruction (opcode = S_TYPE, funct3 = 2)
			dut.io.inst.poke("b00000000000100001010000000100011".U) // SW x1, 1(x2)
			dut.io.pc.poke(0.U)

			dut.io.rs1_addr.expect(2.U) // rs1 = x2
			dut.io.rs2_addr.expect(1.U) // rs2 = x1
			dut.io._op.expect(I_I_SW)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "decode B_TYPE (BRANCH) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: BEQ instruction (opcode = B_TYPE, funct3 = 0)
			dut.io.inst.poke("b00000000000100001000000001100011".U) // BEQ x1, x2, offset
			dut.io.pc.poke(0.U)

			dut.io.rs1_addr.expect(1.U) // rs1 = x1
			dut.io.rs2_addr.expect(2.U) // rs2 = x2
			dut.io.br_type.expect(0.U) // funct3 = 0
			dut.io.illegal.expect(false.B)
		}
	}

	it should "flag illegal instructions" in {
		test(new InstructionDecoder) { dut =>
			// Example: Illegal instruction
			dut.io.inst.poke("b11111111111111111111111111111111".U) // Invalid opcode
			dut.io.pc.poke(0.U)

			dut.io.illegal.expect(true.B)
		}
	}

	it should "decode U_TYPE1 (LUI) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: LUI instruction (opcode = U_TYPE1)
			dut.io.inst.poke("b00000000000100001000000010110111".U) // LUI x1, 0x123
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.imm.expect("h123000".U) // imm = 0x123 shifted left 12 bits
			dut.io.rf_wen.expect(true.B)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "decode U_TYPE2 (AUIPC) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: AUIPC instruction (opcode = U_TYPE2)
			dut.io.inst.poke("b00000000000100001000000010010111".U) // AUIPC x1, 0x123
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.imm.expect("h123000".U) // imm = 0x123 shifted left 12 bits
			dut.io.rf_wen.expect(true.B)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "decode J_TYPE (JAL) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: JAL instruction (opcode = J_TYPE)
			dut.io.inst.poke("b00000000000100001000000011011111".U) // JAL x1, offset
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.rf_wen.expect(true.B)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "handle edge cases for immediate values" in {
		test(new InstructionDecoder) { dut =>
			// Example: ADDI with maximum positive immediate
			dut.io.inst.poke("b01111111111100001000000010010011".U) // ADDI x1, x2, 2047
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.rs1_addr.expect(2.U) // rs1 = x2
			dut.io.imm.expect(2047.U) // imm = 2047
			dut.io.illegal.expect(false.B)

			// Example: ADDI with maximum negative immediate
			dut.io.inst.poke("b10000000000000001000000010010011".U) // ADDI x1, x2, -2048
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.rs1_addr.expect(2.U) // rs1 = x2
			dut.io.imm.expect("hFFFFF800".U) // imm = -2048 (sign-extended)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "flag illegal instructions for unknown opcodes" in {
		test(new InstructionDecoder) { dut =>
			// Example: Invalid opcode
			dut.io.inst.poke("b00000000000000000000000000000000".U) // Invalid opcode
			dut.io.pc.poke(0.U)

			dut.io.illegal.expect(true.B)
		}
	}
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 