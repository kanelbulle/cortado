package se.cortado.ir.frame.SPARC;

import se.cortado.ir.tree.*;

class InFrame implements se.cortado.ir.frame.Access
{
	private int offset;

	public InFrame(int o) {
		offset = o;
	}

	public String toString() {
		return "sparc.InFrame(" + offset + ")";
	}

	public IR_Exp exp(IR_Exp basePointer) {
		// A small optimization for the special case offset == 0.
		// (This case occurs frequently with heap objects.)
		if(offset == 0)
			return new MEM(basePointer);
		else
			return new MEM(new BINOP(BINOP.PLUS,
					basePointer,
					new CONST(offset)));
	}

}
