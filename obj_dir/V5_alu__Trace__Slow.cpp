// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Tracing implementation internals
#include "verilated_vcd_c.h"
#include "V5_alu__Syms.h"


//======================

void V5_alu::trace(VerilatedVcdC* tfp, int, int) {
    tfp->spTrace()->addInitCb(&traceInit, __VlSymsp);
    traceRegister(tfp->spTrace());
}

void V5_alu::traceInit(void* userp, VerilatedVcd* tracep, uint32_t code) {
    // Callback from tracep->open()
    V5_alu__Syms* __restrict vlSymsp = static_cast<V5_alu__Syms*>(userp);
    if (!Verilated::calcUnusedSigs()) {
        VL_FATAL_MT(__FILE__, __LINE__, __FILE__,
                        "Turning on wave traces requires Verilated::traceEverOn(true) call before time 0.");
    }
    vlSymsp->__Vm_baseCode = code;
    tracep->module(vlSymsp->name());
    tracep->scopeEscape(' ');
    V5_alu::traceInitTop(vlSymsp, tracep);
    tracep->scopeEscape('.');
}

//======================


void V5_alu::traceInitTop(void* userp, VerilatedVcd* tracep) {
    V5_alu__Syms* __restrict vlSymsp = static_cast<V5_alu__Syms*>(userp);
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    {
        vlTOPp->traceInitSub0(userp, tracep);
    }
}

void V5_alu::traceInitSub0(void* userp, VerilatedVcd* tracep) {
    V5_alu__Syms* __restrict vlSymsp = static_cast<V5_alu__Syms*>(userp);
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    const int c = vlSymsp->__Vm_baseCode;
    if (false && tracep && c) {}  // Prevent unused
    // Body
    {
        tracep->declBit(c+1,"clock", false,-1);
        tracep->declBit(c+2,"reset", false,-1);
        tracep->declBus(c+3,"io_instr", false,-1, 2,0);
        tracep->declBus(c+4,"io_a", false,-1, 7,0);
        tracep->declBus(c+5,"io_b", false,-1, 7,0);
        tracep->declBus(c+6,"io_out", false,-1, 7,0);
        tracep->declBit(c+7,"io_err", false,-1);
        tracep->declBit(c+1,"ALU clock", false,-1);
        tracep->declBit(c+2,"ALU reset", false,-1);
        tracep->declBus(c+3,"ALU io_instr", false,-1, 2,0);
        tracep->declBus(c+4,"ALU io_a", false,-1, 7,0);
        tracep->declBus(c+5,"ALU io_b", false,-1, 7,0);
        tracep->declBus(c+6,"ALU io_out", false,-1, 7,0);
        tracep->declBit(c+7,"ALU io_err", false,-1);
    }
}

void V5_alu::traceRegister(VerilatedVcd* tracep) {
    // Body
    {
        tracep->addFullCb(&traceFullTop0, __VlSymsp);
        tracep->addChgCb(&traceChgTop0, __VlSymsp);
        tracep->addCleanupCb(&traceCleanup, __VlSymsp);
    }
}

void V5_alu::traceFullTop0(void* userp, VerilatedVcd* tracep) {
    V5_alu__Syms* __restrict vlSymsp = static_cast<V5_alu__Syms*>(userp);
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    {
        vlTOPp->traceFullSub0(userp, tracep);
    }
}

void V5_alu::traceFullSub0(void* userp, VerilatedVcd* tracep) {
    V5_alu__Syms* __restrict vlSymsp = static_cast<V5_alu__Syms*>(userp);
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    vluint32_t* const oldp = tracep->oldp(vlSymsp->__Vm_baseCode);
    if (false && oldp) {}  // Prevent unused
    // Body
    {
        tracep->fullBit(oldp+1,(vlTOPp->clock));
        tracep->fullBit(oldp+2,(vlTOPp->reset));
        tracep->fullCData(oldp+3,(vlTOPp->io_instr),3);
        tracep->fullCData(oldp+4,(vlTOPp->io_a),8);
        tracep->fullCData(oldp+5,(vlTOPp->io_b),8);
        tracep->fullCData(oldp+6,(vlTOPp->io_out),8);
        tracep->fullBit(oldp+7,(vlTOPp->io_err));
    }
}
