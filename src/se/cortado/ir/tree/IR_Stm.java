package se.cortado.ir.tree;

abstract public class IR_Stm {
	
	abstract public IR_ExpList kids();
	abstract public IR_Stm build(IR_ExpList kids);
	
}

