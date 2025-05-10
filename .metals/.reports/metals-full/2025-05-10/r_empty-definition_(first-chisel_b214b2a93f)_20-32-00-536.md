error id: file://<WORKSPACE>/riscv/register.scala:19
file://<WORKSPACE>/riscv/register.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 699
uri: file://<WORKSPACE>/riscv/register.scala
text:
```scala
import chisel3._
import chisel3.util._
import chisel3.Bool
import riscv.{Constants, Opcodes, InstructionTypes}


class ReadPortIo(xlen: Int = Constants.DATA_WIDTH, mem_add_len:Int = Constants.MEM_ADDR_WIDTH) extends Bundle {
  val en    = Input(Bool())
  val addr  = Input(UInt(mem_add_len.W))
  val data  = Output(SInt(xlen.W))
}

class WritePortIo(xlen: Int = Constants.DATA_WIDTH, mem_add_len:Int = Constants.MEM_ADDR_WIDTH) extends Bundle {
  val en    = Input(Bool())
  val addr  = Input(UInt(mem_add_len.W))
  val data  = Input(SInt(xlen.W))
}

class RegIo(xlen: Int = Constants.DATA_WIDTH, mem_add_len:Int = Constants.MEM_ADDR_WIDTHxlen: Int, mem_add_len:Int) extends Bundle {
  val read0 = n@@ew ReadPortIo(xlen, mem_add_len)
  val read1 = new ReadPortIo(xlen, mem_add_len)
  val write = new WritePortIo(xlen, mem_add_len)
}
/**
 * RISC-V Register File Implementation
 * 
 * This module implements a 32-entry register file for a RISC-V processor with the
 * following features:
 *
 * - Two independent read ports with read-after-write hazard detection
 * - One write port
 * - Register x0 hardwired to zero per RISC-V specification
 * - Configurable width based on processor configuration
 * - Read-during-write behavior: returns new value if read and write addresses match
 *
 * Read ports:
 * - Each read port has an enable signal, address input, and data output
 * - When disabled or reading from x0, always returns 0
 * - Implements forwarding logic to handle read-after-write hazards in the same cycle
 *
 * Write port:
 * - Has an enable signal, address input, and data input
 * - Writes are ignored for register x0 to maintain hardwired zero
 *
 * @param conf Configuration parameters that extend RVConfig, determining properties like XLEN
 */

class Regs[Conf <: RVConfig](xlen: Int = Constants.DATA_WIDTH) extends Module {
  val io = IO(new RegIo(xlen))
  
  val r_regs = Mem(32, SInt(xlen.W))

  // Read port 0
  when (io.read0.en && (io.read0.addr =/= 0.U)) {
    io.read0.data := Mux(io.write.en & (io.write.addr === io.read0.addr), 
                          io.write.data, 
                          r_regs(io.read0.addr))
  } .otherwise {
    io.read0.data := 0.S(xlen.W)
  }

  // Read port 1
  when (io.read1.en && (io.read1.addr =/= 0.U)) {
    io.read1.data := Mux(io.write.en & (io.write.addr === io.read1.addr),
                          io.write.data, 
                          r_regs(io.read1.addr))
  } .otherwise {
    io.read1.data := 0.S(xlen.W)
  }

  // Write port
  when (io.write.en && (io.write.addr =/= 0.U)) {
    r_regs(io.write.addr) := io.write.data
  }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 