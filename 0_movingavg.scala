//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using plugin "org.chipsalliance:::chisel-plugin:6.7.0"
//> using options "-unchecked","-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations"

import chisel3._
// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage

class FirFilter(len: Int, seq: Seq[Int]) extends Module {
}


// basic covolution by [1 1 1], nothing fancy
class MovingAverage(bitWidth: Int) extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(bitWidth.W))
    val out = Output(UInt(bitWidth.W))
  })

  val next = RegNext(io.in)
  val next_2 = RegNext(next)

  io.out := (io.in * 1.U) + (next * 1.U) + (next_2 * 1.U)
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
      gen = new MovingAverage(8),
      
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}


