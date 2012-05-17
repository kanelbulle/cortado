package se.cortado.ir.tree;

public class CALL extends IR_Exp {

	public IR_Exp func;
	public IR_ExpList args;

	public CALL(IR_Exp f, IR_ExpList a) {
		func=f; args=a;
	}
	
	public IR_ExpList kids() {
		return new IR_ExpList(func,args);
	}
	
	public IR_Exp build(IR_ExpList kids) {
		return new CALL(kids.head,kids.tail);
	}

}

