package se.cortado.ir.frame.x86_64;

import se.cortado.ir.tree.BINOP;
import se.cortado.ir.tree.CONST;
import se.cortado.ir.tree.IR_Exp;
import se.cortado.ir.tree.MEM;

class InFrame implements se.cortado.ir.frame.Access {
	private int offset;

	public InFrame(int offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return "x86_64.InFrame(" + offset + ")";
	}

	@Override
	public IR_Exp exp(IR_Exp basePointer) {
		if (offset == 0)
			return new MEM(basePointer);
		else
			return new MEM(
					new BINOP(BINOP.PLUS, basePointer, new CONST(offset)));
	}
}
