package se.cortado.ir.translate;

import se.cortado.ir.temp.Label;
import se.cortado.ir.tree.CJUMP;
import se.cortado.ir.tree.IR_Stm;

/** @author Samuel Wejeus 
 * 
 * Used by other constructs that want to use a conditional expression
 * such as "a < 10" to be used in any type of "jump" expression such as if, while.
 * Where the expression is supposed to jump is handled by parent expressions
 * by build_CX() with labels passed by parent.
 * */
public class TR_RelCx extends TR_Cx {
	
    private Translate left;
    private Translate right;
    private int op;
    
    public TR_RelCx(int o, Translate l, Translate r) {
        op = o;
        left = l;
        right = r;
    }

    public IR_Stm getConditional(Label t, Label f) {
        return new CJUMP(op, left.getValue(), right.getValue(), t, f);
    }
}
