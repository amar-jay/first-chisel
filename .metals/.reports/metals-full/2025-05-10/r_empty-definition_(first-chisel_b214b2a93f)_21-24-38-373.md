error id: file://<WORKSPACE>/riscv/core.scala:18
file://<WORKSPACE>/riscv/core.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -ex_rs2_addr.
	 -ex_rs2_addr#
	 -ex_rs2_addr().
	 -scala/Predef.ex_rs2_addr.
	 -scala/Predef.ex_rs2_addr#
	 -scala/Predef.ex_rs2_addr().
offset: 675
uri: file://<WORKSPACE>/riscv/core.scala
text:
```scala
import riscv.{InstructionFetcher, RegisterFile, ALU, Constants}
class RISCVCore extends Module {
	val io = IO(new Bundle {
		val inst = Input(UInt(32.W))
		val pc = Input(UInt(32.W)) // TODO: Program Counter. what is its size? 32 bits? is it necessary in chisel?
		// val pc = Input(UInt(32.W))
	})

	val inst_fetcher = Module(new InstructionFetcher())	
	val regfile = Module(new RegisterFile())
	val alu = Module(new ALU())

	// Connect the instruction fetcher to the register file
	inst_fetcher.io.clk := io.clk
	inst_fetcher.io.instruction := io.inst

	val ex_rd_addr = RegNext(inst_fetcher.io.rd_addr)
	val ex_rs1_addr = RegNext(inst_fetcher.io.rs1_addr)
	val ex_rs2_addr@@ = RegNext(inst_fetcher.io.rs2_addr)
	val rs1_data = 
	val dec_rdata_op0 = Wire(SInt(32.W))
	val dec_rdata_op1 = Wire(SInt(32.W))
	val dec_inst_rs1 = inst_fetcher.io.rs1_addr
	val dec_inst_rs2 = inst_fetcher.io.rs2_addr
	val 
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