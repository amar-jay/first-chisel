// Verilated -*- C++ -*-
// DESCRIPTION: Verilator output: Design implementation internals
// See V5_alu.h for the primary calling header

#include "V5_alu.h"
#include "V5_alu__Syms.h"

//==========

void V5_alu::eval_step() {
    VL_DEBUG_IF(VL_DBG_MSGF("+++++TOP Evaluate V5_alu::eval\n"); );
    V5_alu__Syms* __restrict vlSymsp = this->__VlSymsp;  // Setup global symbol table
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
#ifdef VL_DEBUG
    // Debug assertions
    _eval_debug_assertions();
#endif  // VL_DEBUG
    // Initialize
    if (VL_UNLIKELY(!vlSymsp->__Vm_didInit)) _eval_initial_loop(vlSymsp);
    // Evaluate till stable
    int __VclockLoop = 0;
    QData __Vchange = 1;
    do {
        VL_DEBUG_IF(VL_DBG_MSGF("+ Clock loop\n"););
        vlSymsp->__Vm_activity = true;
        _eval(vlSymsp);
        if (VL_UNLIKELY(++__VclockLoop > 100)) {
            // About to fail, so enable debug to see what's not settling.
            // Note you must run make with OPT=-DVL_DEBUG for debug prints.
            int __Vsaved_debug = Verilated::debug();
            Verilated::debug(1);
            __Vchange = _change_request(vlSymsp);
            Verilated::debug(__Vsaved_debug);
            VL_FATAL_MT("results/5_alu.sv", 2, "",
                "Verilated model didn't converge\n"
                "- See DIDNOTCONVERGE in the Verilator manual");
        } else {
            __Vchange = _change_request(vlSymsp);
        }
    } while (VL_UNLIKELY(__Vchange));
}

void V5_alu::_eval_initial_loop(V5_alu__Syms* __restrict vlSymsp) {
    vlSymsp->__Vm_didInit = true;
    _eval_initial(vlSymsp);
    vlSymsp->__Vm_activity = true;
    // Evaluate till stable
    int __VclockLoop = 0;
    QData __Vchange = 1;
    do {
        _eval_settle(vlSymsp);
        _eval(vlSymsp);
        if (VL_UNLIKELY(++__VclockLoop > 100)) {
            // About to fail, so enable debug to see what's not settling.
            // Note you must run make with OPT=-DVL_DEBUG for debug prints.
            int __Vsaved_debug = Verilated::debug();
            Verilated::debug(1);
            __Vchange = _change_request(vlSymsp);
            Verilated::debug(__Vsaved_debug);
            VL_FATAL_MT("results/5_alu.sv", 2, "",
                "Verilated model didn't DC converge\n"
                "- See DIDNOTCONVERGE in the Verilator manual");
        } else {
            __Vchange = _change_request(vlSymsp);
        }
    } while (VL_UNLIKELY(__Vchange));
}

