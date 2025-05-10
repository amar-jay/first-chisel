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
	val comparator = Module(new BranchComparator())
	val memif = Module(new MemoryInterface())
}


// Main entry point for generating Verilog
object RiscVGenerator extends App {
  chisel3.Driver.execute(args, () => new RiscVSystem)
}