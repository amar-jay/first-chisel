error id: file://<WORKSPACE>/riscv/core.scala:19
file://<WORKSPACE>/riscv/core.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -regfile/io/read0.
	 -scala/Predef.regfile.io.read0.
offset: 650
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

	val dec_inst = inst_fetcher.io.r
	// Connect the instruction fetcher to the ALU

	regfile.io.r@@ead0.en   := true.B
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