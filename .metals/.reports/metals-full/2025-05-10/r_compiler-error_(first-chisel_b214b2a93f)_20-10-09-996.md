file://<WORKSPACE>/riscv/decoder.scala
### scala.ScalaReflectionException: value j_imm is not a method

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
import riscv.{Constants, Opcodes, InstructionTypes}
import riscv.Instructions._

// instruction decoder for RISC-V
import chisel3.util.Fill
import chisel3.util.is
import chisel3.util.switch
import chisel3.when
import firrtl2.backends.experimental.smt.Op


class InstructionFetcher extends Module {
  print("Instruction Fetcher\n", Constants)
  val io = IO(new Bundle {
    val inst = Input(UInt(32.W))
    val pc = Input(UInt(7.W))

    val rd_addr   = Output(UInt(5.W))
    val rs1_addr  = Output(UInt(5.W))
    val rs2_addr  = Output(UInt(5.W))
    val imm       = Output(UInt(32.W))
    // Renamed _type to _op to match usage in code
    val _op    = Output(Instructions())
    val br_type   = Output(UInt(3.W))
    val mem_size  = Output(UInt(3.W)) 
    val wb_sel    = Output(Bool()) 
    val rf_wen    = Output(Bool()) 
    val csr_cmd_en   = Output(UInt(3.W))
    val illegal   = Output(Bool())
    val inst_type = Output(InstructionTypes())
  })

  // Default values
  io.rd_addr := io.inst(11, 7)
  io.rs1_addr := io.inst(19, 15)
  io.rs2_addr := io.inst(24, 20)
  io.imm := 0.U
  io._op := Instructions.NOP
  io.br_type := 0.U
  io.mem_size := 0.U
  io.wb_sel := false.B
  io.rf_wen := false.B
  io.csr_cmd_en := false.B
  io.illegal := false.B
  io.inst_type := InstructionTypes.NOP
  val opcode = io.inst(6, 0)
  val funct3 = io.inst(14, 12)
  val funct7 = io.inst(31, 25)

  // Immediate value extraction
  val i_imm = Cat(Fill(20, io.inst(31)), io.inst(31, 20)) // Sign-extended
  val s_imm = Cat(Fill(20, io.inst(31)), io.inst(31, 25), io.inst(11, 7)) // Sign-extended
  val u_imm = Cat(io.inst(31, 12), 0.U(12.W))
	//  val b_imm = Cat(Fill(19, io.inst(31)), io.inst(31), io.inst(7), io.inst(30, 25), io.inst(11, 8), 0.U(1.W)) // Sign-extended
  val j_imm = Cat(Fill(20, io.inst(31)), io.inst(31), io.inst(19, 12), io.inst(20), io.inst(30, 21), 0.U(1.W))/ // Sign-extended
//   j_imm := j_imm.reverse
  // https://github.com/msyksphinz-self/chisel-soc/blob/main/src/main/scala/core/cpu.scala#L119
  val b_imm_sext_      = Cat(io.inst(31), io.inst(7), io.inst(30,25), io.inst(11,8))
  val b_imm = Cat(Fill(19,b_imm_sext_(11)), b_imm_sext_, 0.U)
  val j_imm_sext_      = Cat(Fill(11, io.inst(31)), io.inst(31), io.inst(19,12), io.inst(20), io.inst(30,21), 0.U(1.W))
//   val j_imm = Cat(Fill(64-21, j_imm_sext_(20)), j_imm_sext_, 0.U(1.W))


