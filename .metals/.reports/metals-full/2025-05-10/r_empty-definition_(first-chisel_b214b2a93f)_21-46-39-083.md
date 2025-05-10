error id: file://<WORKSPACE>/riscv/core.scala:30
file://<WORKSPACE>/riscv/core.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -dec_op1.
	 -dec_op1#
	 -dec_op1().
	 -scala/Predef.dec_op1.
	 -scala/Predef.dec_op1#
	 -scala/Predef.dec_op1().
offset: 981
uri: file://<WORKSPACE>/riscv/core.scala
text:
```scala
import riscv.{InstructionFetcher, RegisterFile, ALU, Constants}
import chisel3.Mux
import riscv.InstructionTypes
class RISCVCore extends Module {
	val io = IO(new Bundle {
		val inst = Input(UInt(32.W))
		// val pc = Input(UInt(32.W)) // TODO: Program Counter. what is its size? 32 bits? is it necessary in chisel?
  		val rddata = Output(SInt(32.W))
	})

	val inst_fetcher = Module(new InstructionFetcher())	
	val regfile = Module(new RegisterFile())
	val alu = Module(new ALU())

	// Connect the instruction fetcher to the register file
	inst_fetcher.io.clk := io.clk
	inst_fetcher.io.inst := io.inst


	val dec_rd_addr = inst_fetcher.io.rd_addr
	val dec_inst_rs1 = inst_fetcher.io.rs1_addr
	val dec_inst_rs2 = inst_fetcher.io.rs2_addr
	val op0 = Wire(SInt(32.W))
	val op1 = Wire(SInt(32.W))
	regfile.io.read0.en   := true.B
	regfile.io.read0.addr := dec_inst_rs1
	regfile.io.read1.en   := true.B
	regfile.io.read1.addr := dec_inst_rs2

	dec_op0	  := regfile.io.read0.data
	dec_o@@p1	  := regfile.io.read1.data


	val jal_en = Wire(Bool())
	jal_en := (inst_fetcher.io.inst_type === InstructionTypes.J_TYPE && inst_fetcher.io._op === Instructions.J_I_JAL)

	val jalr_en = Wire(Bool())
	jalr_en := inst_fetcher.io.inst_type === InstructionTypes.I_TYPE && inst_fetcher.io._op === Instructions.I_JALR


	alu.io.op0 := dec_rdata_op0
	alu.io.op1 := Mux(inst_fetcher.io.inst_type === InstructionTypes.I_TYPE, rd_data, dec_op1)
	alu.io.op := mapping(inst_fetcher.io._op)


	// Connect the instruction fetcher to the ALU

	regfile.io.read0.en   := true.B
	regfile.io.read0.addr := dec_inst_rs1
	dec_rdata_op0     := regfile.io.read0.data

	regfile.io.read0.en   := true.B
	regfile.io.read0.addr := dec_inst_rs2
	dec_rdata_op1     := regfile.io.read1.data
}


// Main entry point for generating Verilog
object RiscVGenerator extends App {
  chisel3.Driver.execute(args, () => new RiscVSystem)
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 