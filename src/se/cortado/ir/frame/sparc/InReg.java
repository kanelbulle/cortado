package se.cortado.ir.frame.sparc;

import se.cortado.ir.temp.*;
import se.cortado.ir.tree.*;

public class InReg implements se.cortado.ir.frame.Access
{
	private Temp reg;

	public InReg(Temp r) {
		reg = r;
	}

	public String toString() {
		return "sparc.InReg(" + reg.toString() + ")";
	}

	public Exp exp(Exp basePointer) {
		return new TEMP(reg);
	}

}
