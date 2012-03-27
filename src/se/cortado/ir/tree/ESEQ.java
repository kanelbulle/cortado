package se.cortado.ir.tree;

public class ESEQ extends IR_Exp {
	public IR_Stm stm;
	public IR_Exp exp;
	
	public ESEQ(IR_Stm s, IR_Exp e) {
		stm = s;
		exp = e;
	}
	
	public IR_ExpList kids() {
		throw new Error("kids() not applicable to ESEQ");
	}
	
	public IR_Exp build(IR_ExpList kids) {
		throw new Error("build() not applicable to ESEQ");
	}
}

