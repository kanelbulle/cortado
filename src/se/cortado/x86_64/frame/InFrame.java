package se.cortado.x86_64.frame;

import se.cortado.ir.tree.BINOP;
import se.cortado.ir.tree.CONST;
import se.cortado.ir.tree.IR_Exp;
import se.cortado.ir.tree.MEM;

class InFrame implements se.cortado.frame.Access {
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
		if (basePointer == null) {
			throw new Error("basePointer == null in InFrame");
		}
		
		if (offset == 0)
			return new MEM(basePointer);
		else
			return new MEM(
					new BINOP(BINOP.MINUS, basePointer, new CONST(offset)));
	}
}
