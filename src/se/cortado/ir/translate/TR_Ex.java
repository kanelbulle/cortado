package se.cortado.ir.translate;

import se.cortado.ir.temp.Label;
import se.cortado.ir.tree.*;

/** @author Samuel Wejeus */
public class TR_Ex extends TR_Exp {
    
	private IR_Exp exp;
    
    TR_Ex(IR_Exp e) {
        exp = e;
    }

    public IR_Exp build_EX() {    
        return exp;
    }

    public IR_Stm build_NX() {
        return new EXP(exp);
    }

    public IR_Stm build_CX(Label t, Label f) {
        return new CJUMP(CJUMP.EQ, exp, new CONST(0), f, t);
    }

}