  // Fetch the instruction based on the opcode
   when (opcode === Opcodes.R_TYPE) {
      io.inst_type := InstructionTypes.R_TYPE
      io.rf_wen := true.B

      when (funct3 === "b000".U) { 
          // add or sub
          when (funct7(5)) {
            io._op := R_I_SUB
          }.otherwise {
            io._op := R_I_ADD
          }
      }.elsewhen(funct3 === "h4".U) { io._op := R_I_XOR 
		}.elsewhen(funct3 === "h6".U) { io._op := R_I_OR 
		}.elsewhen(funct3 === "h7".U) { io._op := R_I_AND 
		}.elsewhen(funct3 === "h1".U) { io._op := R_I_SLL 
		}.elsewhen(funct3 === "h5".U) {
          when (funct7(5)) {
            io._op := R_I_SRA
          }.otherwise {
            io._op := R_I_SRL
          }
        
      }.elsewhen(funct3 === "h2".U) { io._op := R_I_SLT 
		}.elsewhen(funct3 === "h3".U) { io._op := R_I_SLTU 
		}.otherwise {
			io.illegal := true.B
		}

   }.elsewhen (opcode === Opcodes.I_TYPE1) { // ALU immediate operations
      io.imm := i_imm
      io.inst_type := InstructionTypes.I_TYPE
      io.rf_wen := true.B

      when (funct3 === "h0".U) { 
			io._op := I_I_ADDI 
		}.elsewhen (funct3 === "h4".U) { 
			io._op := I_I_XORI 
		}.elsewhen (funct3 === "h6".U) { 
			io._op := I_I_ORI 
		}.elsewhen(funct3 === "h7".U) { 
			io._op := I_I_ANDI 
		}.elsewhen(funct3 === "h1".U) { 
			io._op := I_I_SLLI 
		}.elsewhen (funct3 === "h5".U) {
          when (funct7(5)) {
            io._op := I_I_SRAI
          }.otherwise {
            io._op := I_I_SRLI
          }
      }.elsewhen (funct3 === "h2".U) { 
			io._op := I_I_SLTI 
		}.elsewhen (funct3 === "h3".U) { 
			io._op := I_I_SLTIU
		}.otherwise {
		  io.illegal := true.B
      }

	}.elsewhen (opcode === Opcodes.I_TYPE2) { // LOAD operations
      io.imm := i_imm
      io.inst_type := InstructionTypes.I_TYPE
      io.rf_wen := true.B
      io.wb_sel := true.B
      io.mem_size := funct3

      when (funct3 === "h0".U) { 
			io._op := I_I_LB 
		}.elsewhen (funct3 === "h1".U) {
			io._op := I_I_LH 
      }.elsewhen  (funct3 === "h2".U) { 
			io._op := I_I_LW 
     	}.elsewhen  (funct3 === "h4".U) { 
			io._op := I_I_LBU 
     	}.elsewhen  (funct3 === "h5".U) { 
			io._op := I_I_LHU 
      }.otherwise {
		  io.illegal := true.B
      }

	}.elsewhen (opcode === Opcodes.I_TYPE3) { // JALR operations
      io.imm := i_imm
      io.inst_type := InstructionTypes.I_TYPE
      io.rf_wen := true.B // JALR writes to rd

      when (funct3 === "h0".U) { 
			io._op := I_I_JALR 
		}.otherwise {
			io.illegal := true.B
      }

	}.elsewhen (opcode === Opcodes.I_TYPE4) { // CSR operations
      io.imm := i_imm
      io.inst_type := InstructionTypes.I_TYPE
      // Fixed CSR handling
		io.csr_cmd_en := true.B
      when (funct3 === "b000".U) {
        when (io.imm === 0.U) {
          io._op := I_I_ECALL
        }.elsewhen (io.imm === 1.U) {
          io._op := I_I_EBREAK
        }.otherwise {
         io.illegal := true.B
        	io._op := NOP
			io.rf_wen := true.B
      	}
		}

	}.elsewhen (opcode === Opcodes.S_TYPE) { // STORE operations

      io.imm := s_imm
      io.inst_type := InstructionTypes.S_TYPE
      io.mem_size := funct3
      
		when (funct3 === f"h0".U) { 
			io._op := S_I_SB 
   	}.elsewhen (funct3 === f"h1".U) { 
			io._op := S_I_SH 
		}.elsewhen (funct3 === f"h2".U) { 
			io._op := S_I_SW 
		}.otherwise { 
			io.illegal := true.B 
      }

	}.elsewhen (opcode === Opcodes.B_TYPE) { // BRANCH operations
		      

      io.imm := b_imm
      io.inst_type := InstructionTypes.B_TYPE
      io.br_type := funct3
      
   	when (funct3 === "h0".U) { 
			io._op := B_I_BEQ 
		}.elsewhen (funct3 === "h1".U) {
			io._op := B_I_BNE 
		}.elsewhen (funct3 === "h4".U) { 
			io._op := B_I_BLT
		}.elsewhen (funct3 === "h5".U) { 
			io._op := B_I_BGE 
		}.elsewhen (funct3 === "h6".U) { 
			io._op := B_I_BLTU 
		}.elsewhen (funct3 === "h7".U) { 
			io._op := B_I_BGEU 
		}.otherwise {
			io.illegal := true.B 
      }


	}.elsewhen (opcode === Opcodes.U_TYPE1) { // LUI operations
      io.imm := u_imm
      io.inst_type := InstructionTypes.U_TYPE
      io.rf_wen := true.B
      io._op := U_I_LUI
    
	}.elsewhen (opcode === Opcodes.U_TYPE2) { // AUIPC operations
      io.imm := u_imm
      io.inst_type := InstructionTypes.U_TYPE
      io.rf_wen := true.B
      io._op := U_I_AUIPC
    
	}.elsewhen (opcode === Opcodes.J_TYPE) { // JAL operations
      io.imm := j_imm
      io.inst_type := InstructionTypes.J_TYPE
      io.rf_wen := true.B
      io._op := J_I_JAL

	}.otherwise{
      io.illegal := true.B
    }
  
}

