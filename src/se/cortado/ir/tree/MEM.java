package se.cortado.ir.tree;

public class MEM extends IR_Exp {

	public IR_Exp exp;

	public MEM(IR_Exp e) {
		exp = e;
	}

	public IR_ExpList kids() {
		return new IR_ExpList(exp, null);
	}

	public IR_Exp build(IR_ExpList kids) {
		return new MEM(kids.head);
	}
}

