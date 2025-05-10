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
  val ready = Output(Bool())
}

class WritePortIo(xlen: Int, mem_add_len:Int) extends Bundle {
  val en    = Input(Bool())
  val addr  = Input(UInt(mem_add_len.W))
  val data  = Input(SInt(xlen.W))
}

class RegIo(xlen: Int = Constants.DATA_WIDTH, numRegs: Int = 32) extends Bundle {
  val read0 = new ReadPortIo(xlen, log2Ceil(numRegs))
  val read1 = new ReadPortIo(xlen, log2Ceil(numRegs))
  val write = new WritePortIo(xlen, log2Ceil(numRegs))

  // Debug port for visualization and testing
  val debugRegState = Output(Vec(numRegs, SInt(xlen.W)))
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
  
  // Initialize registers with zeros
  val registers = RegInit(VecInit(Seq.fill(numRegs)(0.S(xlen.W))))
  
  // Helper function for read port logic to reduce duplication
  def readPort(en: Bool, addr: UInt, writeEn: Bool, writeAddr: UInt, writeData: SInt): SInt = {
    val isReg0 = addr === 0.U
    val isWriteConflict = writeEn && (addr === writeAddr)
    return Mux(
      !en || isReg0,
      0.S(xlen.W), // Reading disabled or x0 -> return 0
      Mux(
        isWriteConflict,
        writeData, // Handle read-after-write in same cycle
        registers(addr) // Normal register read
      )
    )
  }
  
  // Read port 0
  when (io.read0.en && (io.read0.addr =/= 0.U)) {
    io.read0.data := Mux(io.write.en & (io.write.addr === io.read0.addr),
                         io.write.data,
                         registers(io.read0.addr))
    io.read0.ready := true.B
  } .otherwise {
    io.read0.data := 0.S(xlen.W)
    io.read0.ready := false.B
  }
  
  // Read port 1 with forwarding logic
  when (io.read1.en && (io.read1.addr =/= 0.U)) {
    io.read1.data := Mux(io.write.en & (io.write.addr === io.read1.addr),
                         io.write.data,
                         registers(io.read1.addr))
    io.read1.ready := true.B
  } .otherwise {
    io.read1.data := 0.S(xlen.W)
    io.read1.ready := false.B
  }
  
  // THIS IS THE MISSING WRITE LOGIC
  when (io.write.en && io.write.addr =/= 0.U) {
    registers(io.write.addr) := io.write.data
  }
  
  // Connect debug interface
  for (i <- 0 until numRegs) {
    io.debugRegState(i) := registers(i)
  }
  
  // Design assertions
  assert(registers(0) === 0.S, "Register x0 must always be zero")
}


class RegisterFileSpec extends AnyFlatSpec with ChiselScalatestTester {
  
  behavior of "RegisterFile"
  
  it should "initialize all registers to zero" in {
  test(new RegisterFile()) { dut =>
    for (i <- 0 until 32) {
    dut.io.read0.en.poke(true.B)
    dut.io.read0.addr.poke(i.U)
    dut.clock.step()
    dut.io.read0.data.expect(0.S)
    }
  }
  }

  it should "always return 0 when reading register x0" in {
  test(new RegisterFile()) { dut =>
    // Try to write a non-zero value to x0
    dut.io.write.en.poke(true.B)
    dut.io.write.addr.poke(0.U)
    dut.io.write.data.poke(42.S)
    dut.clock.step()
    
    // Verify reading x0 still returns 0
    dut.io.read0.en.poke(true.B)
    dut.io.read0.addr.poke(0.U)
    dut.io.read0.data.expect(0.S)
  }
  }
  
  it should "correctly write and read from registers" in {
  test(new RegisterFile()) { dut =>
    // Write values to some registers
    dut.io.write.en.poke(true.B)
    dut.io.write.addr.poke(5.U)
    dut.io.write.data.poke(123.S)
    dut.clock.step()
    
    dut.io.write.addr.poke(10.U)
    dut.io.write.data.poke(-456.S)
    dut.clock.step()
    
    // Read back and verify
    dut.io.read0.en.poke(true.B)
    dut.io.read0.addr.poke(5.U)
    dut.io.read0.data.expect(123.S)
    dut.io.read0.ready.expect(true.B)
    
    dut.io.read1.en.poke(true.B)
    dut.io.read1.addr.poke(10.U)
    dut.io.read1.data.expect(-456.S)
    dut.io.read1.ready.expect(true.B)
  }
  }
  