class InstructionFetcherTest extends AnyFlatSpec with ChiselScalatestTester {
  
  // Helper function to simplify common test patterns
def testInstruction(
    inst: UInt, 
    expectedOp: Instructions.Type,
    expectedRdAddr: Int = 0,
    expectedRs1Addr: Int = 0,
    expectedRs2Addr: Int = 0,
    expectedImm: BigInt = 0,
    expectedBrType: Int = 0,
    expectedMemSize: Int = 0,
    expectedWbSel: Boolean = false,
    expectedRfWen: Boolean = false,
    expectedCsrCmdEn: Boolean = false,
    expectedIllegal: Boolean = false,
    expectedInstType: InstructionTypes.Type = InstructionTypes.NOP
  )(implicit dut: InstructionFetcher): Unit = {
    // Common setup
    dut.io.inst.poke(inst)
    dut.io.pc.poke(0.U)
    
    // Common expectations for all instruction types
    dut.io.illegal.expect(expectedIllegal.B)
    dut.io.inst_type.expect(expectedInstType)
    dut.io._op.expect(expectedOp)
    
    // Type-specific expectations
    expectedInstType match {
      case InstructionTypes.R_TYPE => 
        // R-type expectations
        dut.io.rd_addr.expect(expectedRdAddr.U)
        dut.io.rs1_addr.expect(expectedRs1Addr.U)
        dut.io.rs2_addr.expect(expectedRs2Addr.U)
        dut.io.rf_wen.expect(expectedRfWen.B)
        // R-type instructions don't use immediates
        dut.io.imm.expect(0.U)
      
      case InstructionTypes.I_TYPE =>
        // I-type expectations  
        dut.io.rd_addr.expect(expectedRdAddr.U)
        dut.io.rs1_addr.expect(expectedRs1Addr.U)
        dut.io.imm.expect(expectedImm.U)
        dut.io.rf_wen.expect(expectedRfWen.B)
        dut.io.wb_sel.expect(expectedWbSel.B)
        dut.io.csr_cmd_en.expect(expectedCsrCmdEn.B)
        
      case InstructionTypes.S_TYPE =>
        // S-type expectations
        dut.io.rs1_addr.expect(expectedRs1Addr.U)
        dut.io.rs2_addr.expect(expectedRs2Addr.U)
        dut.io.imm.expect(expectedImm.U)
        dut.io.mem_size.expect(expectedMemSize.U)
        
      case InstructionTypes.B_TYPE =>
        // B-type expectations
        dut.io.rs1_addr.expect(expectedRs1Addr.U)
        dut.io.rs2_addr.expect(expectedRs2Addr.U)
        dut.io.imm.expect(expectedImm.U)
        dut.io.br_type.expect(expectedBrType.U)
        
      case InstructionTypes.U_TYPE =>
        // U-type expectations
        dut.io.rd_addr.expect(expectedRdAddr.U)
        dut.io.imm.expect(expectedImm.U)
        dut.io.rf_wen.expect(expectedRfWen.B)
        
      case InstructionTypes.J_TYPE =>
        // J-type expectations
        dut.io.rd_addr.expect(expectedRdAddr.U)
        dut.io.imm.expect(expectedImm.U)
        dut.io.rf_wen.expect(expectedRfWen.B)
        
      case _ => 
        // NOP or other types
        // Only check the common expectations
    }
  }

