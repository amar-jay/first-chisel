error id: file://<WORKSPACE>/riscv/reg.scala:35
file://<WORKSPACE>/riscv/reg.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -MemorySizes.
	 -MemorySizes#
	 -MemorySizes().
	 -scala/Predef.MemorySizes.
	 -scala/Predef.MemorySizes#
	 -scala/Predef.MemorySizes().
offset: 1097
uri: file://<WORKSPACE>/riscv/reg.scala
text:
```scala
import chisel3.IO
import org.yaml.snakeyaml.scanner.Constant
class RegisterFile extends Module {
	import constants._

	val io = IO(new Bundle {
		val rs1_addr = Input(UInt(5.W))
		val rs2_addr = Input(UInt(5.W))
		val rd_addr = Input(UInt(5.W))
		val rd_data = Input(UInt(Constants.DATA_WIDTH.W))
		val rd_wen = Input(Bool())
		val rs1_data = Output(UInt(Constants.DATA_WIDTH.W))
		val rs2_data = Output(UInt(Constants.DATA_WIDTH.W))
	})

	val registers = RegInit(VecInit(Seq.fill(32)(0.U(Constants.DATA_WIDTH.W))))

	// on read, output the data
	io.rs1_data := Mux(io.rs1_addr === 0.U, 0.U, registers(io.rs1_addr))
	io.rs2_data := Mux(io.rs2_addr === 0.U, 0.U, registers(io.rs2_addr))


	// on write, if rd_wen is high, write to the register
	when(io.rd_wen && io.rd_addr =/= 0.U) {
		registers(io.rd_addr) := io.rd_data
	}
}


class MemoryInterface extends Bundle {
	val io = IO(new Bundle {
		val addr = Input(UInt(Constants.ADDR_WIDTH.W))
		val data_in = Input(UInt(Constants.DATA_WIDTH.W))
		val data_out = Input(Bool())
		val mem_type = Input(MemoryTypes())
		val mem_size = Input(MemorySize@@s())
		val rdata = Output(UInt(32.W))
	})
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 