FIRRTL version 1.2.0
circuit ElasticSRAM :
  module ElasticSRAM : @[home/manan/code/riscv/chisel/3_elastic_sram.scala 21:7]
    input clock : Clock @[home/manan/code/riscv/chisel/3_elastic_sram.scala 21:7]
    input reset : UInt<1> @[home/manan/code/riscv/chisel/3_elastic_sram.scala 21:7]
    output io_rreq_ready : UInt<1> @[home/manan/code/riscv/chisel/3_elastic_sram.scala 24:14]
    input io_rreq_valid : UInt<1> @[home/manan/code/riscv/chisel/3_elastic_sram.scala 24:14]
    input io_rreq_bits : UInt<3> @[home/manan/code/riscv/chisel/3_elastic_sram.scala 24:14]
    input io_rres_ready : UInt<1> @[home/manan/code/riscv/chisel/3_elastic_sram.scala 24:14]
    output io_rres_valid : UInt<1> @[home/manan/code/riscv/chisel/3_elastic_sram.scala 24:14]
    output io_rres_bits : UInt<32> @[home/manan/code/riscv/chisel/3_elastic_sram.scala 24:14]
    input io_write_valid : UInt<1> @[home/manan/code/riscv/chisel/3_elastic_sram.scala 24:14]
    input io_write_bits_data : UInt<32> @[home/manan/code/riscv/chisel/3_elastic_sram.scala 24:14]
    input io_write_bits_idx : UInt<3> @[home/manan/code/riscv/chisel/3_elastic_sram.scala 24:14]
    input io_flush : UInt<1> @[home/manan/code/riscv/chisel/3_elastic_sram.scala 24:14]

    mem mem : @[home/manan/code/riscv/chisel/3_elastic_sram.scala 39:32]
      data-type => UInt<32>
      depth => 8
      read-latency => 1
      write-latency => 1
      reader => io_rres_bits_MPORT
      writer => MPORT
      read-under-write => undefined
    node _GEN_0 = validif(io_write_valid, io_write_bits_idx) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 41:24 43:14]
    node _GEN_1 = validif(io_write_valid, clock) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 41:24 43:14]
    node _GEN_2 = mux(io_write_valid, UInt<1>("h1"), UInt<1>("h0")) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 41:24 43:14 39:32]
    node _GEN_3 = validif(io_write_valid, UInt<1>("h1")) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 41:24 43:14]
    node _GEN_4 = validif(io_write_valid, io_write_bits_data) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 41:24 43:14]
    node s1_replay = eq(io_rres_ready, UInt<1>("h0")) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 49:19]
    reg last_val : UInt<1>, clock with :
      reset => (UInt<1>("h0"), last_val) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 52:25]
    reg last_idx : UInt<3>, clock with :
      reset => (UInt<1>("h0"), last_idx) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 53:25]
    node _GEN_5 = mux(s1_replay, last_val, io_rreq_valid) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 57:25 58:14 61:14]
    node _GEN_6 = mux(s1_replay, last_idx, io_rreq_bits) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 57:25 59:14 62:14]
    node _GEN_7 = mux(io_flush, UInt<1>("h0"), _GEN_5) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 55:18 56:14]
    node _GEN_8 = mux(io_flush, last_idx, _GEN_6) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 55:18 53:25]
    node _s0_valid_T = mux(s1_replay, last_val, io_rreq_valid) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 65:18]
    node _s0_ridx_T = mux(s1_replay, last_idx, io_rreq_bits) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 66:17]
    reg s1_valid : UInt<1>, clock with :
      reset => (UInt<1>("h0"), s1_valid) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 68:25]
    node s0_valid = _s0_valid_T @[home/manan/code/riscv/chisel/3_elastic_sram.scala 50:22 65:12]
    node s0_ridx = _s0_ridx_T @[home/manan/code/riscv/chisel/3_elastic_sram.scala 51:21 66:11]
    node _GEN_9 = validif(s0_valid, s0_ridx) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 70:{27,27}]
    node _GEN_10 = mux(s0_valid, UInt<1>("h1"), UInt<1>("h0")) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 70:{27,27} 39:32]
    node _io_rres_bits_WIRE = _GEN_9 @[home/manan/code/riscv/chisel/3_elastic_sram.scala 70:27]
    node _GEN_11 = validif(s0_valid, _io_rres_bits_WIRE) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 70:{27,27}]
    node _GEN_12 = validif(s0_valid, clock) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 70:{27,27}]
    node _io_rreq_ready_T = eq(s1_replay, UInt<1>("h0")) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 73:20]
    io_rreq_ready <= _io_rreq_ready_T @[home/manan/code/riscv/chisel/3_elastic_sram.scala 73:17]
    io_rres_valid <= s1_valid @[home/manan/code/riscv/chisel/3_elastic_sram.scala 69:17]
    io_rres_bits <= mem.io_rres_bits_MPORT.data @[home/manan/code/riscv/chisel/3_elastic_sram.scala 70:16]
    mem.io_rres_bits_MPORT.addr <= _GEN_11
    mem.io_rres_bits_MPORT.en <= _GEN_10
    mem.io_rres_bits_MPORT.clk <= _GEN_12
    mem.MPORT.addr <= _GEN_0
    mem.MPORT.en <= _GEN_2
    mem.MPORT.clk <= _GEN_1
    mem.MPORT.data <= _GEN_4
    mem.MPORT.mask <= _GEN_3
    last_val <= mux(reset, UInt<1>("h0"), _GEN_7) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 52:{25,25}]
    last_idx <= mux(reset, UInt<3>("h0"), _GEN_8) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 53:{25,25}]
    s1_valid <= mux(reset, UInt<1>("h0"), s0_valid) @[home/manan/code/riscv/chisel/3_elastic_sram.scala 68:{25,25,25}]
