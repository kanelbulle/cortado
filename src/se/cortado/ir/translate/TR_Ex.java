package se.cortado.ir.translate;

import se.cortado.ir.temp.Label;
import se.cortado.ir.tree.*;

/** @author Samuel Wejeus */
public class TR_Ex extends Translate {
    
	private IR_Exp exp;
    
    public TR_Ex(IR_Exp e) {
    	if (e == null) {
    		throw new Error("Initializing TR_Ex with e = null");
    	}
    	
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
    	if (t == null || f == null) {
    		throw new Error("t or f is null in getConditional");
    	}
    	
        return new CJUMP(CJUMP.EQ, exp, new CONST(0), f, t);
    }

}
