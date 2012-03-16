package se.cortado.ir.tree;

public class TEMP extends IR_Exp {
	
	public se.cortado.ir.temp.Temp temp;
	
	public TEMP(se.cortado.ir.temp.Temp t) {
		temp = t;
	}
	
	public IR_ExpList kids() {
		return null;
	}
	
	public IR_Exp build(IR_ExpList kids) {
		return this;
	}
}

