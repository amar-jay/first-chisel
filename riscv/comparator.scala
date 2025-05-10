
package riscv
import constants._
class BranchComparator extends Module {
  val io = IO(new Bundle {
	 val br_type = Input(BranchTypes())	
	 val rs1 = Input(UInt(Constants.DATA_WIDTH.W))
	 val rs2 = Input(UInt(Constants.DATA_WIDTH.W))
	 val output = Output(Bool())
  })

  io.output := false.B
  val eq = io.rs1 === io.rs2
  val ne = !eq
  val lt = io.rs1.asSInt < io.rs2.asSInt
  val ge = !lt
  val ltu = io.rs1 < io.rs2
  val geu = !ltu


  switch(io.br_type) {
	 is(BInstructions.BEQ) {
		io.output := eq
	 }
	 is(BInstructions.BNE) {
		io.output := ne
	 }
	 is(BInstructions.BLT) {
		io.output := lt
	 }
	 is(BInstructions.BGE) {
		io.output := ge
	 }
	 is(BInstructions.BLTU) {
		io.output := ltu
	 }
	 is(BInstructions.BGEU) {
		io.output := geu
	 }
	 default {
		io.output := true.B
	 }
  }
}
