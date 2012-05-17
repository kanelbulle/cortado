package se.cortado.ir.tree;

import se.cortado.ir.temp.Label;

public class LABEL extends IR_Stm { 
	public Label label;
	public LABEL(Label l) {label=l;}
	public IR_ExpList kids() {return null;}
	public IR_Stm build(IR_ExpList kids) {
		return this;
	}
}