  // R-TYPE INSTRUCTIONS
  "InstructionFetcher" should "decode ADD instruction correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // ADD x1, x2, x3
      val inst = "b00000000001100010000000010110011".U
      testInstruction(
        inst = inst,
        expectedOp = R_I_ADD,
        expectedRdAddr = 1,
        expectedRs1Addr = 2,
        expectedRs2Addr = 3,
        expectedRfWen = true,
        expectedInstType = InstructionTypes.R_TYPE
      )
    }
  }

  it should "decode SUB instruction correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // SUB x5, x6, x7
      val inst = "b01000000011101100000001010110011".U
      testInstruction(
        inst = inst,
		  expectedInstType = InstructionTypes.R_TYPE,
        expectedOp = R_I_SUB,
        expectedRdAddr = 5,
        expectedRs1Addr = 12,
        expectedRs2Addr = 7,
        expectedRfWen = true,
      )
    }
  }

  it should "decode XOR instruction correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // XOR x8, x9, x10
      val inst = "b00000000101001001100010000110011".U
      testInstruction(
        inst = inst,
        expectedOp = R_I_XOR,
        expectedRdAddr = 8,
        expectedRs1Addr = 9,
        expectedRs2Addr = 10,
        expectedRfWen = true,
        expectedInstType = InstructionTypes.R_TYPE
      )
    }
  }

  // I-TYPE IMMEDIATE INSTRUCTIONS
  it should "decode ADDI instruction correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // ADDI x1, x2, 123
      val inst = "b00000111101100010000000010010011".U
      testInstruction(
        inst = inst,
        expectedOp = I_I_ADDI,
        expectedRdAddr = 1,
        expectedRs1Addr = 2,
		  expectedRs2Addr = 0,
        expectedImm = 123,
        expectedRfWen = true,
        expectedInstType = InstructionTypes.I_TYPE
      )
    }
  }

  it should "decode ANDI instruction correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // ANDI x3, x4, -1
      val inst = "b11111111111100100111000110010011".U
      testInstruction(
        inst = inst,
        expectedOp = I_I_ANDI,
        expectedRdAddr = 3,
        expectedRs1Addr = 4,
        expectedImm = BigInt("FFFFFFFF", 16), // -1 sign-extended to 32 bits
        expectedRfWen = true,
        expectedInstType = InstructionTypes.I_TYPE
      )
    }
  }

  // I-TYPE LOAD INSTRUCTIONS
  it should "decode LW instruction correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // LW x5, 16(x6)
      val inst = "b00000001000000110010001010000011".U
      testInstruction(
        inst = inst,
        expectedOp = I_I_LW,
        expectedRdAddr = 5,
        expectedRs1Addr = 6,
        expectedImm = 16,
        expectedMemSize = 2,
        expectedWbSel = true,
        expectedRfWen = true,
        expectedInstType = InstructionTypes.I_TYPE
      )
    }
  }

  it should "decode LB instruction correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // LB x7, -4(x8)
      val inst = "b11111111110001000000001110000011".U
      testInstruction(
        inst = inst,
        expectedOp = I_I_LB,
        expectedRdAddr = 7,
        expectedRs1Addr = 8,
        expectedImm = BigInt("FFFFFFFC", 16), // -4 sign-extended
        expectedMemSize = 0,
        expectedWbSel = true,
        expectedRfWen = true,
        expectedInstType = InstructionTypes.I_TYPE
      )
    }
  }

  // S-TYPE INSTRUCTIONS
  it should "decode SW instruction correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // SW x10, 12(x11)
      val inst = "b00000000101001011010011000100011".U
      testInstruction(
        inst = inst,
        expectedOp = S_I_SW,
        expectedRs1Addr = 11,
        expectedRs2Addr = 10,
        expectedImm = 12,
        expectedMemSize = 2,
        expectedInstType = InstructionTypes.S_TYPE
      )
    }
  }

