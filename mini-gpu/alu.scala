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



// mini risc-v core
object Ops extends ChiselEnum {
  val ADD, SUB, MUL, DIV, XOR, NOR, AND, OR = Value
}

class ArithmetricLogicUnit(bitWidth: Int) extends Module {
	val io = IO(new Bundle {
		val instr = Input(Ops())
		val a = Input(UInt(bitWidth.W))
		val b = Input(UInt(bitWidth.W))
		val out = Output(Valid(UInt(bitWidth.W)))
	})

	val op_seq = Seq(
		Ops.ADD -> (io.a + io.b),
		Ops.SUB -> (io.a - io.b),
		Ops.MUL -> (io.a * io.b),
		Ops.DIV -> Mux(io.b === 0.U, 0.U, io.a / io.b), // safe devision
		Ops.XOR -> (io.a ^ io.b),
		Ops.NOR -> (~io.a),
		Ops.AND -> (io.a & io.b),
		Ops.OR -> (io.a | io.b),
	)

	val div_by_zero = (io.instr === Ops.DIV) && (io.b === 0.U)
	val add_overflow = (io.instr === Ops.ADD) && ((io.a +& io.b)(bitWidth))

	val valid_result = !(div_by_zero | add_overflow)

	io.out.valid := valid_result

	// MuxLookup(myEnum, default)(Seq(MyEnum.a -> 1.U, MyEnum.b -> 2.U, MyEnum.c -> 3.U))
	val result = MuxLookup(io.instr.asUInt, 0.U(bitWidth.W))(
			op_seq.map {
				case (ops_type, value) => (ops_type.asUInt, value)
			}
		)
	io.out.bits := result
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
      gen = new ArithmetricLogicUnit(8),
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}

