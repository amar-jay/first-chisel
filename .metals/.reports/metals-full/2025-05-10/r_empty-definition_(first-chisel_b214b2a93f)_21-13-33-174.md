error id: java/lang/Module#
file://<WORKSPACE>/riscv/core.scala
empty definition using pc, found symbol in pc: java/lang/Module#
semanticdb not found
empty definition using fallback
non-local guesses:
	 -Module.
	 -Module#
	 -Module().
	 -scala/Predef.Module.
	 -scala/Predef.Module#
	 -scala/Predef.Module().
offset: 287
uri: file://<WORKSPACE>/riscv/core.scala
text:
```scala
import riscv.{InstructionFetcher, RegisterFile, ALU, Constants}
class RISCVCore extends Module {
	val io = IO(new Bundle {
		val inst = Input(UInt(32.W))
		val pc = Input(UInt(32.W)) // Program Counter. what is its size? 32 bits?
		// val pc = Input(UInt(32.W))
	})

	val inst_fetcher = @@Module(new InstructionFetcher())	
	val regfile = Module(new RegisterFile())
	val alu = Module(new ALU())

	// Connect the instruction fetcher to the register file
	inst_fetcher.io.clk := io.clk
	inst_fetcher.io.instruction := io.inst

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

empty definition using pc, found symbol in pc: java/lang/Module#