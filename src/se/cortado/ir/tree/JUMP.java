package se.cortado.ir.tree;

import se.cortado.ir.temp.*;

public class JUMP extends IR_Stm {

	public IR_Exp exp;
	public LabelList targets;

	public JUMP(IR_Exp e, LabelList t) {
		exp = e;
		targets = t;
	}

	public JUMP(Label target) {
		this(new NAME(target), new LabelList(target));
	}

	public IR_ExpList kids() {
		return new IR_ExpList(exp, null);
	}

	public IR_Stm build(IR_ExpList kids) {
		return new JUMP(kids.head, targets);
	}
}

