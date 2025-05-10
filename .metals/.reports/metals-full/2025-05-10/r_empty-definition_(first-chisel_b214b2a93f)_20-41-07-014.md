error id: file://<WORKSPACE>/riscv/core.scala:14
file://<WORKSPACE>/riscv/core.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -BranchComparator#
	 -scala/Predef.BranchComparator#
offset: 450
uri: file://<WORKSPACE>/riscv/core.scala
text:
```scala
class RISCVCore extends Module {
	val io = IO(new Bundle {
		val clk = Input(Clock())
		val reset = Input(Bool())
		val mem = new MemoryInterface()
		val dispatch = new DispatchIO()
		val regfile = new RegisterFile()
		val alu = new ALU()
		val comparator = new BranchComparator()
	})

	val decoder = Module(new InstructionDecoder())	
	val regfile = Module(new RegisterFile())
	val alu = Module(new ALU())
	val comparator = Module(new BranchComparato@@r())
	val memif = Module(new MemoryInterface())
}


// Main entry point for generating Verilog
object RiscVGenerator extends App {
  chisel3.Driver.execute(args, () => new RiscVSystem)
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 