package se.cortado.ir.tree;

public class MOVE extends IR_Stm {

	public IR_Exp dst, src;
	
	public MOVE(IR_Exp d, IR_Exp s) {
		dst = d;
		src = s;
	}
	
	public IR_ExpList kids() {
		if (dst instanceof MEM) {
			return new IR_ExpList(((MEM)dst).exp, new IR_ExpList(src, null));
		} else {
			return new IR_ExpList(src, null);
		}
	}
	
	public IR_Stm build(IR_ExpList kids) {
		if (dst instanceof MEM) {
			return new MOVE(new MEM(kids.head), kids.tail.head);
		} else {
			return new MOVE(dst, kids.head);
		}
	}
}

