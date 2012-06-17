package se.cortado.ir.common;

import se.cortado.frame.Frame;
import se.cortado.ir.tree.IR_Stm;

public abstract class Fragment {
	
	public String labelName;
	public IR_Stm body;
	public Frame frame;
	public Fragment next;
	
}
