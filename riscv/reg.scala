import chisel3.IO
import org.yaml.snakeyaml.scanner.Constant
import chisel3.util.switch
import chisel3.util.is
class MemoryInterface extends Bundle {
	val io = IO(new Bundle {
		val addr = Input(UInt(Constants.ADDR_WIDTH.W))
		val data_in = Input(UInt(Constants.DATA_WIDTH.W))
		val data_out = Input(Bool())
		val mem_type = Input(MemoryTypes())
		val mem_size_in = Input(MemorySizes())
		val rdata = Output(UInt(32.W))

		val mem_addr = Output(UInt(Constants.ADDR_WIDTH.W))
		val mem_wdata = Output(UInt(Constants.DATA_WIDTH.W))
		val mem_rdata = Input(UInt(Constants.DATA_WIDTH.W))
		val mem_wen = Output(Bool())
		val mem_ren = Output(Bool())
		val mem_size_out = Output(MemorySizes())
	})

	// initialization
	io.mem_addr := 0.U
	io.mem_wdata := 0.U
	io.mem_wen := false.B
	io.mem_ren := false.B
	io.mem_size_out := MemorySizes.BYTE


	switch(io.mem_type) {
		is(MemoryTypes.STORE) {
			io.mem_wen := true.B

			switch(io.mem_size_in) {
				is(MemorySizes.BYTE) {
				}
				is(MemorySizes.HALF) {
				}
				is(MemorySizes.WORD) {
				}
			}
			io.mem_addr := io.addr
			io.mem_wdata := io.data_in
			io.mem_size_out := io.mem_size_in
		}
		is(MemoryTypes.LOAD) {
			io.mem_ren := true.B


			switch(io.mem_size_in) {
				is(MemorySizes.BYTE) {
				}
				is(MemorySizes.HALF) {
				}
				is(MemorySizes.WORD) {
				}
				is(MemorySizes.BYTE_UNSIGNED) {
				}
				is(MemorySizes.HALF_UNSIGNED) {
				}
			}
		}
	}
}