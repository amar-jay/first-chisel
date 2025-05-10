error id: file://<WORKSPACE>/riscv/register.scala:59
file://<WORKSPACE>/riscv/register.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 2236
uri: file://<WORKSPACE>/riscv/register.scala
text:
```scala
//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using dep "edu.berkeley.cs::chiseltest:6.0.0"  // ✅ Chisel test lib
//> using plugin "org.chipsalliance:::chisel-plugin:6.7.0"
//> using options "-unchecked","-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations"
package riscv

import chisel3._
import chisel3.util._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import _root_.circt.stage.ChiselStage
import riscv.{Opcodes, InstructionTypes}
import riscv.Instructions._

import riscv.{Constants, Opcodes, InstructionTypes}


class ReadPortIo(xlen: Int, mem_add_len:Int) extends Bundle {
  val en    = Input(Bool())
  val addr  = Input(UInt(mem_add_len.W))
  val data  = Output(SInt(xlen.W))
}

class WritePortIo(xlen: Int, mem_add_len:Int) extends Bundle {
  val en    = Input(Bool())
  val addr  = Input(UInt(mem_add_len.W))
  val data  = Input(SInt(xlen.W))
}

class RegIo(xlen: Int = Constants.DATA_WIDTH, numRegs: Int = 32) extends Bundle {
  val read0 = new ReadPortIo(xlen, log2Ceil(numRegs))
  val read1 = new ReadPortIo(xlen, log2Ceil(numRegs))
  val write = Decoupled(new WritePortIo(xlen, log2Ceil(numRegs)))

  // Debug port for visualization and testing
  //val debugRegState = Output(Vec(numRegs, SInt(xlen.W)))
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
 * @param conf Configuration parameters that exte@@nd RVConfig, determining properties like XLEN
 */

class RegisterFile(
	xlen: Int = Constants.DATA_WIDTH,
	numRegs: Int = 32
) extends Module {
  require(numRegs >= 32, "RISC-V requires at least 32 registers")
  val io = IO(new RegIo(xlen, numRegs))
  // val registers = Mem(numRegs, SInt(xlen.W))

  // Better logic structure for handling register zero. Data comes on same cycle as write
  // and read. This is a common pattern in RISC-V register files.
  val registers = RegInit(VecInit(Seq.fill(numRegs)(0.S(xlen.W))))

  // Helper function for read port logic to reduce duplication
  def readPort(en: Bool, addr: UInt, writeEn: Bool, writeAddr: UInt, writeData: SInt): SInt = {
    val isReg0 = addr === 0.U
    val isWriteConflict = writeEn && (addr === writeAddr)
    
    Mux(
      !en || isReg0, 
      0.S(xlen.W),                // Reading disabled or x0 -> return 0
      Mux(
        isWriteConflict,
        writeData,                // Handle read-after-write in same cycle
        registers(addr)           // Normal register read
      )
    )
  }

  io.read0.data := 0.S(xlen.W)
  io.read1.data := 0.S(xlen.W)
  // Read port 0
  when (io.read0.en && (io.read0.addr =/= 0.U)) {
    io.read0.data := Mux(io.write.en & (io.write.addr === io.read0.addr), 
                      io.write.data, 
                          registers(io.read0.addr))
  } .otherwise {
    io.read0.data := 0.S(xlen.W)
  }

  // Read port 1
  when (io.read1.en && (io.read1.addr =/= 0.U)) {
    io.read1.data := Mux(io.write.en & (io.write.addr === io.read1.addr),
                          io.write.data, 
                          registers(io.read1.addr))
  } .otherwise {
    io.read1.data := 0.S(xlen.W)
  }

  // Write port
  when (io.write.en && (io.write.addr =/= 0.U)) {
    registers(io.write.addr) := io.write.data
  }

  //   // Connect debug interface
  //   for (i <- 0 until numRegs) {
  //     io.debugRegState(i) := registers(i)
  //   }
  
  // Design assertions
  if (!chisel3.Implicits.SourceInfo(implicitly).isImplicit()) {
    assert(registers(0) === 0.S, "Register x0 must always be zero")
    assert(!io.write.en || io.write.addr =/= 0.U || io.write.data === 0.S, 
           "Cannot write non-zero value to register x0")
  }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 