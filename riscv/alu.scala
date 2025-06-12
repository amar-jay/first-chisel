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

import riscv.{Constants, Instructions}
import firrtl2.backends.experimental.smt.Op

class ALU(val width:Int = Constants.DATA_WIDTH) extends Module {

	val io = IO(new Bundle {
		val a = Input(UInt(width.W))
		val b = Input(UInt(width.W))
		val op = Input(ALUOperations()) // specifically ALU operations
		val result = Output(UInt(width.W))
		val zero = Output(Bool()) // zero flag
		val overflow = Output(Bool()) // overflow error
	})


	// pre-initialization
	io.result := 0.U
	io.zero := false.B
	io.overflow := false.B
	io.zero := (io.op === ALUOperations.DIV) && (io.b === 0.U)

	// Sign-extended inputs (for signed operations)
	val a_signed = io.a.asSInt
	val b_signed = io.b.asSInt

	// Compute results based on operation
	val shamt = io.b(4, 0) // Shift amount (use only lower 5 bits for 32-bit mode)
	
	// Multiplication with double width to detect overflow
	val mul_result = Wire(UInt((2 * width).W))
	mul_result := io.a * io.b
	
	// Division results
	val div_result = Mux(io.b === 0.U, (~0.U(width.W)), io.a / io.b) // Div by zero handling
	
	// Addition with carry out
	val add_result = Wire(UInt((width + 1).W))
	add_result := io.a +& io.b
	
	// Subtraction with borrow
	val sub_result = Wire(UInt((width + 1).W))
	sub_result := io.a -& io.b
  
  // Main operation selection
  switch(io.op) {
    // Arithmetic operations
    is(ALUOperations.ADD) {
      io.result := add_result(width-1, 0)
      // Overflow detection for addition
      io.overflow := (io.a(width-1) === io.b(width-1)) && (io.a(width-1) =/= add_result(width-1))
    }
    is(ALUOperations.SUB) {
      io.result := sub_result(width-1, 0)
      // Overflow detection for subtraction
      io.overflow := (io.a(width-1) =/= io.b(width-1)) && (io.a(width-1) =/= sub_result(width-1))
    }

	//  if (CONFIG.SUPPORT_MULDIV){
		is(ALUOperations.MUL) {
			io.result := mul_result(width-1, 0)
			// Overflow if upper bits are not all the same as the sign bit
			val sign_extend = Fill(width, mul_result(width-1))
			io.overflow := (sign_extend =/= mul_result(2*width-1, width))
		}
		is(ALUOperations.DIV) {
			io.result := div_result
			// Overflow for division only happens in edge case (-2^(width-1)) / (-1)
			val edge_case = (io.a === (1.U << (width-1))) && (io.b === (~0.U(width.W)))
			val div_by_zero = io.b === 0.U
			io.overflow := edge_case || div_by_zero
		}
	//  }

    
    // Logical operations
    is(ALUOperations.AND)  { io.result := io.a & io.b }
    is(ALUOperations.OR)   { io.result := io.a | io.b }
    is(ALUOperations.XOR)  { io.result := io.a ^ io.b }
    is(ALUOperations.SLL)  { io.result := io.a << shamt }
    is(ALUOperations.SRL)  { io.result := io.a >> shamt }
    is(ALUOperations.SRA)  { io.result := (a_signed >> shamt).asUInt }
    
    // Comparison operations
    is(ALUOperations.SLT)  { io.result := Mux(a_signed < b_signed, 1.U, 0.U) }
    is(ALUOperations.SLTU) { io.result := Mux(io.a < io.b, 1.U, 0.U) }
    is(ALUOperations.SEQ)  { io.result := Mux(io.a === io.b, 1.U, 0.U) }
    is(ALUOperations.SNE)  { io.result := Mux(io.a =/= io.b, 1.U, 0.U) }
    
    // Special operations
    is(ALUOperations.COPY1) { io.result := io.a }
    is(ALUOperations.COPY2) { io.result := io.b }
  }

   // Set zero flag
  io.zero := io.result === 0.U
}