//   // B-TYPE INSTRUCTIONS
//   it should "decode BEQ instruction correctly" in {
//     test(new InstructionFetcher) { implicit dut =>
//       // BEQ x12, x27, 100
//       val inst = "b00000001101101100000100001100011".U
//       testInstruction(
//         inst = inst,
//         expectedOp = B_I_BEQ,
//         expectedRs1Addr = 12,
//         expectedRs2Addr = 27,
//         expectedImm = 100,
//         expectedBrType = 0,
//         expectedInstType = InstructionTypes.B_TYPE
//       )
//     }
//   }

//   it should "decode BNE instruction correctly" in {
//     test(new InstructionFetcher) { implicit dut =>
//       // BNE x14, x15, -8
//       val inst = "b11111110111101110001000001100011".U
//       testInstruction(
//         inst = inst,
//         expectedOp = B_I_BNE,
//         expectedRs1Addr = 14,
//         expectedRs2Addr = 15,
//         expectedImm = BigInt("FFFFFFF8", 16), // -8 sign-extended
//         expectedBrType = 1,
//         expectedInstType = InstructionTypes.B_TYPE
//       )
//     }
//   }

  // U-TYPE INSTRUCTIONS
  it should "decode LUI instruction correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // LUI x16, 0xABCDE
      val inst = "b10101011110011011110100000110111".U
      testInstruction(
        inst = inst,
        expectedOp = U_I_LUI,
        expectedRdAddr = 16,
        expectedImm = BigInt("ABCDE000", 16),
        expectedRfWen = true,
        expectedInstType = InstructionTypes.U_TYPE
      )
    }
  }

  it should "decode AUIPC instruction correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // AUIPC x17, 0x12345
      val inst = "b00010010001101000101100010010111".U
      testInstruction(
        inst = inst,
        expectedOp = U_I_AUIPC,
        expectedRdAddr = 17,
        expectedImm = BigInt("12345000", 16),
        expectedRfWen = true,
        expectedInstType = InstructionTypes.U_TYPE
      )
    }
  }

