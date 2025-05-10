error id: file://<WORKSPACE>/riscv/decoder.scala:308
file://<WORKSPACE>/riscv/decoder.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 9670
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
    val csr_cmd_en   = Output(UInt(3.W))
    val illegal   = Output(Bool())
    val inst_type = Output(InstructionTypes())
  })

  // Default values
  io.rd_addr := io.inst(11, 7)
  io.rs1_addr := io.inst(19, 15)
  io.rs2_addr := io.inst(24, 20)
  io.imm := 0.U
  io._op := Instructions.NOP
  io.br_type := 0.U
  io.mem_size := 0.U
  io.wb_sel := false.B
  io.rf_wen := false.B
  io.csr_cmd_en := false.B
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
		io.csr_cmd_en := true.B
      when (funct3 === "b000".U) {
        when (io.imm === 0.U) {
          io._op := I_I_ECALL
        }.elsewhen (io.imm === 1.U) {
          io._op := I_I_EBREAK
        }.otherwise {
          io.illegal := true.B
        }
      }.otherwise {
        io._op := NOP
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
  
  // Helper function to simplify common test patterns
  def testInstruction(
    inst: UInt, 
    expectedOp: Instructions.Type,
    expectedRdAddr: Int = 0,
    expectedRs1Addr: Int = 0,
    expectedRs2Addr: Int = 0,
    expectedImm: BigInt = 0,
    expectedBrType: Int = 0,
    expectedMemSize: Int = 0,
    expectedWbSel: Boolean = false,
    expectedRfWen: Boolean = false,
    expectedCsrCmdEn: Boolean = false,
    expectedIllegal: Boolean = false,
    expectedInstType: InstructionTypes.Type = InstructionTypes.NOP
  )(implicit dut: InstructionDecoder): Unit = {
    dut.io.inst.poke(inst)
    dut.io.pc.poke(0.U)
	
    dut.io.illegal.expect(expectedIllegal.B)
    
    dut.io._op.expect(expectedOp)
    dut.io.rd_addr.expect(expectedRdAddr.U)
    dut.io.rs1_addr.expect(expectedRs1Addr.U)
    dut.io.rs2_addr.expect(expectedRs2Addr.U)
    dut.io.imm.expect(expectedImm.U)
    dut.io.br_type.expect(expectedBrType.U)
    dut.io.mem_size.expect(expectedMemSize.U)
    dut.io.wb_sel.expect(expectedWbSel.B)
    dut.io.rf_wen.expect(expectedRfWen.B)
	 dut.io.csr_cmd_en.expect(expectedCsrCmdEn.B)
    dut.io.inst_type.expect(expectedInstType)
  }

  // R-TYPE INSTRUCTIONS
  "InstructionDecoder" should "decode ADD instruction correctly" in {
    test(new InstructionDecoder) { implicit dut =>
      // ADD x1, x2, x3
      val inst = "b00000000001100010000000010110011".U
      testInstruction(
        inst = inst,
        expectedOp = R_I_ADD,
        expectedRdAddr = 1,
        expectedRs1Addr = 2,
        expectedRs2Addr = 3,
        expectedRfWen = true,
        expectedInstType = InstructionTypes.R_TYPE
      )
    }
  }

  it should "decode SUB instruction correctly" in {
    test(new InstructionDecoder) { implicit dut =>
      // SUB x5, x6, x7
      val inst = "b01000000011101100000001010110011".U
      testInstruction(
        inst = inst,
		  expectedInstType = InstructionTypes.R_TYPE,
        expectedOp = R_I_SUB,
        expectedRdAddr = 5,
        expectedRs1Addr = 12,
        expectedRs2Addr = 7,
        expectedRfWen = true,
      )
    }
  }

  it should "decode XOR instruction correctly" in {
    test(new InstructionDecoder) { implicit dut =>
      // XOR x8, x9, x10
      val inst = "b00000000101001001100010000110011".U
      testInstruction(
        inst = inst,
        expectedOp = R_I_XOR,
        expectedRdAddr = 8,
        expectedRs1Addr = 9,
        expectedRs2Addr = 10,
        expectedRfWen = true,
        expectedInstType = InstructionTypes.R_TYPE
      )
    }
  }

  // I-TYPE IMMEDIATE INSTRUCTIONS
  it should "decode ADDI instruction correctly" in {
    test(new InstructionDecoder) { implicit dut =>
      // ADDI x1, x2, 123
      val inst = "b@@00000111101100010000000010010011".U
      testInstruction(
        inst = inst,
        expectedOp = I_I_ADDI,
        expectedRdAddr = 1,
        expectedRs1Addr = 2,
        expectedImm = 123,
        expectedRfWen = true,
        expectedInstType = InstructionTypes.I_TYPE
      )
    }
  }

//   it should "decode ANDI instruction correctly" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // ANDI x3, x4, -1
//       val inst = "b11111111111100100111000110010011".U
//       testInstruction(
//         inst = inst,
//         expectedOp = I_I_ANDI,
//         expectedRdAddr = 3,
//         expectedRs1Addr = 4,
//         expectedImm = BigInt("FFFFFFFF", 16), // -1 sign-extended to 32 bits
//         expectedRfWen = true,
//         expectedInstType = InstructionTypes.I_TYPE
//       )
//     }
//   }

//   // I-TYPE LOAD INSTRUCTIONS
//   it should "decode LW instruction correctly" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // LW x5, 16(x6)
//       val inst = "b00000001000000110010001010000011".U
//       testInstruction(
//         inst = inst,
//         expectedOp = I_I_LW,
//         expectedRdAddr = 5,
//         expectedRs1Addr = 6,
//         expectedImm = 16,
//         expectedMemSize = 2,
//         expectedWbSel = true,
//         expectedRfWen = true,
//         expectedInstType = InstructionTypes.I_TYPE
//       )
//     }
//   }

//   it should "decode LB instruction correctly" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // LB x7, -4(x8)
//       val inst = "b11111111110001000000001110000011".U
//       testInstruction(
//         inst = inst,
//         expectedOp = I_I_LB,
//         expectedRdAddr = 7,
//         expectedRs1Addr = 8,
//         expectedImm = BigInt("FFFFFFFC", 16), // -4 sign-extended
//         expectedMemSize = 0,
//         expectedWbSel = true,
//         expectedRfWen = true,
//         expectedInstType = InstructionTypes.I_TYPE
//       )
//     }
//   }

//   // S-TYPE INSTRUCTIONS
//   it should "decode SW instruction correctly" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // SW x10, 24(x11)
//       val inst = "b00000000101001011010011000100011".U
//       testInstruction(
//         inst = inst,
//         expectedOp = S_I_SW,
//         expectedRs1Addr = 11,
//         expectedRs2Addr = 10,
//         expectedImm = 24,
//         expectedMemSize = 2,
//         expectedInstType = InstructionTypes.S_TYPE
//       )
//     }
//   }

//   // B-TYPE INSTRUCTIONS
//   it should "decode BEQ instruction correctly" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // BEQ x12, x13, 32
//       val inst = "b00000001101101100000100001100011".U
//       testInstruction(
//         inst = inst,
//         expectedOp = B_I_BEQ,
//         expectedRs1Addr = 12,
//         expectedRs2Addr = 13,
//         expectedImm = 32,
//         expectedBrType = 0,
//         expectedInstType = InstructionTypes.B_TYPE
//       )
//     }
//   }

//   it should "decode BNE instruction correctly" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // BNE x14, x15, -8
//       val inst = "b11111110111101110001000001100011".U
//       testInstruction(
//         inst = inst,
//         expectedOp = B_I_BNE,
//         expectedRs1Addr = 14,
//         expectedRs2Addr = 15,
//         expectedImm = BigInt("FFFFFFF8", 16), // -8 sign-extended
//         expectedBrType = 1,
//         expectedInstType = InstructionTypes.B_TYPE
//       )
//     }
//   }

//   // U-TYPE INSTRUCTIONS
//   it should "decode LUI instruction correctly" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // LUI x16, 0xABCDE
//       val inst = "b10101011110011011110100000110111".U
//       testInstruction(
//         inst = inst,
//         expectedOp = U_I_LUI,
//         expectedRdAddr = 16,
//         expectedImm = BigInt("ABCDE000", 16),
//         expectedRfWen = true,
//         expectedInstType = InstructionTypes.U_TYPE
//       )
//     }
//   }

//   it should "decode AUIPC instruction correctly" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // AUIPC x17, 0x12345
//       val inst = "b00010010001101000101100010010111".U
//       testInstruction(
//         inst = inst,
//         expectedOp = U_I_AUIPC,
//         expectedRdAddr = 17,
//         expectedImm = BigInt("12345000", 16),
//         expectedRfWen = true,
//         expectedInstType = InstructionTypes.U_TYPE
//       )
//     }
//   }

//   // J-TYPE INSTRUCTIONS
//   it should "decode JAL instruction correctly" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // JAL x18, 1024
//       val inst = "b00000000010000000000100101101111".U
//       testInstruction(
//         inst = inst,
//         expectedOp = J_I_JAL,
//         expectedRdAddr = 18,
//         expectedImm = 1024,
//         expectedRfWen = true,
//         expectedInstType = InstructionTypes.J_TYPE
//       )
//     }
//   }

//   // I-TYPE JALR INSTRUCTIONS
//   it should "decode JALR instruction correctly" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // JALR x19, x20, 64
//       val inst = "b00000100000010100000100111100111".U
//       testInstruction(
//         inst = inst,
//         expectedOp = I_I_JALR,
//         expectedRdAddr = 19,
//         expectedRs1Addr = 20,
//         expectedImm = 64,
//         expectedRfWen = true,
//         expectedInstType = InstructionTypes.I_TYPE
//       )
//     }
//   }

//   // Illegal instruction tests
//   it should "flag illegal instructions for unknown opcodes" in {
//     test(new InstructionDecoder) { implicit dut =>
//       val inst = "b00000000000000000000000000000000".U // All zeros
//       testInstruction(
//         inst = inst,
//         expectedOp = 0.U,
//         expectedIllegal = true
//       )
//     }
//   }

//   it should "flag illegal I-TYPE1 instructions with invalid funct3" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // Invalid ADDI-like instruction with funct3=7'b1000
//       val inst = "b00000000000100000100000010010011".U
//       dut.io.inst.poke(inst)
//       dut.io.illegal.expect(true.B)
//     }
//   }

//   // Edge cases
//   it should "handle maximum positive immediate correctly" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // ADDI x21, x22, 2047 (max positive 12-bit immediate)
//       val inst = "b01111111111110110000101010010011".U
//       testInstruction(
//         inst = inst,
//         expectedOp = I_I_ADDI,
//         expectedRdAddr = 21,
//         expectedRs1Addr = 22,
//         expectedImm = 2047,
//         expectedRfWen = true,
//         expectedInstType = InstructionTypes.I_TYPE
//       )
//     }
//   }

//   it should "handle maximum negative immediate correctly" in {
//     test(new InstructionDecoder) { implicit dut =>
//       // ADDI x23, x24, -2048 (max negative 12-bit immediate)
//       val inst = "b10000000000011000000101110010011".U
//       testInstruction(
//         inst = inst,
//         expectedOp = I_I_ADDI,
//         expectedRdAddr = 23,
//         expectedRs1Addr = 24,
//         expectedImm = BigInt("FFFFF800", 16), // -2048 sign-extended
//         expectedRfWen = true,
//         expectedInstType = InstructionTypes.I_TYPE
//       )
//     }
//   }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 