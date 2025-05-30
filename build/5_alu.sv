// Generated by CIRCT firtool-1.62.1
module ALU(
  input        clock,
               reset,
  input  [2:0] io_instr,
  input  [7:0] io_a,
               io_b,
  output [7:0] io_out,
  output       io_err
);

  wire [15:0]      _GEN =
    {8'h0, io_instr == 3'h1 ? io_a - io_b : (|io_instr) ? 8'h0 : io_a + io_b};
  wire [7:0][15:0] _GEN_0 =
    {{_GEN},
     {{8'h0, io_a | io_b}},
     {{8'h0, io_a & io_b}},
     {{8'h0, io_a ^ io_b}},
     {{8'h0, io_a / io_b}},
     {{8'h0, io_a} * {8'h0, io_b}},
     {_GEN},
     {_GEN}};
  assign io_out = _GEN_0[io_instr][7:0];
  assign io_err =
    ~(~(|io_instr) | io_instr == 3'h1 | io_instr == 3'h2 | io_instr == 3'h3
      | io_instr == 3'h4 | io_instr == 3'h5 | io_instr == 3'h6);
endmodule


