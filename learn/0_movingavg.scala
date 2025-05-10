//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using plugin "org.chipsalliance:::chisel-plugin:6.7.0"
//> using options "-unchecked","-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations"

import chisel3._
// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage

/**
  * This Scala file defines a simple Chisel module for computing a moving average
  * using a basic convolution operation with a fixed kernel [1, 1, 1].
  *
  * The `MovingAverage` class:
  * - Represents a hardware module that computes the moving average of the last three
  *   input values using a sliding window.
  * - Takes a parameter `bitWidth` to specify the bit width of the input and output signals.
  * - Contains an `io` interface with:
  *   - `in`: An input signal of type `UInt` with a width of `bitWidth`.
  *   - `out`: An output signal of type `UInt` with a width of `bitWidth`.
  * - Internally uses two registers (`next_1` and `next_2`) to store the previous two
  *   input values, enabling the sliding window computation.
  * - Computes the output as the sum of the current input and the two previous inputs,
  *   effectively implementing the convolution with the kernel [1, 1, 1].
  *
  * The `Main` object:
  * - Serves as the entry point for generating the SystemVerilog code for the `MovingAverage` module.
  * - Uses the `ChiselStage.emitSystemVerilog` method to generate the SystemVerilog representation
  *   of the module.
  * - Configures the FIRRTL compiler with options to disable randomization and strip debug information.
  *
  * Example Usage:
  * - The `MovingAverage` module is instantiated with a bit width of 8 in the `Main` object.
  * - The generated SystemVerilog can be used for simulation or synthesis in hardware design workflows.
  *
  * Notes:
  * - This implementation assumes a simple kernel and does not include scaling or normalization.
  * - The design is parameterized by `bitWidth`, making it reusable for different signal widths.
  */

class MovingAverage(bitWidth: Int) extends Module {
  val io = IO(new Bundle {
    val input = Input(UInt(bitWidth.W))
    val output = Output(UInt(bitWidth.W))
  })

  val next_1 = RegNext(io.input)
  val next_2 = RegNext(next_1)

  io.output := io.input + next_1 + next_2 
}


object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
      gen = new MovingAverage(8),
      
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}


