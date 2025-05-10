error id: file://<WORKSPACE>/riscv/constants.scala:9
file://<WORKSPACE>/riscv/constants.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 253
uri: file://<WORKSPACE>/riscv/constants.scala
text:
```scala
package riscv
import chisel3._
import chisel3.util._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import _root_.circt.stage.ChiselStage

SUPPORT_MUL = true // set to false if you don't want to support multiplication
object CONFIG {
	val@@ SUPPORT_MUL = true
}

object Constants {
  val NUM_CHANNELS = 4
  val NUM_CONSUMERS = 4
  val DATA_WIDTH = 32
  val ADDR_WIDTH = 32
  val CHANNEL_BW = 8
  val CONSUMER_BW = 8
}

object InstructionTypes extends ChiselEnum {
	val NOP, R_TYPE, I_TYPE, S_TYPE, B_TYPE, U_TYPE, J_TYPE, R4_TYPE = Value
}

object Opcodes extends ChiselEnum {
	val R_TYPE = "b0110011".U
	val I_TYPE1 = "b0010011".U // ALU immediate operations
	val I_TYPE2 = "b0000011".U // LOAD operations
	val I_TYPE3 = "b1100111".U // JUMP operations
	val I_TYPE4 = "b1110011".U // CSR operations
	val S_TYPE = "b0100011".U
	val B_TYPE = "b1100011".U
	val U_TYPE1 = "b0110111".U // LUI
	val U_TYPE2 = "b0010111".U // AUIPC
	val J_TYPE = "b1101111".U
}

// will never be used. just for the sake of completeness
private[riscv] trait I_Instructions;

object Instructions extends ChiselEnum {
	val NOP = Value

	// ALU Instructions	
	val R_I_ADD,
	R_I_SUB,
	R_I_XOR,
	R_I_OR,
	R_I_AND,
	R_I_SLL,
	R_I_SRL,
	R_I_SRA,
	R_I_SLT,
	R_I_SLTU = Value

	// multiply operations
	val R_M_MUL,
	R_M_MULH,
	R_M_MULHSU,
	R_M_MULHU,
	R_M_DIV,
	R_M_DIVU,
	R_M_REM,
	R_M_REMU = Value

	// ALU immediate operations

	val I_I_ADDI,
	I_I_XORI,
	I_I_ORI,
	I_I_ANDI,
	I_I_SLLI,
	I_I_SRLI,
	I_I_SRAI, // not sure about this
	I_I_SLTI,
	I_I_SLTIU = Value

	// LOAD operations
	val I_I_LB,
	I_I_LH,
	I_I_LW,
	I_I_LBU,
	I_I_LHU = Value

	// CSR operations
	val I_I_ECALL,
	I_I_EBREAK = Value

	// JUMP operations
	val I_I_JALR = Value

	// store operations
	val S_I_SB,
	S_I_SH,
	S_I_SW = Value


	// branch operations
	val B_I_BEQ,
	B_I_BNE,
	B_I_BLT,
	B_I_BGE,
	B_I_BLTU,
	B_I_BGEU = Value

	// jump operations
	val J_I_JAL = Value


	val U_I_LUI,
	U_I_AUIPC = Value
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


object ALUOperations extends ChiselEnum {
	val ADD, SUB, SLL, SLT, SLTU, XOR, SRL, SRA, OR, AND = Value
	
	if (SUPPORT_MUL) {
		val MUL, DIV, REM = Value
	}
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

object CSRRegisters extends ChiselEnum {
	val MSTATUS, MEPC, MCAUSE, MTVAL, MIP, MIE, MTVEC = Value
}
object CSRRegistersWidth extends ChiselEnum {
	val MSTATUS_WIDTH = 12.U
	val MEPC_WIDTH = 12.U
	val MCAUSE_WIDTH = 12.U
	val MTVAL_WIDTH = 12.U
	val MIP_WIDTH = 12.U
	val MIE_WIDTH = 12.U
	val MTVEC_WIDTH = 12.U
}

object CSRRegistersAddr extends ChiselEnum {
	val MSTATUS = 0x300.U
	val MEPC = 0x341.U
	val MCAUSE = 0x342.U
	val MTVAL = 0x343.U
	val MIP = 0x344.U
	val MIE = 0x305.U
	val MTVEC = 0x305.U
}



```


#### Short summary: 

empty definition using pc, found symbol in pc: 