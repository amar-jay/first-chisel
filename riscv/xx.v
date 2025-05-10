[32mALUTest:[0m
[32mALU[0m
[32m- should perform addition correctly[0m
[32m- should detect addition overflow[0m
[32m- should perform subtraction correctly[0m
[32m- should detect subtraction overflow[0m
[32m- should perform multiplication correctly[0m
[32m- should perform division correctly[0m
[31m- should handle division by zero *** FAILED ***[0m
[31m  In step 1: io_zero=false (0, 0x0) did not equal expected=true (1, 0x1) at (alu.scala:189) (alu.scala:189)[0m
[31m- should detect division overflow for edge case *** FAILED ***[0m
[31m  chisel3.package$ChiselException: Error: Not in a UserModule. Likely cause: Missed Module() wrap, bare chisel API call, or attempting to construct hardware inside a BlackBox.[0m
[31m  at chisel3.internal.throwException$.apply(Error.scala:237)[0m
[31m  at chisel3.internal.Builder$.forcedUserModule(Builder.scala:770)[0m
[31m  at chisel3.internal.Builder$.pushOp(Builder.scala:866)[0m
[31m  at chisel3.Bits.unop(Bits.scala:268)[0m
[31m  at chisel3.UInt.do_unary_$tilde(Bits.scala:609)[0m
[31m  at riscv.ALUTest.$anonfun$new$72(alu.scala:199)[0m
[31m  at riscv.ALUTest.$anonfun$new$72$adapted(alu.scala:196)[0m
[31m  at chiseltest.internal.SimController.run(SimController.scala:124)[0m
[31m  at chiseltest.internal.Context$.$anonfun$runTest$2(Context.scala:30)[0m
[31m  at scala.util.DynamicVariable.withValue(DynamicVariable.scala:59)[0m
[31m  ...[0m
[32m- should perform AND operation correctly[0m
[32m- should perform OR operation correctly[0m
[32m- should perform XOR operation correctly[0m
[32m- should perform logical left shift correctly[0m
[32m- should perform logical right shift correctly[0m
[32m- should perform arithmetic right shift correctly[0m
[32m- should perform signed less than comparison correctly[0m
[32m- should perform unsigned less than comparison correctly[0m
[32m- should perform equality comparison correctly[0m
[32m- should perform inequality comparison correctly[0m
[32m- should copy first input correctly[0m
[32m- should copy second input correctly[0m
[32m- should set zero flag when result is zero[0m
[36mRun completed in 2 seconds, 266 milliseconds.[0m
[36mTotal number of tests run: 21[0m
[36mSuites: completed 1, aborted 0[0m
[36mTests: succeeded 19, failed 2, canceled 0, ignored 0, pending 0[0m
[31m*** 2 TESTS FAILED ***[0m
