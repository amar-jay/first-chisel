error id: file://<WORKSPACE>/riscv/constants.scala:[855..861) in Input.VirtualFile("file://<WORKSPACE>/riscv/constants.scala", "//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using dep "edu.berkeley.cs::chiseltest:6.0.0"  // ✅ Chisel test lib
//> using plugin "org.chipsalliance:::chisel-plugin:6.7.0"
//> using options "-unchecked","-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations"

import chisel3._
import chisel3.util._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import _root_.circt.stage.ChiselStage


object Constatnts {
  val NUM_CHANNELS = 4
  val NUM_CONSUMERS = 4
  val DATA_WIDTH = 32
  val ADDR_WIDTH = 32
  val CHANNEL_BW = 8
  val CONSUMER_BW = 8
}

object InstructionTypes {
	val R_TYPE = 0.U(7.W)
	val I_TYPE = 1.U(7.W)
	val S_TYPE = 2.U(7.W)
	val B_TYPE = 3.U(7.W)
	val U_TYPE = 4.U(7.W)
	val J_TYPE = 5.U(7.W)
}


object 
object Operations {
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
	// not sure about this
	val ALU_ADDI = 10.U(4.W)
	val ALU_SUBI = 11.U(4.W)


	// branch operations
	val BR_EQ = 12.U(4.W)
	val BR_NE = 13.U(4.W)
	val BR_LT = 14.U(4.W)
	val BR_GE = 15.U(4.W)
	val BR_LTU = 16.U(4.W)
	val BR_GEU = 17.U(4.W)
	val BR_JAL = 18.U(4.W)
	val BR_JALR = 19.U(4.W)

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
")
file://<WORKSPACE>/file:<WORKSPACE>/riscv/constants.scala
file://<WORKSPACE>/riscv/constants.scala:34: error: expected identifier; obtained object
object Operations {
^
#### Short summary: 

expected identifier; obtained object