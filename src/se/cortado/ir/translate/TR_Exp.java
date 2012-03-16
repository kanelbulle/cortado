package se.cortado.ir.translate;

import se.cortado.ir.temp.Label;

/** @author Samuel Wejeus */
public abstract class TR_Exp {

	/** when the value is interesting */
	public abstract se.cortado.ir.tree.IR_Exp build_EX();
	
	/** when only the effect is interesting */
	public abstract se.cortado.ir.tree.IR_Stm build_NX();
	
	/** when a 0,1 is to be used in a if or a while */
	public abstract se.cortado.ir.tree.IR_Stm build_CX(Label t, Label f);
	
}
