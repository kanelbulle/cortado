package se.cortado.ir.tree;

public class EXP extends IR_Stm {
	
	public IR_Exp exp;
	
	public EXP(IR_Exp e) {
		exp = e;
	}
	
//	public abstract ExpList kids();
	
//	public abstract Stm build(ExpList kids);
	
	public IR_ExpList kids() {
		return new IR_ExpList(exp, null);
	}
	
	public IR_Stm build(IR_ExpList kids) {
		return new EXP(kids.head);
	}
}


