package se.cortado.ir.tree;

public class IR_StmList {
	
	public IR_Stm head;
	public IR_StmList tail;
	
	public IR_StmList(IR_Stm h, IR_StmList t) {
		head = h;
		tail = t;
	}
}



