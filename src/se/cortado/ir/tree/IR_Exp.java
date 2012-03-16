package se.cortado.ir.tree;

public abstract class IR_Exp {
	
	public abstract IR_ExpList kids();
	public abstract IR_Exp build(IR_ExpList kids);

}

