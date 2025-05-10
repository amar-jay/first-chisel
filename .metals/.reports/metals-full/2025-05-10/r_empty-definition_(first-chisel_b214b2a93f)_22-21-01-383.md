error id: ReadPortIo
file://<WORKSPACE>/riscv/register.scala
empty definition using pc, found symbol in pc: 
semanticdb not found

found definition using fallback; symbol ReadPortIo
offset: 1103
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
  val read0 = Decoupled(R@@eadPortIo(xlen, log2Ceil(numRegs)))
  val read1 = Decoupled(new ReadPortIo(xlen, log2Ceil(numRegs)))
  val write = new WritePortIo(xlen, log2Ceil(numRegs))

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
 * @param conf Configuration parameters that extend RVConfig, determining properties like XLEN
 */

class RegisterFile(
	xlen: Int = Constants.DATA_WIDTH,
	numRegs: Int = 32
) extends Module {
  require(numRegs >= 32, "RISC-V requires at least 32 registers")
  val io = IO(new RegIo(xlen, numRegs))
  val registers = Mem(numRegs, SInt(xlen.W))
  
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
  
  // Assertion for design verification
  assert(registers(0) === 0.S, "Register x0 must always be zero")
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 