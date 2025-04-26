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

// create a FIR filter using convolution coefficients
class FirFilter(bitWidth: Int, seq: Seq[UInt]) extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(bitWidth.W))
    val out = Output(UInt(bitWidth.W))
  })

  val z = Reg(Vec(seq.length, UInt(bitWidth.W)))
  z(0) := io.in
  for (i <- 1 until seq.length) {
    z(i) := z(i-1)
  }

  val conv = VecInit.tabulate(seq.length)(i=> z(i) * seq(i))

  io.out := conv.reduce(_ +& _)
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
      gen = new FirFilter(8, Seq(1.U, 1.U, 1.U)), // moving average FIR filter
      // gen = new FirFilter(8, Seq(0.U, 1.U)), // 1 cycle delay FIR filter
      // gen = new FirFilter(8, Seq(1.U, 2.U, 3.U, 2.U, 1.U)), // triangular impulse response
      // gen = new FirFilter(8, Seq(1.U, 2.U, 3.U, 4.U, 5.U)), // edge detection convolution
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}

class FirFilterTest extends AnyFlatSpec with ChiselScalatestTester {

  "FirFilter" should "correctly apply a moving average with [1, 1, 1]" in {
    test(new FirFilter(8, Seq(1.U, 1.U, 1.U))) { c =>
      c.io.in.poke(1.U); c.clock.step()
      c.io.in.poke(2.U); c.clock.step()
      c.io.in.poke(3.U); c.clock.step()
      c.io.out.expect(6.U) // 1 + 2 + 3
    }
  }

  "FirFilter" should "correctly apply 1 cycle delay with [0, 1]" in {
    test(new FirFilter(8, Seq(0.U, 1.U))) { c =>
      c.io.in.poke(7.U); c.clock.step()
      c.io.in.poke(8.U); c.clock.step()
      c.io.out.expect(7.U) // Only 1-cycle delay, so output is previous input
    }
  }

  "FirFilter" should "correctly apply triangular impulse response [1, 2, 3, 2, 1]" in {
    test(new FirFilter(8, Seq(1.U, 2.U, 3.U, 2.U, 1.U))) { c =>
      // Feed 1,2,3,4,5,6,7 and observe how the weights apply
      for (i <- 1 to 5) {
        c.io.in.poke(i.U)
        c.clock.step()
      }
      c.io.in.poke(6.U); c.clock.step()
      c.io.in.poke(7.U); c.clock.step()
      // Output = 3*5 + 2*4 + 1*3 + 2*6 + 1*7 = 15 + 8 + 3 + 12 + 7 = 45
      c.io.out.expect(45.U)
    }
  }

  "FirFilter" should "correctly apply edge detection convolution [1, 2, 3, 4, 5]" in {
    test(new FirFilter(8, Seq(1.U, 2.U, 3.U, 4.U, 5.U))) { c =>
      // Input some known values to test accumulation
      c.io.in.poke(1.U); c.clock.step()
      c.io.in.poke(2.U); c.clock.step()
      c.io.in.poke(3.U); c.clock.step()
      c.io.in.poke(4.U); c.clock.step()
      c.io.in.poke(5.U); c.clock.step()
      c.io.out.expect(
        (1 * 5 + 2 * 4 + 3 * 3 + 4 * 2 + 5 * 1).U
      ) // 5 + 8 + 9 + 8 + 5 = 35
    }
  }
}
