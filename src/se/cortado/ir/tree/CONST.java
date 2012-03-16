package se.cortado.ir.tree;

public class CONST extends IR_Exp {
	
	public int value;
	
	public CONST(int v) {
		value = v;
	}
	
	public IR_ExpList kids() {
		return null;
	}
	
	public IR_Exp build(IR_ExpList kids) {
		return this;
	}
}

