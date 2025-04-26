FIRRTL version 1.2.0
circuit FirFilter :
  module FirFilter : @[home/manan/code/riscv/chisel/1_firfilter.scala 15:7]
    input clock : Clock @[home/manan/code/riscv/chisel/1_firfilter.scala 15:7]
    input reset : UInt<1> @[home/manan/code/riscv/chisel/1_firfilter.scala 15:7]
    input io_in : UInt<8> @[home/manan/code/riscv/chisel/1_firfilter.scala 16:14]
    output io_out : UInt<8> @[home/manan/code/riscv/chisel/1_firfilter.scala 16:14]

    reg zs_0 : UInt<8>, clock with :
      reset => (UInt<1>("h0"), zs_0) @[home/manan/code/riscv/chisel/1_firfilter.scala 22:15]
    reg zs_1 : UInt<8>, clock with :
      reset => (UInt<1>("h0"), zs_1) @[home/manan/code/riscv/chisel/1_firfilter.scala 22:15]
    reg zs_2 : UInt<8>, clock with :
      reset => (UInt<1>("h0"), zs_2) @[home/manan/code/riscv/chisel/1_firfilter.scala 22:15]
    node _products_T = mul(zs_0, UInt<1>("h1")) @[home/manan/code/riscv/chisel/1_firfilter.scala 28:58]
    node _products_T_1 = mul(zs_1, UInt<1>("h1")) @[home/manan/code/riscv/chisel/1_firfilter.scala 28:58]
    node _products_T_2 = mul(zs_2, UInt<1>("h1")) @[home/manan/code/riscv/chisel/1_firfilter.scala 28:58]
    node products_0 = _products_T @[home/manan/code/riscv/chisel/1_firfilter.scala 28:{46,46}]
    node products_1 = _products_T_1 @[home/manan/code/riscv/chisel/1_firfilter.scala 28:{46,46}]
    node _io_out_T = add(products_0, products_1) @[home/manan/code/riscv/chisel/1_firfilter.scala 30:31]
    node products_2 = _products_T_2 @[home/manan/code/riscv/chisel/1_firfilter.scala 28:{46,46}]
    node _io_out_T_1 = add(_io_out_T, products_2) @[home/manan/code/riscv/chisel/1_firfilter.scala 30:31]
    io_out <= bits(_io_out_T_1, 7, 0) @[home/manan/code/riscv/chisel/1_firfilter.scala 30:10]
    zs_0 <= io_in @[home/manan/code/riscv/chisel/1_firfilter.scala 23:9]
    zs_1 <= zs_0 @[home/manan/code/riscv/chisel/1_firfilter.scala 25:11]
    zs_2 <= zs_1 @[home/manan/code/riscv/chisel/1_firfilter.scala 25:11]
