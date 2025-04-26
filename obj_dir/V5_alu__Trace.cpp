// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Tracing implementation internals
#include "verilated_vcd_c.h"
#include "V5_alu__Syms.h"


void V5_alu::traceChgTop0(void* userp, VerilatedVcd* tracep) {
    V5_alu__Syms* __restrict vlSymsp = static_cast<V5_alu__Syms*>(userp);
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Variables
    if (VL_UNLIKELY(!vlSymsp->__Vm_activity)) return;
    // Body
    {
        vlTOPp->traceChgSub0(userp, tracep);
    }
}

void V5_alu::traceChgSub0(void* userp, VerilatedVcd* tracep) {
    V5_alu__Syms* __restrict vlSymsp = static_cast<V5_alu__Syms*>(userp);
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    vluint32_t* const oldp = tracep->oldp(vlSymsp->__Vm_baseCode + 1);
    if (false && oldp) {}  // Prevent unused
    // Body
    {
        tracep->chgBit(oldp+0,(vlTOPp->clock));
        tracep->chgBit(oldp+1,(vlTOPp->reset));
        tracep->chgCData(oldp+2,(vlTOPp->io_instr),3);
        tracep->chgCData(oldp+3,(vlTOPp->io_a),8);
        tracep->chgCData(oldp+4,(vlTOPp->io_b),8);
        tracep->chgCData(oldp+5,(vlTOPp->io_out),8);
        tracep->chgBit(oldp+6,(vlTOPp->io_err));
    }
}

void V5_alu::traceCleanup(void* userp, VerilatedVcd* /*unused*/) {
    V5_alu__Syms* __restrict vlSymsp = static_cast<V5_alu__Syms*>(userp);
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    {
        vlSymsp->__Vm_activity = false;
        vlTOPp->__Vm_traceActivity[0U] = 0U;
    }
}
