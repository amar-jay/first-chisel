error id: file://<WORKSPACE>/riscv/core.scala:17
file://<WORKSPACE>/riscv/core.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -decoder.
	 -scala/Predef.decoder.
offset: 567
uri: file://<WORKSPACE>/riscv/core.scala
text:
```scala
import riscv.{InstructionFetcher, RegisterFile, ALU, Constants}
class RISCVCore extends Module {
	val io = IO(new Bundle {
		val clk = Input(Clock())
		val reset = Input(Bool())
		val inst = Input(UInt(32.W))
		val pc = Input(UInt(32.W)) // Program Counter. what is its size? 32 bits?
		// val pc = Input(UInt(32.W))
	})

	val inst_fetcher = Module(new InstructionFetcher())	
	val regfile = Module(new RegisterFile())
	val alu = Module(new ALU())

	// Connect the instruction fetcher to the register file
	decoder.io.clk := io.clk
	decoder.io.reset := io.reset
	decod@@er.io.instruction := io.inst
	decoder.io.pc := io.pc

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