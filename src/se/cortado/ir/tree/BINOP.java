package se.cortado.ir.tree;

public class BINOP extends IR_Exp {

	public int binop;
	public IR_Exp left, right;
	
	public BINOP(int b, IR_Exp l, IR_Exp r) {
		binop = b;
		left = l;
		right = r; 
	}
	
	public final static int 
		PLUS    =0, 
		MINUS   =1, 
		MUL     =2, 
		DIV     =3, 
		AND     =4,
		OR      =5,
		LSHIFT  =6,
		RSHIFT  =7,
		ARSHIFT =8,
		XOR     =9;

	public IR_ExpList kids() {
		return new IR_ExpList(left, new IR_ExpList(right, null));
	}

	public IR_Exp build(IR_ExpList kids) {
		return new BINOP(binop, kids.head, kids.tail.head);
	}
}