  it should "implement read-after-write forwarding" in {
  test(new RegisterFile()) { dut =>
    // Write and read in the same cycle from port 0
    dut.io.write.en.poke(true.B)
    dut.io.write.addr.poke(15.U)
    dut.io.write.data.poke(789.S)
    
    dut.io.read0.en.poke(true.B)
    dut.io.read0.addr.poke(15.U)
    dut.io.read0.data.expect(789.S)
    
    // Write and read in the same cycle from port 1
    dut.io.read1.en.poke(true.B)
    dut.io.read1.addr.poke(15.U)
    dut.io.read1.data.expect(789.S)
    
    dut.clock.step()
    
    // Verify after the clock step
    dut.io.write.en.poke(false.B)
    dut.io.read0.data.expect(789.S)
    dut.io.read1.data.expect(789.S)
  }
  }
  
  it should "disable output when read enable is false" in {
  test(new RegisterFile()) { dut =>
    // Write a value to register 20
    dut.io.write.en.poke(true.B)
    dut.io.write.addr.poke(20.U)
    dut.io.write.data.poke(999.S)
    dut.clock.step()
    
    // Read with enable true
    dut.io.read0.en.poke(true.B)
    dut.io.read0.addr.poke(20.U)
    dut.io.read0.data.expect(999.S)
    dut.io.read0.ready.expect(true.B)
    
    // Read with enable false
    dut.io.read0.en.poke(false.B)
    dut.io.read0.data.expect(0.S)
    dut.io.read0.ready.expect(false.B)
  }
  }
  
  it should "provide register state via debug port" in {
  test(new RegisterFile()) { dut =>
    // Write values to several registers
    dut.io.write.en.poke(true.B)
    
    for (i <- 1 until 10) {
    dut.io.write.addr.poke(i.U)
    dut.io.write.data.poke((i * 100).S)
    dut.clock.step()
    
    // Verify via debug port
    dut.io.debugRegState(i).expect((i * 100).S)
    }
    
    // x0 should still be 0
    dut.io.debugRegState(0).expect(0.S)
  }
  }
  
  it should "handle concurrent operations on both read ports" in {
  test(new RegisterFile()) { dut =>
    // Setup some register values
    dut.io.write.en.poke(true.B)
    dut.io.write.addr.poke(7.U)
    dut.io.write.data.poke(111.S)
    dut.clock.step()
    
    dut.io.write.addr.poke(8.U)
    dut.io.write.data.poke(222.S)
    dut.clock.step()
    
    // Read from both ports simultaneously
    dut.io.read0.en.poke(true.B)
    dut.io.read0.addr.poke(7.U)
    dut.io.read1.en.poke(true.B)
    dut.io.read1.addr.poke(8.U)
    
    // Verify both reads are correct
    dut.io.read0.data.expect(111.S)
    dut.io.read1.data.expect(222.S)
  }
  }
  

  it should "properly handle boundary values" in {
    test(new RegisterFile()) { dut =>
      // Define max and min values without using dynamic hardware expressions
      val maxValue = BigInt(2).pow(Constants.DATA_WIDTH - 1) - 1
      val minValue = -BigInt(2).pow(Constants.DATA_WIDTH - 1)
      
      // Write maximum value
      dut.io.write.en.poke(true.B)
      dut.io.write.addr.poke(31.U)
      dut.io.write.data.poke(maxValue.S)
      dut.clock.step()
      
      // Write minimum value 
      dut.io.write.addr.poke(30.U)
      dut.io.write.data.poke(minValue.S)
      dut.clock.step()
      
      // Read and verify max value
      dut.io.read0.en.poke(true.B)
      dut.io.read0.addr.poke(31.U)
      dut.io.read0.data.expect(maxValue.S)
      
      // Read and verify min value
      dut.io.read1.en.poke(true.B)
      dut.io.read1.addr.poke(30.U)
      dut.io.read1.data.expect(minValue.S)
    }
  }
}