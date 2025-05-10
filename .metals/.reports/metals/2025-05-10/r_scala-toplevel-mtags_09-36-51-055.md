error id: file://<WORKSPACE>/riscv/constants.scala:[3941..3941) in Input.VirtualFile("file://<WORKSPACE>/riscv/constants.scala", "//> using scala "2.13.12"
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


object Constants {
  val NUM_CHANNELS = 4
  val NUM_CONSUMERS = 4
  val DATA_WIDTH = 32
  val ADDR_WIDTH = 32
  val CHANNEL_BW = 8
  val CONSUMER_BW = 8
}

object InstructionTypes extends ChiselEnum {
	val R_TYPE, I_TYPE, S_TYPE, B_TYPE, U_TYPE, J_TYPE, R4_TYPE = Value
}


object Opcodes {
  val LOAD      = "b0000011".U(7.W)
  val LOAD_FP   = "b0000111".U(7.W)
  val MISC_MEM  = "b0001111".U(7.W)
  val OP_IMM    = "b0010011".U(7.W)
  val AUIPC     = "b0010111".U(7.W)
  val OP_IMM_32 = "b0011011".U(7.W)
  val STORE     = "b0100011".U(7.W)
  val STORE_FP  = "b0100111".U(7.W)
  val AMO       = "b0101111".U(7.W)
  val OP        = "b0110011".U(7.W)
  val LUI       = "b0110111".U(7.W)
  val OP_32     = "b0111011".U(7.W)
  val MADD      = "b1000011".U(7.W)
  val MSUB      = "b1000111".U(7.W)
  val NMSUB     = "b1001011".U(7.W)
  val NMADD     = "b1001111".U(7.W)
  val OP_FP     = "b1010011".U(7.W)
  val BRANCH    = "b1100011".U(7.W)
  val JALR      = "b1100111".U(7.W)
  val JAL       = "b1101111".U(7.W)
  val SYSTEM    = "b1110011".U(7.W)
}

object R_Instructions extends ChiselEnum {
	// ALU Operations
	val R_ADD,
	R_SUB,
	R_XOR,
	R_OR,
	R_AND,
	R_SLL,
	R_SRL,
	R_SRA,
	R_SLT,
	R_SLTU = Value
}

object I_Instructions extends ChiselEnum {
	// not sure about this
	// ALU immediate operations
	val I_ADDI,
	I_XORI,
	I_ORI,
	I_ANDI,
	I_SLLI,
	I_SRLI,
	I_SLTI,
	I_SLTIU,

	// load operations
	I_LB,
	I_LH,
	I_LW,
	I_LBU,
	I_LHU,

	
	// CSR operations
	I_ECALL,
	I_EBREAK,
	
	// JUMP operations
	I_JALR = Value
}


object S_Instructions extends ChiselEnum {
	// store operations
	val S_SB,
	S_SH,
	S_SW = Value

}

object B_Instructions extends ChiselEnum {
	// branch operations
	val B_BEQ,
	B_BNE,
	B_BLT,
	B_BGE,
	B_BLTU,
	B_BGEU = Value
}


object J_Instructions extends ChiselEnum {
	// jump operations
	val J_JAL = Value
}

object U_Instructions extends ChiselEnum {
	val U_LUI,
	U_AUIPC = Value
}

object M_Instructions extends ChiselEnum {
	// multiply operations
	val M_MUL,
	M_MULH,
	M_MULHSU,
	M_MULHU,
	M_DIV,
	M_DIVU,
	M_REM,
	M_REMU = Value
}

object F_Instructions extends ChiselEnum {
	// floating point operations
	val  F_FLW,
	F_FSW,
	F_MADD,
	F_MSUB,
	F_NMSUB,
	// not sure about the ones above

	F_FADD,
	F_FSUB,
	F_FMUL,
	F_FDIV,
	F_FSQRT,
	F_FSGNJ,
	F_FSGNJN,
	F_FSGNJX,
	F_FMIN,
	F_FMAX,
	F_FEQ,
	F_FLT,
	F_FLE = Value
}

object Instructions extends ChiselEnum {
	// // memory operations
	// MEM_LOAD,
	// MEM_STORE,
	// // not sure about this
	// MEM_LW,


	// // control operations
	// CTRL_NOP,
	// CTRL_WRITE,
	// CTRL_SET,
	// CTRL_FLUSH,
	// CTRL_IMMEDIATE = Value

}

object BranchTypes extends ChiselEnum {
	val BR_EQ, BR_NE, BR_LT, BR_GE, BR_LTU, BR_GEU, BR_JAL, BR_JALR = Value
}


object MemorySizes extends ChiselEnum {
	val BYTE = 0.U
	val HALF_WORD = 1.U
	val WORD = 2.U
	val DOUBLE_WORD = 3.U
	val WORD_UNSIGNED = 4.U
	val HALF_WORD_UNSIGNED = 5.U
	val BYTE_UNSIGNED = 6.U
}

			// switch (funct3) {
			// 	is("b000".U) { io.csr_cmd := CSRCommands.CSRRW }
			// 	is("b001".U) { io.csr_cmd := CSRCommands.CSRRS }
			// 	is("b010".U) { io.csr_cmd := CSRCommands.CSRRC }
			// 	is("b101".U) { io.csr_cmd := CSRCommands.CSRRWI }
			// 	is("b110".U) { io.csr_cmd := CSRCommands.CSRRSI }
			// 	is("b111".U) { io.csr_cmd := CSRCommands.CSRRCI }
object CSRCommands extends ChiselEnum {
	val CSRRW, CSRRS, CSRRC, CSRRWI, CSRRSI, CSRRCI, ECALL, EBREAK = Value
}

object ")
file://<WORKSPACE>/file:<WORKSPACE>/riscv/constants.scala
file://<WORKSPACE>/riscv/constants.scala:204: error: expected identifier; obtained eof
object 
       ^
#### Short summary: 

expected identifier; obtained eof