package se.cortado.ir.common;

import se.cortado.frame.Frame;
import se.cortado.ir.tree.IR_Stm;

public abstract class Fragment {
	
	/* TODO: Should be refactored into more suitable structures... */
//	public IR_StmList canonicalized;
//	public Proc proc;
//	public Liveness liveness;
//	public RegAlloc regalloc;
	
	public String labelName;
	public Frame frame;
	public Fragment next;
	
}
