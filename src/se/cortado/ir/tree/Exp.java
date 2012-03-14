package se.cortado.ir.tree;

public abstract class Exp extends Stm {
	public Exp exp;
	
//	public Exp(Exp e) {
//		exp = e;
//	}
	
	public abstract ExpList kids();
	
	public abstract Stm build(ExpList kids);
	
//	public ExpList kids() {
//		return new ExpList(exp);
//	}
//	
//	public Stm build(ExpList kids) {
//		return new ExpList(kids.head);
//	}
}

