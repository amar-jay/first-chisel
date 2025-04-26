//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using dep "edu.berkeley.cs::chiseltest:6.0.0"  // ✅ Chisel test lib
//> using plugin "org.chipsalliance:::chisel-plugin:6.7.0"
//> using options "-unchecked","-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations"

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage

object Instructions extends ChiselEnum{
  def ADD = 1.U(4.W)
  def SUB = 2.U(4.W)
  def MUL = 3.U(4.W)
  def DIV = 4.U(4.W)
  def NOR = 5.U(4.W)
  def XOR = 6.U(4.W)
  def AND = 7.U(4.W)
  def OR = 8.U(4.W)
  def SLL = 9.U(4.W)
  def SLT = 10.U(4.W)
  def SLTU = 11.U(4.W)
  def SRL = 12.U(4.W)
}

import Instructions._

// create a FIR filter using convollution coefficients
class ALU(bitWidth: Int) extends Module {
  val io = IO(new Bundle {
    val instr = Input(UInt(bitWidth.W))
    val a = Input(UInt(bitWidth.W))
    val b = Input(UInt(bitWidth.W))
    val out = Output(UInt(bitWidth.W))
    val err = Output(Bool())
  })

  val instr = io.instr
  io.err := false.B
  val result =  Reg(UInt(bitWidth.W))
  when (instr === ADD) { result := io.a + io.b }
  .elsewhen (instr === SUB) {result := io.a - io.b}
  .elsewhen (instr === MUL) {result := io.a * io.b}
  .elsewhen (instr === DIV) {
    io.err := Mux(io.b === 0.U, true.B, false.B)
    result := io.a / io.b
  }
  .elsewhen (instr === XOR) {result := io.a ^ io.b}
  .elsewhen (instr === AND) {result := io.a & io.b}
  .elsewhen (instr === OR) {result := io.a | io.b}
  .otherwise {
    result:=0.U
  }
  io.out := result
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
      gen = new ALU(8),
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}

class ALUTest extends AnyFlatSpec with ChiselScalatestTester {
}
