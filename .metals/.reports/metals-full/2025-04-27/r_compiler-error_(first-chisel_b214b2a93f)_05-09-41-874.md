file://<WORKSPACE>/mini-gpu/dispatch.scala
### java.lang.StringIndexOutOfBoundsException: offset 3679, count -7, length 6359

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.13.12
Classpath:
<WORKSPACE>/.scala-build/first-chisel_b214b2a93f/classes/main [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/chipsalliance/chisel_2.13/6.7.0/chisel_2.13-6.7.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/edu/berkeley/cs/chiseltest_2.13/6.0.0/chiseltest_2.13-6.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/scopt/scopt_2.13/4.1.0/scopt_2.13-4.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/net/jcazevedo/moultingyaml_2.13/0.4.2/moultingyaml_2.13-0.4.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-native_2.13/4.1.0-M4/json4s-native_2.13-4.1.0-M4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-text/1.10.0/commons-text-1.10.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/github/alexarchambault/data-class_2.13/0.2.6/data-class_2.13-0.2.6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/os-lib_2.13/0.9.2/os-lib_2.13-0.9.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-parallel-collections_2.13/1.0.4/scala-parallel-collections_2.13-1.0.4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle_2.13/3.1.0/upickle_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/chipsalliance/firtool-resolver_2.13/1.3.0/firtool-resolver_2.13-1.3.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/edu/berkeley/cs/firrtl2_2.13/6.0.0/firrtl2_2.13-6.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest_2.13/3.2.17/scalatest_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/net/java/dev/jna/jna/5.14.0/jna-5.14.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/nscala-time/nscala-time_2.13/2.22.0/nscala-time_2.13-2.22.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/yaml/snakeyaml/1.26/snakeyaml-1.26.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-core_2.13/4.1.0-M4/json4s-core_2.13-4.1.0-M4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-native-core_2.13/4.1.0-M4/json4s-native-core_2.13-4.1.0-M4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.12.0/commons-lang3-3.12.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/geny_2.13/1.0.0/geny_2.13-1.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/ujson_2.13/3.1.0/ujson_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upack_2.13/3.1.0/upack_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle-implicits_2.13/3.1.0/upickle-implicits_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/dev/dirs/directories/26/directories-26.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/outr/scribe_2.13/3.13.0/scribe_2.13-3.13.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/get-coursier/coursier_2.13/2.1.8/coursier_2.13-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/antlr/antlr4-runtime/4.9.3/antlr4-runtime-4.9.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-core_2.13/3.2.17/scalatest-core_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-featurespec_2.13/3.2.17/scalatest-featurespec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-flatspec_2.13/3.2.17/scalatest-flatspec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-freespec_2.13/3.2.17/scalatest-freespec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-funsuite_2.13/3.2.17/scalatest-funsuite_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-funspec_2.13/3.2.17/scalatest-funspec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-propspec_2.13/3.2.17/scalatest-propspec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-refspec_2.13/3.2.17/scalatest-refspec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-wordspec_2.13/3.2.17/scalatest-wordspec_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-diagrams_2.13/3.2.17/scalatest-diagrams_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-matchers-core_2.13/3.2.17/scalatest-matchers-core_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-shouldmatchers_2.13/3.2.17/scalatest-shouldmatchers_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-mustmatchers_2.13/3.2.17/scalatest-mustmatchers_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/joda-time/joda-time/2.10.1/joda-time-2.10.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/joda/joda-convert/2.2.0/joda-convert-2.2.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-ast_2.13/4.1.0-M4/json4s-ast_2.13-4.1.0-M4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-scalap_2.13/4.1.0-M4/json4s-scalap_2.13-4.1.0-M4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle-core_2.13/3.1.0/upickle-core_2.13-3.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/outr/perfolation_2.13/1.2.9/perfolation_2.13-1.2.9.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/sourcecode_2.13/0.3.1/sourcecode_2.13-0.3.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-collection-compat_2.13/2.11.0/scala-collection-compat_2.13-2.11.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/outr/moduload_2.13/1.1.7/moduload_2.13-1.1.7.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/plokhotnyuk/jsoniter-scala/jsoniter-scala-core_2.13/2.13.5.2/jsoniter-scala-core_2.13-2.13.5.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/get-coursier/coursier-core_2.13/2.1.8/coursier-core_2.13-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/get-coursier/coursier-cache_2.13/2.1.8/coursier-cache_2.13-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/get-coursier/coursier-proxy-setup/2.1.8/coursier-proxy-setup-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalatest/scalatest-compatible/3.2.17/scalatest-compatible-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scalactic/scalactic_2.13/3.2.17/scalactic_2.13-3.2.17.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-xml_2.13/2.2.0/scala-xml_2.13-2.2.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/github/alexarchambault/concurrent-reference-hash-map/1.1.0/concurrent-reference-hash-map-1.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/get-coursier/coursier-util_2.13/2.1.8/coursier-util_2.13-2.1.8.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/get-coursier/jniutils/windows-jni-utils/0.3.3/windows-jni-utils-0.3.3.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/codehaus/plexus/plexus-archiver/4.9.0/plexus-archiver-4.9.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/codehaus/plexus/plexus-container-default/2.1.1/plexus-container-default-2.1.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/virtuslab/scala-cli/config_2.13/0.2.1/config_2.13-0.2.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/github/alexarchambault/windows-ansi/windows-ansi/0.0.5/windows-ansi-0.0.5.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/javax/inject/javax.inject/1/javax.inject-1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/codehaus/plexus/plexus-utils/4.0.0/plexus-utils-4.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/codehaus/plexus/plexus-io/3.4.1/plexus-io-3.4.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/commons-io/commons-io/2.15.0/commons-io-2.15.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-compress/1.24.0/commons-compress-1.24.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/iq80/snappy/snappy/0.4/snappy-0.4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/tukaani/xz/1.9/xz-1.9.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/luben/zstd-jni/1.5.5-10/zstd-jni-1.5.5-10.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/codehaus/plexus/plexus-classworlds/2.6.0/plexus-classworlds-2.6.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/xbean/xbean-reflect/3.7/xbean-reflect-3.7.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/fusesource/jansi/jansi/1.18/jansi-1.18.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/sourcegraph/semanticdb-javac/0.10.0/semanticdb-javac-0.10.0.jar [exists ]
Options:
-unchecked ,"-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations" -language:implicitConversions -Yrangepos


action parameters:
offset: 3679
uri: file://<WORKSPACE>/mini-gpu/dispatch.scala
text:
```scala
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
import dataclass.data

// if X is an input, ready is an output, and valid and input
// but if X is an output, ready is an output and 
object ChannelStates extends ChiselEnum {
  val IDLE, READING, WRITING, RELAY_READ, RELAY_WRITE = Value
}

class ConsumerIO(numConsumers:Int, dataWidth: Int, addrWidth: Int) extends Bundle {
  val read = new Bundle {
    val ready = Output(Bool())
    val valid = Input(Bool())
    val bits = new Bundle {
      val address = Input(UInt(addrWidth.W))
      val data = Output(UInt(dataWidth.W))
    }
  }
  val write = Flipped(Decoupled(new Bundle {
    val address = UInt(addrWidth.W)
    val data = UInt(dataWidth.W)
  }))


  val read_ = new Bundle {
	 val ready = Input(Vec(numConsumers, Bool()))
	 val valid = Output(Vec(numConsumers, Bool()))
	 val address = Output(Vec(numConsumers, UInt(addrWidth.W)))
	 val data = Input(Vec(numConsumers, UInt(dataWidth.W)))
  }

  val write_ = new Bundle {
	 val ready = Output(Vec(numConsumers, Bool()))
	 val valid = Input(Vec(numConsumers, Bool()))
	 val address = Input(Vec(numConsumers, UInt(addrWidth.W)))
	 val data = Input(Vec(numConsumers, UInt(dataWidth.W)))
  }

}

/***
 * The GPU contains many cores. Interface of b/n core and controller is consumer
 * Interface between memory and controller is channelschannels
 * the consumer is based on number of cores
 * the channel is based on the bandwidth of memory
 */

class ChannelIO(numChannels:Int, dataWidth: Int, addrWidth: Int) extends Bundle {
  val read = new Bundle {
    val ready = Input(Bool())
    val valid = Output(Bool())
    val bits = new Bundle {
      val address = Output(UInt(addrWidth.W))
      val data = Input(UInt(dataWidth.W))
    }
  }
  val write = Decoupled(new Bundle {
    val address = UInt(addrWidth.W)
    val data = UInt(dataWidth.W)
  })

  val read_ = new Bundle {
	val ready = Output(Vec(numChannels, Bool()))
  	val valid = Input(Vec(numChannels, Bool())) // use a much more efficient method
	val address = Input(Vec(numChannels, UInt(addrWidth.W)))
	val data = Output(Vec(numChannels, UInt(dataWidth.W)))
  }

  val write_ = new Bundle {
	val ready = Input(Vec(numChannels, Bool()))
  	val valid = Output(Vec(numChannels, Bool()))
	val address = Output(Vec(numChannels, UInt(addrWidth.W)))
	val data = Output(Vec(numChannels, UInt(dataWidth.W)))
  }
}

class Dispatcher(
    numConsumers: Int,
    numChannels: Int,
    dataWidth: Int,
    addrWidth: Int
) extends Module {
  val io = IO(new Bundle {
    val consumers = new ConsumerIO(numConsumers, dataWidth, addrWidth)
    val channels =  new ChannelIO(numConsumers, dataWidth, addrWidth)
  })

  val channelState = RegInit(VecInit(Seq.fill(numChannels){ChannelStates.IDLE}))
  val currentConsumer = RegInit(VecInit(Seq.fill(numChannels)(0.U(log2Ceil(numConsumers).W))))
  val readAddressReg = RegInit(VecInit(Seq.fill(numChannels)(0.U(addrWidth.W))))
  val writeAddressReg = RegInit(VecInit(Seq.fill(numChannels)(0.U(addrWidth.W))))
  val writeDataReg = RegInit(VecInit(Seq.fill(numChannels)(0.U(dataWidth.W))))

  // Default values for consumer and channel interfaces
  for (i <- 0 until numConsumers) {
	io.consumers.read.valid()@@
	 io.consumers.read.valid := false.B
	 io.consumers.write.valid := false.B
	 io.consumers.read.bits.data := DontCare
	 io.consumers.write.bits.data := DontCare
	 io.consumers.write.bits.address := 0.U
  }
  io.consumers.read_.ready
  io.consumers.foreach { consumer =>
    consumer.read.ready := false.B
    consumer.read.bits.data := DontCare
    consumer.write.ready := false.B
  }

  io.channels.foreach { channel =>
    channel.read.valid := false.B
    channel.read.bits.address := 0.U
    channel.write.valid := false.B
    channel.write.bits.address := 0.U
    channel.write.bits.data := 0.U
  }

  var requestFound = Reg(Bool())
  for (i <- 0 until numChannels) {
    switch(channelState(i)) {
      is(ChannelStates.IDLE) {
        requestFound := false.B
        for (j <- 0 until numConsumers) {
          // Check for read request first
          when(!requestFound && io.consumers(j).read.valid) {
            requestFound := true.B
            currentConsumer(i) := j.U
            readAddressReg(i) := io.consumers(j).read.bits.address
            io.consumers(j).read.ready := true.B
            channelState(i) := ChannelStates.RELAY_READ
          // If no read request, check for write
          }.elsewhen(!requestFound && io.consumers(j).write.valid) {
            requestFound := true.B
            currentConsumer(i) := j.U
            writeAddressReg(i) := io.consumers(j).write.bits.address
            writeDataReg(i) := io.consumers(j).write.bits.data
            io.consumers(j).write.ready := true.B
            channelState(i) := ChannelStates.RELAY_WRITE
          }
        }
      }
      is(ChannelStates.RELAY_READ) {
        io.channels(i).read.valid := true.B
        io.channels(i).read.bits.address := readAddressReg(i)
        when(io.channels(i).read.ready) {
          channelState(i) := ChannelStates.READING
        }
      }
      is(ChannelStates.READING) {
        when(io.channels(i).read.valid) {
          io.consumers(currentConsumer(i)).read.bits.data := io.channels(i).read.bits.data
          channelState(i) := ChannelStates.IDLE
        }
      }
      is(ChannelStates.RELAY_WRITE) {
        io.channels(i).write.valid := true.B
        io.channels(i).write.bits.address := writeAddressReg(i)
        io.channels(i).write.bits.data := writeDataReg(i)
        when(io.channels(i).write.ready) {
          channelState(i) := ChannelStates.IDLE
        }
      }
    }
  }
}

object Main extends App {
  println(
    ChiselStage.emitSystemVerilog(
		gen = new Dispatcher(4, 2, 8, 32), // 4 cores, 2 channels, 8 bit data, 32 bit address
      firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info")
    )
  )
}
```



#### Error stacktrace:

```
java.base/java.lang.String.checkBoundsOffCount(String.java:4591)
	java.base/java.lang.String.rangeCheck(String.java:304)
	java.base/java.lang.String.<init>(String.java:300)
	scala.tools.nsc.interactive.Global.typeCompletions$1(Global.scala:1245)
	scala.tools.nsc.interactive.Global.completionsAt(Global.scala:1283)
	scala.meta.internal.pc.SignatureHelpProvider.$anonfun$treeSymbol$1(SignatureHelpProvider.scala:390)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.pc.SignatureHelpProvider.treeSymbol(SignatureHelpProvider.scala:388)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCall$.unapply(SignatureHelpProvider.scala:205)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.visit(SignatureHelpProvider.scala:316)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.traverse(SignatureHelpProvider.scala:310)
	scala.meta.internal.pc.SignatureHelpProvider$MethodCallTraverser.fromTree(SignatureHelpProvider.scala:279)
	scala.meta.internal.pc.SignatureHelpProvider.signatureHelp(SignatureHelpProvider.scala:27)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$signatureHelp$1(ScalaPresentationCompiler.scala:417)
```
#### Short summary: 

java.lang.StringIndexOutOfBoundsException: offset 3679, count -7, length 6359