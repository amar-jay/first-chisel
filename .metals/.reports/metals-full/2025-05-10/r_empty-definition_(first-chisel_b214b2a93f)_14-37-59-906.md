error id: COPY1
file://<WORKSPACE>/riscv/alu.scala
empty definition using pc, found symbol in pc: 
semanticdb not found

found definition using fallback; symbol COPY1
offset: 3623
uri: file://<WORKSPACE>/riscv/alu.scala
text:
```scala
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

class ALU extends Module {

	val io = IO(new Bundle {
		val a = Input(UInt(Constants.DATA_WIDTH.W))
		val b = Input(UInt(Constants.DATA_WIDTH.W))
		val op = Input(ALUOperations()) // specifically ALU operations
		val result = Output(UInt(Constants.DATA_WIDTH.W))
		val zero = Output(Bool()) // zero division error
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

	 if (CONFIG.SUPPORT_MULDIV){
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
			io.overflow := edge_case
		}
	 }

    
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
    is(ALUOperations.COPY1@@) { io.result := io.a }
    is(ALUOperations.COPY2) { io.result := io.b }
  }

   // Set zero flag
  io.zero := io.result === 0.U
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
      gen = new ALU,
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 