file://<WORKSPACE>/3_elastic_sram.scala
### java.lang.OutOfMemoryError: Java heap space

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 746
uri: file://<WORKSPACE>/3_elastic_sram.scala
text:
```scala
//> using scala "2.13.12"
//> using dep "org.chipsalliance::chisel:6.7.0"
//> using dep "edu.berkeley.cs::chiseltest:6.0.0"  // ✅ Chisel test lib
//> using plugin "org.chipsalliance:::chisel-plugin:6.7.0"
//> using options "-unchecked","-deprecation","-language:reflectiveCalls","-feature","-Xcheckinit","-Xfatal-warnings","-Ywarn-dead-code","-Ywarn-unused","-Ymacro-annotations"

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

// _root_ disambiguates from package chisel3.util.circt if user imports chisel3.util._
import _root_.circt.stage.ChiselStage


class ElasticSRAM[T <: Data](
  numEntries: Int,
  gen: T) {
    val io = IO(new Bundle{
      val rreq = Flipped(Decoupled(numEntries.W))
      val rres = De@@
    })

  }

```



#### Error stacktrace:

```

```
#### Short summary: 

java.lang.OutOfMemoryError: Java heap space