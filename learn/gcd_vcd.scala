//> using scala "2.13.12"
//> using dep "com.github.rameloni::tywaves-chisel-api:0.4.2-SNAPSHOT"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using plugin "org.chipsalliance:::chisel-plugin:6.7.0"
//> using options "-unchecked" "-deprecation" "-language:reflectiveCalls" "-feature" "-Xcheckinit" "-Xfatal-warnings" "-Ywarn-dead-code" "-Ywarn-unused" "-Ymacro-annotations"
//> using dep "org.scalatest::scalatest:3.2.18"
//> using repository "https://s01.oss.sonatype.org/content/repositories/snapshots"



// DO NOT EDIT THE ORTHER OF THESE IMPORTS (it will be solved in future versions)
import chisel3._
import tywaves.simulator._
import tywaves.simulator.ParametricSimulator._
import tywaves.simulator.simulatorSettings._
import circt.stage.ChiselStage
// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
//import _root_.circt.stage.ChiselStage
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

/** A simple module useful for testing Chisel generation and testing */
class GCD extends Module {
  val io = IO(new Bundle {
    val a             = Input(UInt(32.W))
    val b             = Input(UInt(32.W))
    val loadValues    = Input(Bool())
    val result        = Output(UInt(32.W))
    val resultIsValid = Output(Bool())
  })

  val x = Reg(UInt(32.W))
  val y = Reg(UInt(32.W))

  val tmp = x + y
  when(x > y) {
    val myTmpVal: UInt = x -% y
    x := myTmpVal
  }.otherwise {
    val myTmpVal = y -% x
    val myTmpVal3 = Wire(UInt(32.W))
    myTmpVal3 := myTmpVal
    y         := y -% x
  }

  when(io.loadValues) { x := io.a; y := io.b }

  io.result        := x
  io.resultIsValid := y === 0.U
}

class GCDTest extends AnyFunSpec with Matchers {
  describe("ParametricSimulator") {
    it("runs GCD correctly") {

      val chiselStage = new ChiselStage(true)
      chiselStage.execute(
        args = Array("--target", "chirrtl"),
        annotations = Seq(
          chisel3.stage.ChiselGeneratorAnnotation(() => new GCD()),
          circt.stage.FirtoolOption("-g"),
          circt.stage.FirtoolOption("--emit-hgldd"),
        ),
      )
      println("Hello, world!")
      simulate(new GCD(), Seq(VcdTrace, SaveWorkdirFile("gcdWorkdir"))) { gcd =>
        gcd.io.a.poke(24.U)
        gcd.io.b.poke(36.U)
        gcd.io.loadValues.poke(1.B)
        gcd.clock.step()
        gcd.io.loadValues.poke(0.B)
        gcd.clock.stepUntil(sentinelPort = gcd.io.resultIsValid, sentinelValue = 1, maxCycles = 10)
        gcd.io.resultIsValid.expect(true.B)
        gcd.io.result.expect(12)
      }
    }
  }

  describe("TywavesSimulator") {
    it("runs GCD correctly") {
      import TywavesSimulator._

      simulate(new GCD(), Seq(VcdTrace, WithTywavesWaveforms(true)), simName = "runs_GCD_correctly_launch_tywaves") {
        gcd =>
          gcd.io.a.poke(24.U)
          gcd.io.b.poke(36.U)
          gcd.io.loadValues.poke(1.B)
          gcd.clock.step()
          gcd.io.loadValues.poke(0.B)
          gcd.clock.stepUntil(sentinelPort = gcd.io.resultIsValid, sentinelValue = 1, maxCycles = 10)
          gcd.io.resultIsValid.expect(true.B)
          gcd.io.result.expect(12)
          gcd.io.a.poke(24.U)
          gcd.io.b.poke(72.U)
          gcd.reset.poke(true.B)
          gcd.io.loadValues.poke(1.B)
          gcd.clock.step()
          gcd.io.loadValues.poke(0.B)
          gcd.reset.poke(false.B)
          gcd.clock.stepUntil(sentinelPort = gcd.io.resultIsValid, sentinelValue = 1, maxCycles = 10)
          gcd.io.resultIsValid.expect(true.B)
          gcd.io.result.expect(24)
      }
    }
  }

}