VL_INLINE_OPT void V5_alu::_combo__TOP__1(V5_alu__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    V5_alu::_combo__TOP__1\n"); );
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Variables
    WData/*127:0*/ __Vtemp2[4];
    // Body
    vlTOPp->io_err = (1U & (~ (((((((~ (IData)((0U 
                                                != (IData)(vlTOPp->io_instr)))) 
                                    | (1U == (IData)(vlTOPp->io_instr))) 
                                   | (2U == (IData)(vlTOPp->io_instr))) 
                                  | (3U == (IData)(vlTOPp->io_instr))) 
                                 | (4U == (IData)(vlTOPp->io_instr))) 
                                | (5U == (IData)(vlTOPp->io_instr))) 
                               | (6U == (IData)(vlTOPp->io_instr)))));
    vlTOPp->ALU__DOT___GEN = (0xffU & ((1U == (IData)(vlTOPp->io_instr))
                                        ? ((IData)(vlTOPp->io_a) 
                                           - (IData)(vlTOPp->io_b))
                                        : ((0U != (IData)(vlTOPp->io_instr))
                                            ? 0U : 
                                           ((IData)(vlTOPp->io_a) 
                                            + (IData)(vlTOPp->io_b)))));
    __Vtemp2[0U] = (((IData)(vlTOPp->ALU__DOT___GEN) 
                     << 0x10U) | (IData)(vlTOPp->ALU__DOT___GEN));
    __Vtemp2[1U] = ((0xff0000U & (VL_DIV_III(8, (IData)(vlTOPp->io_a), (IData)(vlTOPp->io_b)) 
                                  << 0x10U)) | (0xffffU 
                                                & ((IData)(vlTOPp->io_a) 
                                                   * (IData)(vlTOPp->io_b))));
    __Vtemp2[2U] = (IData)((((QData)((IData)((((IData)(vlTOPp->ALU__DOT___GEN) 
                                               << 0x10U) 
                                              | ((IData)(vlTOPp->io_a) 
                                                 | (IData)(vlTOPp->io_b))))) 
                             << 0x20U) | (QData)((IData)(
                                                         ((((IData)(vlTOPp->io_a) 
                                                            & (IData)(vlTOPp->io_b)) 
                                                           << 0x10U) 
                                                          | ((IData)(vlTOPp->io_a) 
                                                             ^ (IData)(vlTOPp->io_b)))))));
    __Vtemp2[3U] = (IData)(((((QData)((IData)((((IData)(vlTOPp->ALU__DOT___GEN) 
                                                << 0x10U) 
                                               | ((IData)(vlTOPp->io_a) 
                                                  | (IData)(vlTOPp->io_b))))) 
                              << 0x20U) | (QData)((IData)(
                                                          ((((IData)(vlTOPp->io_a) 
                                                             & (IData)(vlTOPp->io_b)) 
                                                            << 0x10U) 
                                                           | ((IData)(vlTOPp->io_a) 
                                                              ^ (IData)(vlTOPp->io_b)))))) 
                            >> 0x20U));
    vlTOPp->io_out = (0xffU & (((0U == (0x1fU & ((IData)(vlTOPp->io_instr) 
                                                 << 4U)))
                                 ? 0U : (__Vtemp2[((IData)(1U) 
                                                   + 
                                                   (3U 
                                                    & ((IData)(vlTOPp->io_instr) 
                                                       >> 1U)))] 
                                         << ((IData)(0x20U) 
                                             - (0x1fU 
                                                & ((IData)(vlTOPp->io_instr) 
                                                   << 4U))))) 
                               | (__Vtemp2[(3U & ((IData)(vlTOPp->io_instr) 
                                                  >> 1U))] 
                                  >> (0x1fU & ((IData)(vlTOPp->io_instr) 
                                               << 4U)))));
}

void V5_alu::_eval(V5_alu__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    V5_alu::_eval\n"); );
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    vlTOPp->_combo__TOP__1(vlSymsp);
}

VL_INLINE_OPT QData V5_alu::_change_request(V5_alu__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    V5_alu::_change_request\n"); );
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    return (vlTOPp->_change_request_1(vlSymsp));
}

VL_INLINE_OPT QData V5_alu::_change_request_1(V5_alu__Syms* __restrict vlSymsp) {
    VL_DEBUG_IF(VL_DBG_MSGF("+    V5_alu::_change_request_1\n"); );
    V5_alu* const __restrict vlTOPp VL_ATTR_UNUSED = vlSymsp->TOPp;
    // Body
    // Change detection
    QData __req = false;  // Logically a bool
    return __req;
}

#ifdef VL_DEBUG
void V5_alu::_eval_debug_assertions() {
    VL_DEBUG_IF(VL_DBG_MSGF("+    V5_alu::_eval_debug_assertions\n"); );
    // Body
    if (VL_UNLIKELY((clock & 0xfeU))) {
        Verilated::overWidthError("clock");}
    if (VL_UNLIKELY((reset & 0xfeU))) {
        Verilated::overWidthError("reset");}
    if (VL_UNLIKELY((io_instr & 0xf8U))) {
        Verilated::overWidthError("io_instr");}
}
#endif  // VL_DEBUG
