error id: file://<WORKSPACE>/riscv/core.scala:6
file://<WORKSPACE>/riscv/core.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -InstructionDecoder#
	 -scala/Predef.InstructionDecoder#
offset: 154
uri: file://<WORKSPACE>/riscv/core.scala
text:
```scala
class RISCVCore extends Module {
	val io = IO(new Bundle {
		val clk = Input(Clock())
		val reset = Input(Bool())
	})

	val decoder = Module(new Instructi@@onDecoder())	
	val regfile = Module(new RegisterFile())
	val alu = Module(new ALU())
	// val comparator = Module(new BranchComparator())
	// val memif = Module(new MemoryInterface())
	val regfile = Module(new RegisterFile())
}


// Main entry point for generating Verilog
object RiscVGenerator extends App {
  chisel3.Driver.execute(args, () => new RiscVSystem)
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 