//   J-TYPE INSTRUCTIONS
  it should "decode JAL instruction correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // JAL x18, 1024
      val inst = "b00000000010000000000100101101111".U
      testInstruction(
        inst = inst,
        expectedOp = J_I_JAL,
        expectedRdAddr = 18,
        expectedImm = 1024,
        expectedRfWen = true,
        expectedInstType = InstructionTypes.J_TYPE
      )
    }
  }

  // I-TYPE JALR INSTRUCTIONS
  it should "decode JALR instruction correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // JALR x19, x20, 64
      val inst = "b00000100000010100000100111100111".U
      testInstruction(
        inst = inst,
        expectedOp = I_I_JALR,
        expectedRdAddr = 19,
        expectedRs1Addr = 20,
        expectedImm = 64,
        expectedRfWen = true,
        expectedInstType = InstructionTypes.I_TYPE
      )
    }
  }

  // Illegal instruction tests
  it should "flag illegal instructions for unknown opcodes" in {
    test(new InstructionFetcher) { implicit dut =>
      val inst = "b00000000000000000000000000000000".U // All zeros
      testInstruction(
        inst = inst,
        expectedOp = Instructions.NOP,
        expectedIllegal = true
      )
    }
  }

  it should "flag illegal I-TYPE2 instructions with invalid funct3" in {
    test(new InstructionFetcher) { implicit dut =>
      // Invalid ADDI-like instruction with funct3=7'b110
      val inst = "b00000000000100000110000010000011".U
      dut.io.inst.poke(inst)
      dut.io.illegal.expect(true.B)
    }
  }

  // Edge cases
  it should "handle maximum positive immediate correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // ADDI x21, x22, 2047 (max positive 12-bit immediate)
      val inst = "b01111111111110110000101010010011".U
      testInstruction(
        inst = inst,
        expectedOp = I_I_ADDI,
        expectedRdAddr = 21,
        expectedRs1Addr = 22,
        expectedImm = 2047,
        expectedRfWen = true,
        expectedInstType = InstructionTypes.I_TYPE
      )
    }
  }

  it should "handle maximum negative immediate correctly" in {
    test(new InstructionFetcher) { implicit dut =>
      // ADDI x23, x24, -2048 (max negative 12-bit immediate)
      val inst = "b10000000000011000000101110010011".U
      testInstruction(
        inst = inst,
        expectedOp = I_I_ADDI,
        expectedRdAddr = 23,
        expectedRs1Addr = 24,
        expectedImm = BigInt("FFFFF800", 16), // -2048 sign-extended
        expectedRfWen = true,
        expectedInstType = InstructionTypes.I_TYPE
      )
    }
  }
}
// object Main extends App {
//   println(
//     ChiselStage.emitSystemVerilog(
// 		gen = new InstructionFetcher,
//       firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
//     )
//   )
// }

```



#### Error stacktrace:

```
scala.reflect.api.Symbols$SymbolApi.asMethod(Symbols.scala:240)
	scala.reflect.api.Symbols$SymbolApi.asMethod$(Symbols.scala:234)
	scala.reflect.internal.Symbols$SymbolContextApiImpl.asMethod(Symbols.scala:99)
	scala.tools.nsc.typechecker.ContextErrors$TyperContextErrors$TyperErrorGen$.MissingArgsForMethodTpeError(ContextErrors.scala:795)
	scala.tools.nsc.typechecker.Typers$Typer.adaptMethodTypeToExpr$1(Typers.scala:984)
	scala.tools.nsc.typechecker.Typers$Typer.adapt(Typers.scala:1305)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6168)
	scala.tools.nsc.typechecker.Typers$Typer.typedDefDef(Typers.scala:6417)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6059)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6231)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$4(Typers.scala:3422)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$4$adapted(Typers.scala:3417)
	scala.reflect.internal.Scopes$Scope.foreach(Scopes.scala:455)
	scala.tools.nsc.typechecker.Typers$Typer.addSynthetics$1(Typers.scala:3417)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3482)
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
	scala.meta.internal.pc.WithCompilationUnit.<init>(WithCompilationUnit.scala:22)
	scala.meta.internal.pc.SimpleCollector.<init>(PcCollector.scala:340)
	scala.meta.internal.pc.PcSemanticTokensProvider$Collector$.<init>(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector$lzycompute$1(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.provide(PcSemanticTokensProvider.scala:73)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$semanticTokens$1(ScalaPresentationCompiler.scala:196)
```
#### Short summary: 

scala.ScalaReflectionException: value j_imm is not a method