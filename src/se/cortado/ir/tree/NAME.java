package se.cortado.ir.tree;

import se.cortado.ir.temp.Label;

public class NAME extends IR_Exp {
	public Label label;
	public NAME(Label l) {label=l;}
	public IR_ExpList kids() {return null;}
	public IR_Exp build(IR_ExpList kids) {return this;}
}

