error id: file://<WORKSPACE>/riscv/constants.scala:[1594..1601) in Input.VirtualFile("file://<WORKSPACE>/riscv/constants.scala", "//> using scala "2.13.12"
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

object  extends ChiselEnum {
	// ALU Operations
	val ALU_ADD,
	ALU_SUB,
	ALU_XOR,
	ALU_OR,
	ALU_AND,
	ALU_SLL,
	ALU_SRL,
	ALU_SRA,
	ALU_SLT,
	ALU_SLTU,

	// not sure about this
	ALU_ADDI,
	ALU_XORI,
	ALU_ORI,
	ALU_ANDI,
	ALU_SLLI,
	ALU_SRLI,
	ALU_SLTI,
	ALU_SLTIU, // not sure about this

	ALU_LB,
	ALU_LH,
	ALU_LW,
	ALU_LBU,
	ALU_LHU,


	ALU_SB,
	ALU_SH,
	ALU_SW,


	ALU_BEQ,
	ALU_BNE,
	ALU_BLT,
	ALU_BGE,
	ALU_BLTU,
	ALU_BGEU,


	ALU_JAL,
	ALU_JALR,

	ALU_LUI,
	ALU_AUIPC,

	ALU_ECALL,
	ALU_EBREAK,



	// memory operations
	MEM_LOAD,
	MEM_STORE,
	// not sure about this
	MEM_LW,


	// control operations
	CTRL_NOP,
	CTRL_WRITE,
	CTRL_SET,
	CTRL_FLUSH,
	CTRL_IMMEDIATE = Value

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
}")
file://<WORKSPACE>/file:<WORKSPACE>/riscv/constants.scala
file://<WORKSPACE>/riscv/constants.scala:53: error: expected identifier; obtained extends
object  extends ChiselEnum {
        ^
#### Short summary: 

expected identifier; obtained extends