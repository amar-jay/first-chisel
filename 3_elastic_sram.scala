//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using dep "edu.berkeley.cs::chiseltest:6.0.0"  // ✅ Chisel test lib
//> using plugin "org.chipsalliance:::chisel-plugin:6.7.0"
//> using options "-unchecked","-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations"

import chisel3._
import chisel3.util._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage

class ESramWritePort[T <: Data](val idxSz: Int, private val gen: T)
    extends Bundle {
  val idx = UInt(idxSz.W)
  val data = gen
}

class ElasticSRAM[T <: Data](numEntries: Int, gen: T) extends Module {
  val idxSz = log2Ceil(numEntries)

  val io = IO(new Bundle {
    // read request on cycle S0 (pass in read address)
    val rreq = Flipped(
      Decoupled(UInt(idxSz.W))
    ) // ought to be valid & ready input req address

    // read response on cycle S1 (receive read output)
    val rres = Decoupled(gen) // reversed reading
    val write = Flipped(Valid(new ESramWritePort(idxSz, gen))) // valid only
    val flush = Input(
      Bool()
    ) // clear out queued inflight requests, but allow current response to be valid.
  })

  // memory block
  private val mem = SyncReadMem(numEntries, gen)

  when(io.write.valid) {
    // every valid data in the write should be written to memory. No waiting for if it is ready.
    mem.write(io.write.bits.idx, io.write.bits.data)
  }

  // since in SyncMemBlock data is only recieved on the subsequent cycle of sending address.
  // This is a shadow flop to decouple read request from its response, by caching preceding address and indices
  // but still replay s0 onto s1 if not ready
  val s1_replay = !io.rres.ready
  val s0_valid = Wire(Bool())
  val s0_ridx = Wire(UInt(idxSz.W))
  val last_val = RegInit(false.B)
  val last_idx = RegInit(0.U(idxSz.W))

  when(io.flush) {
    last_val := false.B
  }.elsewhen(s1_replay) {
    last_val := last_val
    last_idx := last_idx
  }.otherwise {
    last_val := io.rreq.valid
    last_idx := io.rreq.bits
  }

  s0_valid := Mux(s1_replay, last_val, io.rreq.valid)
  s0_ridx := Mux(s1_replay, last_idx, io.rreq.bits)

  val s1_valid = RegNext(s0_valid, false.B)
  io.rres.valid := s1_valid
  io.rres.bits := mem.read(s0_ridx, s0_valid)

  // Set ready signal based on whether we can accept a new request
  io.rreq.ready := !s1_replay
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
      gen = new ElasticSRAM(8, UInt(8.W)), // moving average FIR filter
      // gen = new FirFilter(8, Seq(0.U, 1.U)), // 1 cycle delay FIR filter
      // gen = new FirFilter(8, Seq(1.U, 2.U, 3.U, 2.U, 1.U)), // triangular impulse response
      // gen = new FirFilter(8, Seq(1.U, 2.U, 3.U, 4.U, 5.U)), // edge detection convolution
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}

class ElasticSRAMTest extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "ElasticSRAM"

  it should "perform basic read and write operations" in {
    test(
      new ElasticSRAM(numEntries = 8, gen = UInt(32.W))
    ) { dut =>
      // Write some data
      dut.io.write.valid.poke(true.B)
      dut.io.write.bits.idx.poke(0.U)
      dut.io.write.bits.data.poke(0x00adbeef.U)
      dut.io.flush.poke(false.B)
      dut.clock.step()

      dut.io.write.valid.poke(true.B)
      dut.io.write.bits.idx.poke(1.U)
      dut.io.write.bits.data.poke(0x00cdef01.U)
      dut.clock.step()

      dut.io.write.valid.poke(false.B)
      dut.clock.step()

      // Read data back
      dut.io.rreq.valid.poke(true.B)
      dut.io.rreq.bits.poke(0.U)
      dut.io.rres.ready.poke(true.B)
      dut.clock.step()

      dut.io.rreq.valid.poke(true.B)
      dut.io.rreq.bits.poke(1.U)
      dut.io.rres.ready.poke(true.B)
      dut.io.rres.valid.expect(true.B)
      dut.io.rres.bits.expect(0x00adbeef.U)
      dut.clock.step()

      dut.io.rreq.valid.poke(false.B)
      dut.io.rres.valid.expect(true.B)
      dut.io.rres.bits.expect(0x00cdef01.U)
      dut.clock.step()
    }
  }

}
