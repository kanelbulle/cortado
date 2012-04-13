package se.cortado.ir.translate;

import se.cortado.ir.temp.Label;

/** @author Samuel Wejeus */
public abstract class Translate {

	/** when the value is interesting */
	public abstract se.cortado.ir.tree.IR_Exp getValue();
	
	/** when only the effect is interesting */
	public abstract se.cortado.ir.tree.IR_Stm getNoValue();
	
	/** when a 0,1 is to be used in a if or a while */
	public abstract se.cortado.ir.tree.IR_Stm getConditional(Label t, Label f);
	
}
