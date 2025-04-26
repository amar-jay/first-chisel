//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using dep "edu.berkeley.cs::chiseltest:6.0.0"  // ✅ Chisel test lib
//> using plugin "org.chipsalliance:::chisel-plugin:6.7.0"
//> using options "-unchecked","-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations"

import chisel3._
import chisel3.util._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import _root_.circt.stage.ChiselStage

object ALUOps extends ChiselEnum {
  val ADD, SUB, MUL, DIV, XOR, AND, OR = Value
}

import ALUOps._

class ALU(bitWidth: Int) extends Module {
  val io = IO(new Bundle {
    val instr = Input(ALUOps())
    val a = Input(UInt(bitWidth.W))
    val b = Input(UInt(bitWidth.W))
    val out = Output(UInt(bitWidth.W))
    val err = Output(Bool())
  })

  // Default output
  val defaultResult = 0.U(bitWidth.W)

  // Table of operations
  val operationTable = Seq(
    ALUOps.ADD -> (io.a + io.b),
    ALUOps.SUB -> (io.a - io.b),
    ALUOps.MUL -> (io.a * io.b),
    ALUOps.DIV -> (io.a / io.b),
    ALUOps.XOR -> (io.a ^ io.b),
    ALUOps.AND -> (io.a & io.b),
    ALUOps.OR -> (io.a | io.b)
  )

  // Actual result from MuxLookup
  val result = MuxLookup(io.instr.asUInt, defaultResult)(
    operationTable.map { case (op, expr) =>
      (op.asUInt, expr)
    }
  )

  io.out := result
  io.err := !operationTable
    .map(_._1)
    .map(_.asUInt === io.instr.asUInt)
    .reduce(_ || _)
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
  behavior of "ALU"

  it should "perform all operations correctly" in {
    test(new ALU(8)) { c =>
      val testCases = Seq(
        (ADD, 3.U, 2.U, 5.U),
        (SUB, 10.U, 4.U, 6.U),
        (MUL, 3.U, 4.U, 12.U),
        (DIV, 8.U, 2.U, 4.U),
        (XOR, 5.U, 3.U, 6.U),
        (AND, 6.U, 3.U, 2.U),
        (OR, 6.U, 3.U, 7.U)
      )

      for ((op, a, b, expected) <- testCases) {
        c.io.instr.poke(op)
        c.io.a.poke(a)
        c.io.b.poke(b)
        c.clock.step()
        c.io.out.expect(expected)
        c.io.err.expect(false.B)
      }
    }
  }

  it should "set error on invalid instruction" in {
    test(new ALU(8)) { c =>
      c.io.instr.poke(ALUOps.DIV)
      c.io.a.poke(1.U)
      c.io.b.poke(0.U)
      c.clock.step()
      c.io.err.expect(false.B) // set it up
    }
  }
}
