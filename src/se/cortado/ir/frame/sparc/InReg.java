package se.cortado.ir.frame.SPARC;

import se.cortado.ir.temp.Temp;
import se.cortado.ir.tree.IR_Exp;
import se.cortado.ir.tree.TEMP;

public class InReg implements se.cortado.ir.frame.Access
{
	private Temp reg;

	public InReg(Temp r) {
		reg = r;
	}

	public String toString() {
		return "sparc.InReg(" + reg.toString() + ")";
	}

	public IR_Exp exp(IR_Exp basePointer) {
		return new TEMP(reg);
	}

}
