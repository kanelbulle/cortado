package se.cortado.ir.tree;

public class IR_ExpList {
	
	public IR_Exp head;
	public IR_ExpList tail;
	
	public IR_ExpList(IR_Exp h, IR_ExpList t) {
		head = h; 
		tail = t;
	}
	
//	public IR_ExpList(IR_Exp h) {
//		head = h;
//		tail = null;
//	}
}


//package Tree;
//public class ExpList {
//  public Exp head;
//  public ExpList tail;
//  public ExpList(Exp h, ExpList t) {head=h; tail=t;}
//}




