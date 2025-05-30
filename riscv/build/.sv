// Generated by CIRCT firtool-1.62.1
// Standard header to adapt well known macros for prints and assertions.

// Users can define 'ASSERT_VERBOSE_COND' to add an extra gate to assert error printing.
`ifndef ASSERT_VERBOSE_COND_
  `ifdef ASSERT_VERBOSE_COND
    `define ASSERT_VERBOSE_COND_ (`ASSERT_VERBOSE_COND)
  `else  // ASSERT_VERBOSE_COND
    `define ASSERT_VERBOSE_COND_ 1
  `endif // ASSERT_VERBOSE_COND
`endif // not def ASSERT_VERBOSE_COND_

// Users can define 'STOP_COND' to add an extra gate to stop conditions.
`ifndef STOP_COND_
  `ifdef STOP_COND
    `define STOP_COND_ (`STOP_COND)
  `else  // STOP_COND
    `define STOP_COND_ 1
  `endif // STOP_COND
`endif // not def STOP_COND_

module InstructionFetcher(
  input  [31:0] io_inst,
  output [4:0]  io_rd_addr,
                io_rs1_addr,
                io_rs2_addr,
  output [31:0] io_imm,
  output [5:0]  io__op,
  output [2:0]  io_inst_type
);

  wire [31:0]     io_imm_0;
  wire [7:0][5:0] _GEN = '{6'h2C, 6'h2B, 6'h2A, 6'h29, 6'h0, 6'h0, 6'h28, 6'h27};
  wire            _GEN_0 = io_inst[6:0] == 7'h33;
  wire            _GEN_1 = io_inst[14:12] == 3'h0;
  wire            _GEN_2 = io_inst[14:12] == 3'h1;
  wire            _GEN_3 = io_inst[14:12] == 3'h2;
  wire [7:0][5:0] _GEN_4 =
    {{6'h5},
     {6'h4},
     {io_inst[30] ? 6'h8 : 6'h7},
     {6'h3},
     {6'hA},
     {6'h9},
     {6'h6},
     {io_inst[30] ? 6'h2 : 6'h1}};
  wire            _GEN_5 = io_inst[6:0] == 7'h13;
  wire [7:0][5:0] _GEN_6 =
    {{6'h16},
     {6'h15},
     {{5'hC, io_inst[30]}},
     {6'h14},
     {6'h1B},
     {6'h1A},
     {6'h17},
     {6'h13}};
  wire            _GEN_7 = io_inst[6:0] == 7'h3;
  wire            _GEN_8 = io_inst[6:0] == 7'h67;
  wire            _GEN_9 = io_inst[6:0] == 7'h73;
  wire            _GEN_10 = io_inst[6:0] == 7'h23;
  wire            _GEN_11 = io_inst[6:0] == 7'h63;
  wire            _GEN_12 = io_inst[6:0] == 7'h37;
  wire            _GEN_13 = io_inst[6:0] == 7'h17;
  wire            _GEN_14 = io_inst[6:0] == 7'h6F;
  wire            _GEN_15 = _GEN_12 | _GEN_13;
  wire            _GEN_16 = _GEN_5 | _GEN_7 | _GEN_8 | _GEN_9;
  assign io_imm_0 =
    _GEN_0
      ? 32'h0
      : _GEN_16
          ? {{20{io_inst[31]}}, io_inst[31:20]}
          : _GEN_10
              ? {{20{io_inst[31]}}, io_inst[31:25], io_inst[11:7]}
              : _GEN_11
                  ? {{20{io_inst[31]}}, io_inst[7], io_inst[30:25], io_inst[11:8], 1'h0}
                  : _GEN_15
                      ? {io_inst[31:12], 12'h0}
                      : _GEN_14
                          ? {{12{io_inst[31]}},
                             io_inst[19:12],
                             io_inst[20],
                             io_inst[30:21],
                             1'h0}
                          : 32'h0;
  assign io_rd_addr = io_inst[11:7];
  assign io_rs1_addr = io_inst[19:15];
  assign io_rs2_addr = io_inst[24:20];
  assign io_imm = io_imm_0;
  assign io__op =
    _GEN_0
      ? _GEN_4[io_inst[14:12]]
      : _GEN_5
          ? _GEN_6[io_inst[14:12]]
          : _GEN_7
              ? (_GEN_1
                   ? 6'h1C
                   : _GEN_2
                       ? 6'h1D
                       : _GEN_3
                           ? 6'h1E
                           : io_inst[14:12] == 3'h4
                               ? 6'h1F
                               : {io_inst[14:12] == 3'h5, 5'h0})
              : _GEN_8
                  ? (_GEN_1 ? 6'h23 : 6'h0)
                  : _GEN_9
                      ? (_GEN_1
                           ? (io_imm_0 == 32'h0
                                ? 6'h21
                                : io_imm_0 == 32'h1 ? 6'h22 : 6'h0)
                           : 6'h0)
                      : _GEN_10
                          ? (_GEN_1 ? 6'h24 : _GEN_2 ? 6'h25 : _GEN_3 ? 6'h26 : 6'h0)
                          : _GEN_11
                              ? _GEN[io_inst[14:12]]
                              : _GEN_12
                                  ? 6'h2E
                                  : _GEN_13 ? 6'h2F : _GEN_14 ? 6'h2D : 6'h0;
  assign io_inst_type =
    _GEN_0
      ? 3'h1
      : _GEN_16
          ? 3'h2
          : _GEN_10 ? 3'h3 : _GEN_11 ? 3'h4 : _GEN_15 ? 3'h5 : _GEN_14 ? 3'h6 : 3'h0;
endmodule

module RegisterFile(
  input         clock,
                reset,
                io_read0_en,
  input  [4:0]  io_read0_addr,
  output [31:0] io_read0_data,
  input         io_read1_en,
  input  [4:0]  io_read1_addr,
  output [31:0] io_read1_data,
  input         io_write_en,
  input  [4:0]  io_write_addr,
  input  [31:0] io_write_data
);

  `ifndef SYNTHESIS
    always @(posedge clock) begin
      if (~reset & ~(~io_write_en | (|io_write_addr) | io_write_data == 32'h0)) begin
        if (`ASSERT_VERBOSE_COND_)
          $error("Assertion failed: Cannot write non-zero value to register x0\n    at register.scala:134 assert(!io.write.en || io.write.addr =/= 0.U || io.write.data === 0.S,\n");
        if (`STOP_COND_)
          $fatal;
      end
    end // always @(posedge)
  `endif // not def SYNTHESIS
  assign io_read0_data =
    ~io_read0_en | io_read0_addr == 5'h0 | ~(io_write_en & io_read0_addr == io_write_addr)
      ? 32'h0
      : io_write_data;
  assign io_read1_data =
    ~io_read1_en | io_read1_addr == 5'h0 | ~(io_write_en & io_read1_addr == io_write_addr)
      ? 32'h0
      : io_write_data;
endmodule

module RISCVCore(
  input         clock,
                reset,
  input  [31:0] io_inst,
  output [31:0] io_rddata
);

  wire        _GEN;
  wire [31:0] _regfile_io_read0_data;
  wire [31:0] _regfile_io_read1_data;
  wire [4:0]  _inst_fetcher_io_rd_addr;
  wire [4:0]  _inst_fetcher_io_rs1_addr;
  wire [4:0]  _inst_fetcher_io_rs2_addr;
  wire [31:0] _inst_fetcher_io_imm;
  wire [5:0]  _inst_fetcher_io__op;
  wire [2:0]  _inst_fetcher_io_inst_type;
  reg  [31:0] pc;
  wire [31:0] _regfile_io_write_data_T_23 = pc + 32'h4;
  wire        _GEN_0 = _inst_fetcher_io_inst_type == 3'h1;
  wire        _GEN_1 = _inst_fetcher_io_inst_type == 3'h3;
  wire        _GEN_2 = _inst_fetcher_io_inst_type == 3'h4;
  wire        _GEN_3 = _GEN_0 | _GEN_1 | _GEN_2;
  wire        _GEN_4 = _inst_fetcher_io_inst_type == 3'h2;
  wire        _GEN_5 = _GEN_4 | _GEN_3;
  wire        _GEN_6 = _inst_fetcher_io__op == 6'h23;
  wire        _GEN_7 = _inst_fetcher_io_inst_type == 3'h5;
  wire        _GEN_8 = _inst_fetcher_io_inst_type == 3'h6;
  wire        _GEN_9 = _inst_fetcher_io__op == 6'h2D;
  wire        _GEN_10 = _GEN_8 & _GEN_9;
  wire        _GEN_11 = _GEN_1 | _GEN_2;
  wire        _GEN_12 = ~_GEN_11 & (_GEN_7 | _GEN_8 & _GEN_9);
  assign _GEN = _GEN_0 | (_GEN_4 ? ~_GEN_6 : _GEN_12);
  always @(posedge clock) begin
    if (reset)
      pc <= 32'h0;
    else if (_GEN_0 | _GEN_4 | _GEN_1)
      pc <= _regfile_io_write_data_T_23;
    else if (_GEN_2) begin
      automatic logic [31:0] rs1_data = _GEN_5 ? _regfile_io_read0_data : 32'h0;
      automatic logic [31:0] rs2_data =
        _GEN_4 ? _inst_fetcher_io_imm : _GEN_3 ? _regfile_io_read1_data : 32'h0;
      if (_inst_fetcher_io__op == 6'h27
            ? rs1_data == rs2_data
            : _inst_fetcher_io__op == 6'h28
                ? rs1_data != rs2_data
                : _inst_fetcher_io__op == 6'h29
                    ? rs1_data < rs2_data
                    : _inst_fetcher_io__op == 6'h2A
                        ? rs1_data >= rs2_data
                        : _inst_fetcher_io__op != 6'h2B & _inst_fetcher_io__op == 6'h2C)
        pc <= pc + _inst_fetcher_io_imm;
      else
        pc <= _regfile_io_write_data_T_23;
    end
    else if (_GEN_7 | ~_GEN_10)
      pc <= _regfile_io_write_data_T_23;
    else
      pc <= pc + _inst_fetcher_io_imm;
  end // always @(posedge)
  InstructionFetcher inst_fetcher (
    .io_inst      (io_inst),
    .io_rd_addr   (_inst_fetcher_io_rd_addr),
    .io_rs1_addr  (_inst_fetcher_io_rs1_addr),
    .io_rs2_addr  (_inst_fetcher_io_rs2_addr),
    .io_imm       (_inst_fetcher_io_imm),
    .io__op       (_inst_fetcher_io__op),
    .io_inst_type (_inst_fetcher_io_inst_type)
  );
  RegisterFile regfile (
    .clock         (clock),
    .reset         (reset),
    .io_read0_en   (_GEN_5),
    .io_read0_addr (_GEN_4 | _GEN_3 ? _inst_fetcher_io_rs1_addr : 5'h0),
    .io_read0_data (_regfile_io_read0_data),
    .io_read1_en   (_GEN_3),
    .io_read1_addr (_GEN_3 ? _inst_fetcher_io_rs2_addr : 5'h0),
    .io_read1_data (_regfile_io_read1_data),
    .io_write_en   (_GEN),
    .io_write_addr
      (_GEN_0 | (_GEN_4 ? ~_GEN_6 | _GEN : _GEN_12 | _GEN)
         ? _inst_fetcher_io_rd_addr
         : 5'h0),
    .io_write_data
      (_GEN_0 | _GEN_4 | _GEN_11
         ? 32'h0
         : _GEN_7
             ? (_inst_fetcher_io__op == 6'h2E
                  ? _inst_fetcher_io_imm
                  : _inst_fetcher_io__op == 6'h2F ? pc + _inst_fetcher_io_imm : 32'h0)
             : _GEN_10 ? _regfile_io_write_data_T_23 : 32'h0)
  );
  assign io_rddata = 32'h0;
endmodule


