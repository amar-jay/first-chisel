// Generated by CIRCT firtool-1.62.1
module FirFilter(
  input        clock,
               reset,
  input  [7:0] io_in,
  output [7:0] io_out
);

  reg [7:0] z_0;
  reg [7:0] z_1;
  reg [7:0] z_2;
  always @(posedge clock) begin
    z_0 <= io_in;
    z_1 <= z_0;
    z_2 <= z_1;
  end // always @(posedge)
  assign io_out = z_0 + z_1 + z_2;
endmodule


