error id: DATA_WIDTH
file://<WORKSPACE>/riscv/register.scala
empty definition using pc, found symbol in pc: 
semanticdb not found

found definition using fallback; symbol DATA_WIDTH
offset: 158
uri: file://<WORKSPACE>/riscv/register.scala
text:
```scala
import chisel3._
import chisel3.util._
import chisel3.Bool
import riscv.{Constants, Opcodes, InstructionTypes}

class RegIo(dataWidth:Int = Constants.DATA_WID@@TH, addrWidth:Int = Constants.MEM_ADDR_WIDTH) extends Bundle {
  val rden0   = Input  (Bool())
  val rdaddr0 = Input  (UInt(5.W))
  val rddata0 = Output (SInt(64.W))

  val rden1   = Input  (Bool())
  val rdaddr1 = Input  (UInt(5.W))
  val rddata1 = Output (SInt(64.W))

  val wren   = Input  (Bool())
  val wraddr = Input  (UInt(5.W))
  val wrdata = Input  (SInt(64.W))
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

class Regs [Conf <: RVConfig](conf: Conf) extends Module {
  val io = IO (new RegIo)

  // val r_regs = RegInit( Vec(32, SInt(conf.xlen.W)).asTypeOf(0.U) )
  val r_regs = Mem(32, SInt(Constants.DATA_WIDTH.W))

  when (io.rden0 && (io.rdaddr0 =/= 0.U)) {
    io.rddata0 := Mux (io.wren & (io.wraddr === io.rdaddr0), io.wrdata, r_regs(io.rdaddr0))
  } .otherwise {
    io.rddata0 := 0.S(conf.xlen.W)
  }

  when (io.rden1 && (io.rdaddr1 =/= 0.U)) {
    io.rddata1 := Mux (io.wren & (io.wraddr === io.rdaddr1), io.wrdata, r_regs(io.rdaddr1))
  } .otherwise {
    io.rddata1 := 0.S(conf.xlen.W)
  }

  when (io.wren && (io.wraddr =/= 0.U)) {
    r_regs(io.wraddr) := io.wrdata
  }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 