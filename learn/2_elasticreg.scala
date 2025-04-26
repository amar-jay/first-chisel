//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using dep "edu.berkeley.cs::chiseltest:6.0.0"  // ✅ Chisel test lib
//> using plugin "org.chipsalliance:::chisel-plugin:6.5.0"
//> using options -language:implicitConversions
//> using options "-unchecked","-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations"

import chisel3._
import chiseltest._
import chisel3.util._
import org.scalatest.flatspec.AnyFlatSpec

// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage

/**
 * Implements the same interface as chisel3.util.Queue.
 * Effectively a two-entry Queue.
 *
 * @param gen the type of Data object to store in the register
 */
class ElasticReg[T <: Data](gen: T) extends Module
{
  val entries = 2
  val io = IO(new QueueIO(gen, entries) {})

  private val valid = RegInit(VecInit(Seq.fill(entries) { false.B }))
  private val elts = Reg(Vec(entries, gen))

  for (i <- 0 until entries) {
    def paddedValid(i: Int) = if (i == -1) true.B else if (i == entries) false.B else valid(i)

    val wdata = if (i == entries-1) io.enq.bits else Mux(valid(i+1), elts(i+1), io.enq.bits)
    val wen = Mux(io.deq.ready,
                  paddedValid(i+1) || io.enq.fire && ((i == 0).B || valid(i)),
                  io.enq.fire && paddedValid(i-1) && !valid(i))
    when (wen) { elts(i) := wdata }

    valid(i) := Mux(io.deq.ready,
                    paddedValid(i+1) || io.enq.fire && ((i == 0).B || valid(i)),
                    io.enq.fire && paddedValid(i-1) || valid(i))
  }

  io.enq.ready := !valid(entries-1)
  io.deq.valid := valid(0)
  io.deq.bits := elts.head

  io.count := PopCount(valid.asUInt)
}

/**
 * Companion object to ElasticReg which enqueues a data type
 * and returns a dequeued Data object.
 */
object ElasticReg
{
  def apply[T <: Data](enq: DecoupledIO[T]): DecoupledIO[T] = {
    val q = Module(new ElasticReg(enq.bits.cloneType))
    q.io.enq <> enq
    q.io.deq
  }
}
object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
      gen = new ElasticReg(DecoupledIO(UInt(32.W))), // moving average FIR filter
      // gen = new FirFilter(8, Seq(0.U, 1.U)), // 1 cycle delay FIR filter
      // gen = new FirFilter(8, Seq(1.U, 2.U, 3.U, 2.U, 1.U)), // triangular impulse response
      // gen = new FirFilter(8, Seq(1.U, 2.U, 3.U, 4.U, 5.U)), // edge detection convolution
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}

class ElasticRegTest extends AnyFlatSpec with ChiselScalatestTester {
}
