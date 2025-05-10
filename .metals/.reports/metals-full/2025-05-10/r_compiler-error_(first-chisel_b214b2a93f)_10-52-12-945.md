file://<WORKSPACE>/riscv/decoder.scala
### java.lang.IndexOutOfBoundsException: -1 is out of bounds (min 0, max 2)

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.13.12
Classpath:
<WORKSPACE>/.scala-build/first-chisel_b214b2a93f/classes/main [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/chipsalliance/chisel_2.13/6.7.0/chisel_2.13-6.7.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/edu/berkeley/cs/chiseltest_2.13/6.0.0/chiseltest_2.13-6.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/scopt/scopt_2.13/4.1.0/scopt_2.13-4.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/net/jcazevedo/moultingyaml_2.13/0.4.2/moultingyaml_2.13-0.4.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-native_2.13/4.1.0-M4/json4s-native_2.13-4.1.0-M4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-text/1.10.0/commons-text-1.10.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/github/alexarchambault/data-class_2.13/0.2.6/data-class_2.13-0.2.6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/os-lib_2.13/0.9.2/os-lib_2.13-0.9.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-parallel-collections_2.13/1.0.4/scala-parallel-collections_2.13-1.0.4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle_2.13/3.1.0/upickle_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/chipsalliance/firtool-resolver_2.13/1.3.0/firtool-resolver_2.13-1.3.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/edu/berkeley/cs/firrtl2_2.13/6.0.0/firrtl2_2.13-6.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest_2.13/3.2.17/scalatest_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/net/java/dev/jna/jna/5.14.0/jna-5.14.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/nscala-time/nscala-time_2.13/2.22.0/nscala-time_2.13-2.22.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/yaml/snakeyaml/1.26/snakeyaml-1.26.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-core_2.13/4.1.0-M4/json4s-core_2.13-4.1.0-M4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-native-core_2.13/4.1.0-M4/json4s-native-core_2.13-4.1.0-M4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.12.0/commons-lang3-3.12.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/geny_2.13/1.0.0/geny_2.13-1.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/ujson_2.13/3.1.0/ujson_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upack_2.13/3.1.0/upack_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle-implicits_2.13/3.1.0/upickle-implicits_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/dev/dirs/directories/26/directories-26.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/outr/scribe_2.13/3.13.0/scribe_2.13-3.13.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/get-coursier/coursier_2.13/2.1.8/coursier_2.13-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/antlr/antlr4-runtime/4.9.3/antlr4-runtime-4.9.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-core_2.13/3.2.17/scalatest-core_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-featurespec_2.13/3.2.17/scalatest-featurespec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-flatspec_2.13/3.2.17/scalatest-flatspec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-freespec_2.13/3.2.17/scalatest-freespec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-funsuite_2.13/3.2.17/scalatest-funsuite_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-funspec_2.13/3.2.17/scalatest-funspec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-propspec_2.13/3.2.17/scalatest-propspec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-refspec_2.13/3.2.17/scalatest-refspec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-wordspec_2.13/3.2.17/scalatest-wordspec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-diagrams_2.13/3.2.17/scalatest-diagrams_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-matchers-core_2.13/3.2.17/scalatest-matchers-core_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-shouldmatchers_2.13/3.2.17/scalatest-shouldmatchers_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-mustmatchers_2.13/3.2.17/scalatest-mustmatchers_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/joda-time/joda-time/2.10.1/joda-time-2.10.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/joda/joda-convert/2.2.0/joda-convert-2.2.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-ast_2.13/4.1.0-M4/json4s-ast_2.13-4.1.0-M4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-scalap_2.13/4.1.0-M4/json4s-scalap_2.13-4.1.0-M4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle-core_2.13/3.1.0/upickle-core_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/outr/perfolation_2.13/1.2.9/perfolation_2.13-1.2.9.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/sourcecode_2.13/0.3.1/sourcecode_2.13-0.3.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-collection-compat_2.13/2.11.0/scala-collection-compat_2.13-2.11.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/outr/moduload_2.13/1.1.7/moduload_2.13-1.1.7.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/plokhotnyuk/jsoniter-scala/jsoniter-scala-core_2.13/2.13.5.2/jsoniter-scala-core_2.13-2.13.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/get-coursier/coursier-core_2.13/2.1.8/coursier-core_2.13-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/get-coursier/coursier-cache_2.13/2.1.8/coursier-cache_2.13-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/get-coursier/coursier-proxy-setup/2.1.8/coursier-proxy-setup-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-compatible/3.2.17/scalatest-compatible-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalactic/scalactic_2.13/3.2.17/scalactic_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-xml_2.13/2.2.0/scala-xml_2.13-2.2.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/github/alexarchambault/concurrent-reference-hash-map/1.1.0/concurrent-reference-hash-map-1.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/get-coursier/coursier-util_2.13/2.1.8/coursier-util_2.13-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/get-coursier/jniutils/windows-jni-utils/0.3.3/windows-jni-utils-0.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/codehaus/plexus/plexus-archiver/4.9.0/plexus-archiver-4.9.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/codehaus/plexus/plexus-container-default/2.1.1/plexus-container-default-2.1.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/virtuslab/scala-cli/config_2.13/0.2.1/config_2.13-0.2.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/github/alexarchambault/windows-ansi/windows-ansi/0.0.5/windows-ansi-0.0.5.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/javax/inject/javax.inject/1/javax.inject-1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/codehaus/plexus/plexus-utils/4.0.0/plexus-utils-4.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/codehaus/plexus/plexus-io/3.4.1/plexus-io-3.4.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/commons-io/commons-io/2.15.0/commons-io-2.15.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-compress/1.24.0/commons-compress-1.24.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/iq80/snappy/snappy/0.4/snappy-0.4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/tukaani/xz/1.9/xz-1.9.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/luben/zstd-jni/1.5.5-10/zstd-jni-1.5.5-10.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/codehaus/plexus/plexus-classworlds/2.6.0/plexus-classworlds-2.6.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/xbean/xbean-reflect/3.7/xbean-reflect-3.7.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/fusesource/jansi/jansi/1.18/jansi-1.18.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/sourcegraph/semanticdb-javac/0.10.0/semanticdb-javac-0.10.0.jar [exists ]
Options:
-unchecked ,"-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations" -language:implicitConversions -Yrangepos


action parameters:
uri: file://<WORKSPACE>/riscv/decoder.scala
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
import riscv.{Constants, Opcodes, InstructionTypes, I_Operations, R_Operations}

// instruction decoder for RISC-V
import chisel3.util.Fill
import chisel3.util.is
import chisel3.util.switch
import chisel3.when
import firrtl2.backends.experimental.smt.Op


class InstructionDecoder extends Module {
	print("Instruction Decoder\n", Constants)
	val io = IO(new Bundle {
		val inst = Input(UInt(32.W))
		val pc = Input(UInt(7.W))

		val rd_addr   = Output(UInt(5.W))
		val rs1_addr  = Output(UInt(5.W))
		val rs2_addr  = Output(UInt(5.W))
		val imm       = Output(UInt(32.W))
		val alu_op    = Output(UInt(4.W))
		val br_type   = Output(UInt(3.W))
		val mem_type  = Output(UInt(2.W))
		val mem_size  = Output(UInt(3.W)) // memory size
		val wb_sel    = Output(Bool()) // write back select
		val rf_wen    = Output(Bool()) // register file write enable
		val csr_cmd   = Output(UInt(3.W)) // CSR command
		val illegal   = Output(Bool()) // illegal instruction flag
		// instruction type uses the InstructionTypes enum
		val inst_type = Output(InstructionTypes())
	})

	io.rd_addr := io.inst(11, 7)
	io.rs1_addr := io.inst(19, 15)
	io.rs2_addr := io.inst(24, 20)
	io.imm := 0.U
	io.alu_op := 0.U
	io.br_type := 0.U
	io.mem_type := 0.U
	io.mem_size := 0.U
	io.wb_sel := false.B
	io.rf_wen := false.B
	io.csr_cmd := 0.U
	io.illegal := false.B
	io.inst_type := 0.U


	val opcode = Opcodes(io.inst(6, 0))
	val funct3 = io.inst(14, 12)
	val funct7 = io.inst(31, 25)

	// immediate value extraction
	val i_imm = io.inst(31, 20)
	val s_imm = Cat(io.inst(31, 25), io.inst(11, 7))
	val u_imm = io.inst(31, 12)
	//TODO: j type and b type immediate are a bit tricky

	// Decode the instruction based on the opcode
	switch (opcode) {
		is(Opcodes.R_TYPE) {

			io.inst_type := InstructionTypes.R_TYPE
			io.alu_op := funct3
			io.rf_wen := true.B

			switch (funct3) {
				is("b000".U) { 
					// add or sub
					when (funct7(5)) { // checking the 6th bit of funct7 is enough
						io.alu_op := R_Operations.I_SUB
					}.otherwise {
						io.alu_op := R_Operations.I_ADD
					}
				}
				// 0x4 - xor
				is ("h4".U) { io.alu_op := R_Operations.I_XOR }
				is ("h6".U) { io.alu_op := R_Operations.I_OR }
				is ("h7".U) { io.alu_op := R_Operations.I_AND }
				is ("h1".U) { io.alu_op := R_Operations.I_SLL }
				is ("h5".U) {
					// srl or sra
					when (funct7(5)) { // checking the 6th bit of funct7 is enough
						io.alu_op := R_Operations.I_SRA
					}.otherwise {
						io.alu_op := R_Operations.I_SRL
					}
				}
				is ("h2".U) { io.alu_op := R_Operations.I_SLT }
				is ("h3".U) { io.alu_op := R_Operations.I_SLTU }

				otherwise {
					// illegal instruction
					io.illegal := true.B
				}
			}
		}
		is (Opcodes.I_TYPE1){ // ALU immediate operations

			switch (funct3) {
				is ("h0".U) { io.alu_op := I_Operations.I_ADDI }
				is ("h4".U) { io.alu_op := I_Operations.I_XORI }
				is ("h6".U) { io.alu_op := I_Operations.I_ORI }
				is ("h7".U) { io.alu_op := I_Operations.I_ANDI }
				is ("h1".U) { io.alu_op := I_Operations.I_SLLI } // TODO: check this
				is ("h5".U) {
					// srl or sra
					// imm[5:11] = x0 for srl x2 for sra
					when (funct7(5)) { // checking the 6th bit of funct7, does pretty much the same as the above
						io.alu_op := I_Operations.I_SRA
					}.otherwise {
						io.alu_op := I_Operations.I_SRL
					}
				}
				is ("h2".U) { io.alu_op := I_Operations.I_SLTI }
				is ("h3".U) { io.alu_op := I_Operations.I_SLTIU }

				otherwise {
					// illegal instruction
					io.illegal := true.B
				}
			}
		}

		is (Opcodes.I_TYPE2){ // LOAD operations

			io.rf_wen := true.B
			io.wb_sel := true.B // write back select
			io.mem_size := funct3 // memory size
			switch (funct3) {
				is ("h0".U) { io.mem_type := I_Operations.I_LB }
				is ("h1".U) { io.mem_type := I_Operations.I_LH }
				is ("h2".U) { io.mem_type := I_Operations.I_LW }
				is ("h4".U) { io.mem_type := I_Operations.I_LBU }
				is ("h5".U) { io.mem_type := I_Operations.I_LHU }

				otherwise {
					// illegal instruction
					io.illegal := true.B
				}
			}
		}

		is (Opcodes.I_TYPE3){ // JALR operations
			switch (funct3) {
				is ("h0".U) { io.alu_op := I_Operations.I_JALR }
				otherwise {
					// illegal instruction
					io.illegal := true.B
				}
			}
		}

		is (Opcodes.I_TYPE4){ // CSR operations
			// ecall or ebreak
			switch (i_imm(8)) {
				is (0.U) { io.csr_cmd := I_Operations.I_ECALL }
				is (1.U) { io.csr_cmd := I_Operations.I_EBREAK }
				otherwise {
					// illegal instruction
					io.illegal := true.B
				}

			} // checking the bit is enough
		}

		is (Opcodes.S_TYPE){ // STORE operations
			switch (funct3) {
				is ("h0".U) { io.mem_type := I_Operations.I_SB }
				is ("h1".U) { io.mem_type := I_Operations.I_SH }
				is ("h2".U) { io.mem_type := I_Operations.I_SW }

				otherwise {
					// illegal instruction
					io.illegal := true.B
				}
			}
		}


		is (Opcodes.B_TYPE){ // BRANCH operations
			io.br_type := funct3
			switch (funct3) {
				is ("h0".U) { io.alu_op := I_Operations.I_BEQ }
				is ("h1".U) { io.alu_op := I_Operations.I_BNE }
				is ("h4".U) { io.alu_op := I_Operations.I_BLT }
				is ("h5".U) { io.alu_op := I_Operations.I_BGE }
				is ("h6".U) { io.alu_op := I_Operations.I_BLTU }
				is ("h7".U) { io.alu_op := I_Operations.I_BGEU }

				d {
					// illegal instruction
					io.illegal := true.B
				}
			}
		}

		is (Opcodes.U_TYPE1){ // LUI operations
		}
		is (Opcodes.U_TYPE2){ // AUIPC operations
		}
		is (Opcodes.J_TYPE){ // JAL operations
		}


		// // otherwise, set illegal flag
		default {
			io.illegal := true.B
		}
	}
}


class InstructionDecoderTest extends AnyFlatSpec with ChiselScalatestTester {
	"InstructionDecoder" should "decode R_TYPE instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: ADD instruction (opcode = R_TYPE, funct3 = 0, funct7 = 0)
			dut.io.inst.poke("b00000000000100001000000010110011".U) // ADD x1, x2, x3
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.rs1_addr.expect(2.U) // rs1 = x2
			dut.io.rs2_addr.expect(3.U) // rs2 = x3
			dut.io.alu_op.expect(R_Operations.I_ADD)
			dut.io.rf_wen.expect(true.B)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "decode I_TYPE1 (ALU immediate) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: ADDI instruction (opcode = I_TYPE1, funct3 = 0)
			dut.io.inst.poke("b00000000000100001000000010010011".U) // ADDI x1, x2, 1
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.rs1_addr.expect(2.U) // rs1 = x2
			dut.io.alu_op.expect(I_Operations.I_ADDI)
			dut.io.rf_wen.expect(true.B)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "decode I_TYPE2 (LOAD) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: LW instruction (opcode = I_TYPE2, funct3 = 2)
			dut.io.inst.poke("b00000000000100001010000000000011".U) // LW x1, 1(x2)
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.rs1_addr.expect(2.U) // rs1 = x2
			dut.io.mem_type.expect(I_Operations.I_LW)
			dut.io.mem_size.expect(2.U) // funct3 = 2
			dut.io.rf_wen.expect(true.B)
			dut.io.wb_sel.expect(true.B)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "decode S_TYPE (STORE) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: SW instruction (opcode = S_TYPE, funct3 = 2)
			dut.io.inst.poke("b00000000000100001010000000100011".U) // SW x1, 1(x2)
			dut.io.pc.poke(0.U)

			dut.io.rs1_addr.expect(2.U) // rs1 = x2
			dut.io.rs2_addr.expect(1.U) // rs2 = x1
			dut.io.mem_type.expect(I_Operations.I_SW)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "decode B_TYPE (BRANCH) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: BEQ instruction (opcode = B_TYPE, funct3 = 0)
			dut.io.inst.poke("b00000000000100001000000001100011".U) // BEQ x1, x2, offset
			dut.io.pc.poke(0.U)

			dut.io.rs1_addr.expect(1.U) // rs1 = x1
			dut.io.rs2_addr.expect(2.U) // rs2 = x2
			dut.io.br_type.expect(0.U) // funct3 = 0
			dut.io.illegal.expect(false.B)
		}
	}

	it should "flag illegal instructions" in {
		test(new InstructionDecoder) { dut =>
			// Example: Illegal instruction
			dut.io.inst.poke("b11111111111111111111111111111111".U) // Invalid opcode
			dut.io.pc.poke(0.U)

			dut.io.illegal.expect(true.B)
		}
	}

	it should "decode U_TYPE1 (LUI) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: LUI instruction (opcode = U_TYPE1)
			dut.io.inst.poke("b00000000000100001000000010110111".U) // LUI x1, 0x123
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.imm.expect("h123000".U) // imm = 0x123 shifted left 12 bits
			dut.io.rf_wen.expect(true.B)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "decode U_TYPE2 (AUIPC) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: AUIPC instruction (opcode = U_TYPE2)
			dut.io.inst.poke("b00000000000100001000000010010111".U) // AUIPC x1, 0x123
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.imm.expect("h123000".U) // imm = 0x123 shifted left 12 bits
			dut.io.rf_wen.expect(true.B)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "decode J_TYPE (JAL) instructions correctly" in {
		test(new InstructionDecoder) { dut =>
			// Example: JAL instruction (opcode = J_TYPE)
			dut.io.inst.poke("b00000000000100001000000011011111".U) // JAL x1, offset
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.rf_wen.expect(true.B)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "handle edge cases for immediate values" in {
		test(new InstructionDecoder) { dut =>
			// Example: ADDI with maximum positive immediate
			dut.io.inst.poke("b01111111111100001000000010010011".U) // ADDI x1, x2, 2047
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.rs1_addr.expect(2.U) // rs1 = x2
			dut.io.imm.expect(2047.U) // imm = 2047
			dut.io.illegal.expect(false.B)

			// Example: ADDI with maximum negative immediate
			dut.io.inst.poke("b10000000000000001000000010010011".U) // ADDI x1, x2, -2048
			dut.io.pc.poke(0.U)

			dut.io.rd_addr.expect(1.U) // rd = x1
			dut.io.rs1_addr.expect(2.U) // rs1 = x2
			dut.io.imm.expect("hFFFFF800".U) // imm = -2048 (sign-extended)
			dut.io.illegal.expect(false.B)
		}
	}

	it should "flag illegal instructions for unknown opcodes" in {
		test(new InstructionDecoder) { dut =>
			// Example: Invalid opcode
			dut.io.inst.poke("b00000000000000000000000000000000".U) // Invalid opcode
			dut.io.pc.poke(0.U)

			dut.io.illegal.expect(true.B)
		}
	}
}
```



#### Error stacktrace:

```
scala.collection.mutable.ArrayBuffer.apply(ArrayBuffer.scala:106)
	scala.reflect.internal.Types$Type.findMemberInternal$1(Types.scala:1030)
	scala.reflect.internal.Types$Type.findMember(Types.scala:1035)
	scala.reflect.internal.Types$Type.memberBasedOnName(Types.scala:661)
	scala.reflect.internal.Types$Type.nonPrivateMember(Types.scala:632)
	scala.tools.nsc.typechecker.Infer$Inferencer.followApply(Infer.scala:661)
	scala.tools.nsc.typechecker.Infer$Inferencer$InferMethodAlternativeTwice$1.followType(Infer.scala:1522)
	scala.tools.nsc.typechecker.Infer$Inferencer$InferMethodAlternativeTwice$1.rankAlternatives(Infer.scala:1525)
	scala.tools.nsc.typechecker.Infer$Inferencer$InferMethodAlternativeTwice$1.$anonfun$bestForExpectedType$2(Infer.scala:1529)
	scala.tools.nsc.typechecker.Infer$Inferencer$InferMethodAlternativeTwice$1.bestForExpectedType(Infer.scala:1529)
	scala.tools.nsc.typechecker.Infer$Inferencer$InferMethodAlternativeTwice$1.tryOnce(Infer.scala:1542)
	scala.tools.nsc.typechecker.Contexts$Context$TryTwice.$anonfun$apply$1(Contexts.scala:587)
	scala.tools.nsc.typechecker.Contexts$Context$TryTwice.apply(Contexts.scala:655)
	scala.tools.nsc.typechecker.Infer$Inferencer.inferMethodAlternative(Infer.scala:1546)
	scala.tools.nsc.typechecker.Typers$Typer.handleOverloaded$1(Typers.scala:3639)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:3643)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typed1$28(Typers.scala:5093)
	scala.tools.nsc.typechecker.Typers$Typer.silent(Typers.scala:712)
	scala.tools.nsc.typechecker.Typers$Typer.tryTypedApply$1(Typers.scala:5093)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5181)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5194)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6097)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typed1$41(Typers.scala:5160)
	scala.tools.nsc.typechecker.Typers$Typer.silent(Typers.scala:698)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5162)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5194)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6097)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6231)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$8(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedBlock(Typers.scala:2597)
	scala.tools.nsc.typechecker.Typers$Typer.typedOutsidePatternMode$1(Typers.scala:6071)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6107)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg(Typers.scala:3488)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgWithFormal$1(PatternTypers.scala:134)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.$anonfun$typedArgsForFormals$4(PatternTypers.scala:150)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgsForFormals(PatternTypers.scala:150)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgsForFormals$(PatternTypers.scala:131)
	scala.tools.nsc.typechecker.Typers$Typer.typedArgsForFormals(Typers.scala:203)
	scala.tools.nsc.typechecker.Typers$Typer.handleMonomorphicCall$1(Typers.scala:3823)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:3874)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5183)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5194)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6097)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg(Typers.scala:3488)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgWithFormal$1(PatternTypers.scala:134)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.$anonfun$typedArgsForFormals$4(PatternTypers.scala:150)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgsForFormals(PatternTypers.scala:150)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgsForFormals$(PatternTypers.scala:131)
	scala.tools.nsc.typechecker.Typers$Typer.typedArgsForFormals(Typers.scala:203)
	scala.tools.nsc.typechecker.Typers$Typer.handleMonomorphicCall$1(Typers.scala:3823)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:3874)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5183)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5194)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6097)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6231)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$8(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedBlock(Typers.scala:2597)
	scala.tools.nsc.typechecker.Typers$Typer.typedOutsidePatternMode$1(Typers.scala:6071)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6107)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg(Typers.scala:3488)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgWithFormal$1(PatternTypers.scala:134)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.$anonfun$typedArgsForFormals$4(PatternTypers.scala:150)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgsForFormals(PatternTypers.scala:150)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgsForFormals$(PatternTypers.scala:131)
	scala.tools.nsc.typechecker.Typers$Typer.typedArgsForFormals(Typers.scala:203)
	scala.tools.nsc.typechecker.Typers$Typer.handleMonomorphicCall$1(Typers.scala:3823)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:3874)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5183)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5194)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6097)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6231)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$8(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedTemplate(Typers.scala:2089)
	scala.tools.nsc.typechecker.Typers$Typer.typedClassDef(Typers.scala:1927)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6060)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6231)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$8(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedPackageDef$1(Typers.scala:5743)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6063)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Analyzer$typerFactory$TyperPhase.apply(Analyzer.scala:124)
	scala.tools.nsc.Global$GlobalPhase.applyPhase(Global.scala:480)
	scala.tools.nsc.interactive.Global$TyperRun.applyPhase(Global.scala:1370)
	scala.tools.nsc.interactive.Global$TyperRun.typeCheck(Global.scala:1363)
	scala.tools.nsc.interactive.Global.typeCheck(Global.scala:680)
	scala.meta.internal.pc.Compat.$anonfun$runOutline$1(Compat.scala:57)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:576)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:574)
	scala.collection.AbstractIterable.foreach(Iterable.scala:933)
	scala.meta.internal.pc.Compat.runOutline(Compat.scala:49)
	scala.meta.internal.pc.Compat.runOutline(Compat.scala:35)
	scala.meta.internal.pc.Compat.runOutline$(Compat.scala:33)
	scala.meta.internal.pc.MetalsGlobal.runOutline(MetalsGlobal.scala:36)
	scala.meta.internal.pc.ScalaCompilerWrapper.compiler(ScalaCompilerAccess.scala:19)
	scala.meta.internal.pc.ScalaCompilerWrapper.compiler(ScalaCompilerAccess.scala:14)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$semanticTokens$1(ScalaPresentationCompiler.scala:195)
```
#### Short summary: 

java.lang.IndexOutOfBoundsException: -1 is out of bounds (min 0, max 2)