// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Design implementation internals
// See V5_alu.h for the primary calling header

#include "V5_alu.h"
#include "V5_alu__Syms.h"

//==========

VL_CTOR_IMP(V5_alu) {
    V5_alu__Syms* __restrict vlSymsp = __VlSymsp = new V5_alu__Syms(this, name());
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Reset internal values
    
    // Reset structure values
    _ctor_var_reset();
}

void V5_alu::__Vconfigure(V5_alu__Syms* vlSymsp, bool first) {
    if (false && first) {}  // Prevent unused
    this->__VlSymsp = vlSymsp;
    if (false && this->__VlSymsp) {}  // Prevent unused
    Verilated::timeunit(-12);
    Verilated::timeprecision(-12);
}

V5_alu::~V5_alu() {
    VL_DO_CLEAR(delete __VlSymsp, __VlSymsp = NULL);
}

void V5_alu::_eval_initial(V5_alu__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    V5_alu::_eval_initial\n"); );
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
}

void V5_alu::final() {
    VL_DEBUG_IF(VL_DBG_MSGF("+    V5_alu::final\n"); );
    // Variables
    V5_alu__Syms* __restrict vlSymsp = this->__VlSymsp;
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
}

void V5_alu::_eval_settle(V5_alu__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    V5_alu::_eval_settle\n"); );
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    vlTOPp->_combo__TOP__1(vlSymsp);
}

void V5_alu::_ctor_var_reset() {
    VL_DEBUG_IF(VL_DBG_MSGF("+    V5_alu::_ctor_var_reset\n"); );
    // Body
    clock = VL_RAND_RESET_I(1);
    reset = VL_RAND_RESET_I(1);
    io_instr = VL_RAND_RESET_I(3);
    io_a = VL_RAND_RESET_I(8);
    io_b = VL_RAND_RESET_I(8);
    io_out = VL_RAND_RESET_I(8);
    io_err = VL_RAND_RESET_I(1);
    ALU__DOT___GEN = VL_RAND_RESET_I(16);
    { int __Vi0=0; for (; __Vi0<1; ++__Vi0) {
            __Vm_traceActivity[__Vi0] = VL_RAND_RESET_I(1);
    }}
}