class ALUTest extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "ALU"

  // Basic arithmetic operations
  it should "perform basic operations correctly" in {
		// perform addition with two inputs
		test(new ALU())
		.withAnnotations(Seq(WriteVcdAnnotation)){ dut =>
      dut.io.a.poke(10.U)
      dut.io.b.poke(15.U)
      dut.io.op.poke(ALUOperations.ADD)
      dut.clock.step(10)
      dut.io.result.expect(25.U)
      dut.io.zero.expect(false.B)
      dut.io.overflow.expect(false.B)

		// perform subtraction with two inputs
      dut.io.a.poke(20.U)
      dut.io.b.poke(5.U)
      dut.io.op.poke(ALUOperations.SUB)
      dut.clock.step(10)
      dut.io.result.expect(15.U)
      dut.io.zero.expect(false.B)

			// perform multiplication with two inputs
      dut.io.a.poke(7.U)
      dut.io.b.poke(9.U)
      dut.io.op.poke(ALUOperations.MUL)
      dut.clock.step(10)
      dut.io.result.expect(63.U)
      dut.io.overflow.expect(false.B)

			// perform division with two inputs
      dut.io.a.poke(100.U)
      dut.io.b.poke(5.U)
      dut.io.op.poke(ALUOperations.DIV)
      dut.clock.step(10)
      dut.io.result.expect(20.U)
      dut.io.zero.expect(false.B)

      dut.io.a.poke("b10101010".U)
      dut.io.b.poke("b11110000".U)
      dut.io.op.poke(ALUOperations.AND)
      dut.clock.step(10)
      dut.io.result.expect("b10100000".U)

			// perform or with two inputs
      dut.io.a.poke("b10101010".U)
      dut.io.b.poke("b11110000".U)
      dut.io.op.poke(ALUOperations.OR)
      dut.clock.step(10)
      dut.io.result.expect("b11111010".U)

			// perform XOR with two inputs
      dut.io.a.poke("b10101010".U)
      dut.io.b.poke("b11110000".U)
      dut.io.op.poke(ALUOperations.XOR)
      dut.clock.step(10)
      dut.io.result.expect("b01011010".U)

			// perform logical left shift
      dut.io.a.poke("b00000001".U)
      dut.io.b.poke(3.U)
      dut.io.op.poke(ALUOperations.SLL)
      dut.clock.step(10)
      dut.io.result.expect("b00001000".U)

			// perform logical right shift
      dut.io.a.poke("b10000000".U)
      dut.io.b.poke(3.U)
      dut.io.op.poke(ALUOperations.SRL)
      dut.clock.step(10)
      dut.io.result.expect("b00010000".U)



			// perform arithmetic right shift
      // With MSB = 1 (negative number)
      var negativeNum = BigInt(2).pow(Constants.DATA_WIDTH - 1) | BigInt("10000000", 2)
      dut.io.a.poke(negativeNum)
      dut.io.b.poke(3.U)
      dut.io.op.poke(ALUOperations.SRA)
      dut.clock.step(10)
      // Should preserve sign bit
      val expected = (negativeNum >> 3) | (BigInt(7) << (Constants.DATA_WIDTH - 3))
      dut.io.result.expect(expected)


			// perform signed less than comparison
      // Test with negative and positive numbers
      negativeNum = BigInt(2).pow(Constants.DATA_WIDTH - 1) | 5
      dut.io.a.poke(negativeNum) // Negative number in two's complement
      dut.io.b.poke(5.U)
      dut.io.op.poke(ALUOperations.SLT)
      dut.clock.step(10)
      dut.io.result.expect(1.U) // Negative < Positive
      // Positive vs positive
      dut.io.a.poke(3.U)
      dut.io.b.poke(5.U)
      dut.clock.step(10)
      dut.io.result.expect(1.U) // 3 < 5

			// perform unsigned less than comparison
      // What's a negative number in signed is a large positive in unsigned
      val largeNum = BigInt(2).pow(Constants.DATA_WIDTH - 1)
      dut.io.a.poke(largeNum)
      dut.io.b.poke(5.U)
      dut.io.op.poke(ALUOperations.SLTU)
      dut.clock.step(10)
      dut.io.result.expect(0.U) // As unsigned, large number > 5
      // Small numbers comparison
      dut.io.a.poke(3.U)
      dut.io.b.poke(5.U)
      dut.clock.step(10)
      dut.io.result.expect(1.U) // 3 < 5

			// perform equality check
      dut.io.a.poke(42.U)
      dut.io.b.poke(42.U)
      dut.io.op.poke(ALUOperations.SEQ)
      dut.clock.step(10)
      dut.io.result.expect(1.U) // Equal
      dut.io.b.poke(43.U)
      dut.clock.step(10)
      dut.io.result.expect(0.U) // Not equal

			// perform inequality check
      dut.io.a.poke(42.U)
      dut.io.b.poke(43.U)
      dut.io.op.poke(ALUOperations.SNE)
      dut.clock.step(10)
      dut.io.result.expect(1.U) // Not equal
      dut.io.b.poke(42.U)
      dut.clock.step(10)
      dut.io.result.expect(0.U) // Equal
    }
  }

  it should "detect overflow" in {
		test(new ALU())
		.withAnnotations(Seq(WriteVcdAnnotation)){ dut =>
			// detect overflow in addition
      // Max positive + 1 should overflow
      dut.io.a.poke(BigInt(2).pow(31) - 1)
      dut.io.b.poke(1.U)
      dut.io.op.poke(ALUOperations.ADD)
      dut.clock.step(10)
      dut.io.overflow.expect(true.B)

			// detect overflow in subtraction
      // Most negative number - 1 should overflow
      dut.io.a.poke(BigInt(2).pow(31))
      dut.io.b.poke(1.U)
      dut.io.op.poke(ALUOperations.SUB)
      dut.clock.step(10)
      dut.io.overflow.expect(true.B)

			// detect overflow in multiplication
			// Max positive * 2 should overflow
			dut.io.a.poke(BigInt(2).pow(31) - 1)
			dut.io.b.poke(2.U)
			dut.io.op.poke(ALUOperations.MUL)
			dut.clock.step(10)
			dut.io.overflow.expect(true.B)
    }
  }


	it should "handle division by zero" in {
	test(new ALU()) { dut =>
		dut.io.a.poke(100.U)
		dut.io.b.poke(0.U)
		dut.io.op.poke(ALUOperations.DIV)
		dut.clock.step(10)
		
		// Use BigInt for test expectations instead of Chisel hardware expressions
		val allOnes = (BigInt(1) << Constants.DATA_WIDTH) - 1
		dut.io.result.expect(allOnes.U)  // All 1s (-1 in two's complement)
		dut.io.overflow.expect(true.B)   // Division by zero should set overflow flag
		dut.io.zero.expect(false.B)      // Result isn't zero, so zero flag shouldn't be set
	}
	}

  it should "detect division overflow for edge case" in {
    test(new ALU()) { dut =>
      // MIN_INT / -1 overflows in two's complement
      dut.io.a.poke(BigInt(2).pow(31))
		val allOnes = (BigInt(1) << Constants.DATA_WIDTH) - 1
      dut.io.b.poke(allOnes.U) // -1 in two's complement
      dut.io.op.poke(ALUOperations.DIV)
      dut.clock.step(10)
      dut.io.overflow.expect(true.B)
    }
  }

  // Special operations
  it should "copy first input correctly" in {
    test(new ALU()) { dut =>
      dut.io.a.poke(0xABCD1234L.U)
      dut.io.b.poke(0x00000000L.U)
      dut.io.op.poke(ALUOperations.COPY1)
      dut.clock.step(10)
      dut.io.result.expect(0xABCD1234L.U)
    }
  }

  it should "copy second input correctly" in {
    test(new ALU()) { dut =>
      dut.io.a.poke(0x00000000L.U)
      dut.io.b.poke(0xABCD1234L.U)
      dut.io.op.poke(ALUOperations.COPY2)
      dut.clock.step(10)
      dut.io.result.expect(0xABCD1234L.U)
    }
  }

  // Zero flag tests
  it should "set zero flag when result is zero" in {
    test(new ALU()) { dut =>
      dut.io.a.poke(5.U)
      dut.io.b.poke(5.U)
      dut.io.op.poke(ALUOperations.SUB)
      dut.clock.step(10)
      dut.io.result.expect(0.U)
      dut.io.zero.expect(true.B)
    }
  }
}

// object Main extends App {
//   println(
//     ChiselStage.emitSystemVerilog(
//       gen = new ALU,
//       firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
//     )
//   )
// }