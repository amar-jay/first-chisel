error id: file://<WORKSPACE>/riscv/constants.scala:64
file://<WORKSPACE>/riscv/constants.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 1889
uri: file://<WORKSPACE>/riscv/constants.scala
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

object Operations extends ChiselEnum {
	// ALU Operations
	val ALU_ADD = 0.U(4.W)
	val ALU_SUB = 1.U(4.W)
	val ALU_SLL = 2.U(4.W)
	val ALU_SLT = 3.U(4.W)
	val ALU_SLTU = 4.U(4.W)
	val ALU_XOR = 5.U(4.W)
	val ALU_SRL = 6.U(4.W)
	val ALU_SRA = 7.U(4.W)
	val ALU_OR = 8.U(4.W)
	val ALU_AND = 9.U(4.W)
	// n@@ot sure about this
	val ALU_COPY2 = 10.U(4.W)
	val ALU_SUBI = 11.U(4.W)



	// memory operations
	val MEM_LOAD = 20.U(4.W)
	val MEM_STORE = 21.U(4.W)
	// not sure about this
	val MEM_LW = 22.U(4.W)


	// control operations
	val CTRL_NOP = 23.U(4.W)
	val CTRL_WRITE = 24.U(4.W)
	val CTRL_SET = 25.U(4.W)
	val CTRL_FLUSH = 26.U(4.W)
	val CTRL_IMMEDIATE = 27.U(4.W)

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
```


#### Short summary: 

empty definition using pc, found symbol in pc: 