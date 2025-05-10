error id: file://<WORKSPACE>/riscv/decoder.scala:58
file://<WORKSPACE>/riscv/decoder.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -constants/Opcodes.
	 -Opcodes.
	 -scala/Predef.Opcodes.
offset: 1782
uri: file://<WORKSPACE>/riscv/decoder.scala
text:
```scala
// instruction decoder for RISC-V
import chisel3.util.Fill
import chisel3.util.is
import chisel3.util.switch
import chisel3.when


class InstructionDecoder extends Module {
	import constants._
	val io = IO(new Bundle {
		val inst = Input(UInt(32.W))
		val pc = Input(UInt(7.W))

		val rd_addr   = Output(UInt(5.W))
		val rs1_addr  = Output(UInt(5.W))
		val rs2_addr  = Output(UInt(5.W))
		val imm       = Output(UInt(32.W))
		val alu_op    = Output(UInt(4.W))
		val br_type   = Output(UInt(3.W))
		val mem_type  = Output(UInt(2.W))
		val mem_size  = Output(UInt(3.W)) // memory size
		val wb_sel    = Output(Bool()) // write back select
		val rf_wen    = Output(Bool()) // register file write enable
		val csr_cmd   = Output(UInt(3.W)) // CSR command
		val illegal   = Output(Bool()) // illegal instruction flag
		// instruction type uses the InstructionTypes enum
		val inst_type = Output(InstructionTypes())
	})

	io.rd_addr := io.inst(11, 7)
	io.rs1_addr := io.inst(19, 15)
	io.rs2_addr := io.inst(24, 20)
	io.imm := 0.U
	io.alu_op := 0.U
	io.br_type := 0.U
	io.mem_type := 0.U
	io.mem_size := 0.U
	io.wb_sel := false.B
	io.rf_wen := false.B
	io.csr_cmd := 0.U
	io.illegal := false.B
	io.inst_type := 0.U


	val opcode:Opcodes = io.inst(6, 0)
	val funct3 = io.inst(14, 12)
	val funct7 = io.inst(31, 25)

	// immediate value extraction
	val i_imm = Cat(Fill(21, io.inst(31)), io.inst(30, 20))
	val s_imm = Cat(Fill(21, io.inst(31)), io.inst(30, 25), io.inst(11, 7))
	val b_imm = Cat(Fill(20, io.inst(31)), io.inst(7), io.inst(30, 25), io.inst(11, 8), 0.U(1.W))
	val u_imm = Cat(io.inst(31, 12), 0.U(12.W))
	val j_imm = Cat(Fill(12, io.inst(31)), io.inst(19, 12), io.inst(20), io.inst(30, 21), 0.U(1.W))


	// Decode the instruction based on the opcode
	switch (opcode) {
		is(Opco@@des.LOAD) {
			io.inst_type := InstructionTypes.I_TYPE 
			io.imm := i_imm
			io.alu_op := Operations.ALU_ADD
			io.mem_type := Operations.MEM_LOAD
			io.wb_sel := true.B
			io.rf_wen := true.B

			switch (funct3) {
				is("b000".U) { io.mem_size := MemorySizes.BYTE }
				is("b001".U) { io.mem_size := MemorySizes.HALF_WORD }
				is("b010".U) { io.mem_size := MemorySizes.WORD }
				is("b100".U) { io.mem_size := MemorySizes.BYTE_UNSIGNED }
				is("b101".U) { io.mem_size := MemorySizes.HALF_WORD_UNSIGNED }
			}
		}

		is(Opcodes.OP_IMM) {
			io.inst_type := InstructionTypes.I_TYPE
			io.imm := i_imm
			io.rf_wen := true.B

			switch (funct3) {
				is("b000".U) { io.alu_op := Operations.ALU_ADD }
				is("b001".U) { io.alu_op := Operations.ALU_SLL }
				is("b010".U) { io.alu_op := Operations.ALU_SLT }
				is("b011".U) { io.alu_op := Operations.ALU_SLTU }
				is("b100".U) { io.alu_op := Operations.ALU_XOR }
				is("b101".U) {
					when (funct7 === "b0100000".U) { // can use when(funct7(5)) too but this is more readable
						io.alu_op := Operations.ALU_SRA
					}.otherwise {
						io.alu_op := Operations.ALU_SRL
					}
				}
				is("b110".U) { io.alu_op := Operations.ALU_OR }
				is("b111".U) { io.alu_op := Operations.ALU_AND }
			}
		}

		is(Opcodes.AUIPC) {
			io.inst_type := InstructionTypes.U_TYPE
			io.imm := u_imm
			io.alu_op := Operations.ALU_ADD
			io.rs1_addr := 0.U // AUIPC does not use rs1
			io.rf_wen := true.B
		}

		is(Opcodes.STORE) {
			io.inst_type := InstructionTypes.S_TYPE
			io.imm := s_imm
			io.alu_op := Operations.ALU_ADD
			io.mem_type := Operations.MEM_STORE
			io.rf_wen := false.B

			switch (funct3) {
				is("b000".U) { io.mem_size := MemorySizes.BYTE }
				is("b001".U) { io.mem_size := MemorySizes.HALF_WORD }
				is("b010".U) { io.mem_size := MemorySizes.WORD }
			}
		}
		is(Opcodes.OP) {
			io.inst_type := InstructionTypes.R_TYPE
			io.rf_wen := true.B

			switch(funct3) {
				//TODO: doo it later
			}
		}
		is(Opcodes.LUI) {

			io.inst_type := InstructionTypes.U_TYPE
			io.imm := u_imm
			io.alu_op := Operations.ALU_COPY2
			io.rs1_addr := 0.U // LUI does not use rs1
			io.rf_wen := true.B
		}

		is(Opcodes.BRANCH) {

			io.inst_type := InstructionTypes.B_TYPE
			io.imm := b_imm
			io.alu_op := Operations.ALU_ADD

			switch (funct3) {
				is("b000".U) { io.br_type := Operations.BR_EQ }
				is("b001".U) { io.br_type := Operations.BR_NE }
				is("b100".U) { io.br_type := Operations.BR_LT }
				is("b101".U) { io.br_type := Operations.BR_GE }
				is("b110".U) { io.br_type := Operations.BR_LT_UNSIGNED }
				is("b111".U) { io.br_type := Operations.BR_GE_UNSIGNED }
			}

		}
		is(Opcodes.JAL) {
			io.inst_type := InstructionTypes.J_TYPE
			io.imm := j_imm
			io.alu_op := Operations.ALU_ADD
			io.br_type := Operations.BR_JAL
			io.wb_sel := true.B
			io.rf_wen := true.B
		}
		is(Opcodes.JALR) {
			io.inst_type := InstructionTypes.I_TYPE
			io.imm := i_imm
			io.alu_op := Operations.ALU_ADD
			io.br_type := Operations.BR_JALR
			io.wb_sel := true.B
			io.rf_wen := true.B
		}
		is(Opcodes.SYSTEM) {
			io.inst_type := InstructionTypes.I_TYPE
			io.imm := i_imm
			io.alu_op := Operations.ALU_ADD
			io.wb_sel := true.B
			io.rf_wen := true.B

			when (funct3 === 0.U){
				// ECALL, EBREAK
				when (funct7 === 0.U) {
					io.csr_cmd := CSRCommands.ECALL
				}.elsewhen (funct7 === 1.U) {
					io.csr_cmd := CSRCommands.EBREAK
				}
			}

			switch (funct3) {
				is("b000".U) { io.csr_cmd := CSRCommands.CSRRW }
				is("b010".U) { io.csr_cmd := CSRCommands.CSRRS }
				is("b011".U) { io.csr_cmd := CSRCommands.CSRRC }
				is("b101".U) { io.csr_cmd := CSRCommands.CSRRWI }
				is("b110".U) { io.csr_cmd := CSRCommands.CSRRSI }
				is("b111".U) { io.csr_cmd := CSRCommands.CSRRCI }
			}

		}
		is(Opcodes.MISC_MEM) {
			io.inst_type := InstructionTypes.I_TYPE
			// FENCE, FENCE.I - basically NOPs in this implementation
		}

		// otherwise, set illegal flag
		default {
			io.illegal := true.B
		}
	}
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 