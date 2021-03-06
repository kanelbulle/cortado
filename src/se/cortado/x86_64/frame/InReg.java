package se.cortado.x86_64.frame;

import se.cortado.ir.temp.Temp;
import se.cortado.ir.tree.IR_Exp;
import se.cortado.ir.tree.TEMP;

public class InReg implements se.cortado.frame.Access {
	private Temp register;

	public InReg(Temp register) {
		this.register = register;
	}

	@Override
	public String toString() {
		return "x86_64.InReg(" + register + ")";
	}

	@Override
	public IR_Exp exp(IR_Exp basePointer) {
		if (basePointer == null) {
			throw new Error("basePointer == null in InReg");
		}
		
		return new TEMP(register);
	}
}
