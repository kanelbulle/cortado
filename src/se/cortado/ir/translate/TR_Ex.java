package se.cortado.ir.translate;

import se.cortado.ir.temp.Label;
import se.cortado.ir.tree.*;

/** @author Samuel Wejeus */
public class TR_Ex extends Translate {
    
	private IR_Exp exp;
    
    public TR_Ex(IR_Exp e) {
        exp = e;
    }

    public IR_Exp getValue() {    
        return exp;
    }

    public IR_Stm getNoValue() {
        return new EXP(exp);
    }
    
    /** TODO (From Appel): 
     * "The unCx method of class Ex is left as an exercise. It’s helpful 
     * to have unCx treat the cases of CONST 0 and CONST 1 specially, 
     * since they have particularly simple and efficient translations." 
     */
    public IR_Stm getConditional(Label t, Label f) {
        return new CJUMP(CJUMP.EQ, exp, new CONST(0), f, t);
    }

}
