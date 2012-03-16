package se.cortado.ir.translate;

import se.cortado.ir.temp.*;
import se.cortado.ir.tree.*;

/** @author Samuel Wejeus */
public abstract class TR_Cx extends TR_Exp {
	
	/** FROM APPEL:
	 * To convert a “conditional” into a “value expression,” we invent a 
	 * new temporary r and new labels t and f . Then we make a Tree.Stm 
	 * that moves the value 1 into r , and a conditional jump unCx(t , f ) 
	 * that implements the conditional. If the condition is false, 
	 * then 0 is moved into r; if it is true, then execution proceeds at 
	 * t and the second move is skipped. The result of the whole thing 
	 * is just the temporary r containing zero or one.
	 */
	public IR_Exp build_EX() {
		Label t = new Label();
		Label f = new Label();
		Temp r = new Temp();

		MOVE move1 = new MOVE( new TEMP(r),new CONST(1) );
		MOVE move0 = new MOVE( new TEMP(r),new CONST(0) );
		SEQ tail = new SEQ( move0, new LABEL(t) );
		SEQ mid = new SEQ(new LABEL(f), tail);
		SEQ before = new SEQ(build_CX(t,f), mid);
		SEQ retval = new SEQ(move1, before);

		return new ESEQ(retval, new TEMP(r));
	}

	public IR_Stm build_NX() {
		Label t = new Label();
		Label f = new Label();
		/*---------------------------------------
        Temp r = new Temp();

        MOVE move1 = new MOVE( new TEMP(r),new CONST(1) );
        MOVE move0 = new MOVE( new TEMP(r),new CONST(0) );
        SEQ tail = new SEQ( move0, new LABEL(t) );
        SEQ mid = new SEQ(new LABEL(f), tail);
        SEQ before = new SEQ(unCx(t,f), mid);
        SEQ retval = new SEQ(move1, before);
        ---------------------------------------*/

		return build_CX(t,f);
	}

	public abstract IR_Stm build_CX(Label t, Label f);
}
