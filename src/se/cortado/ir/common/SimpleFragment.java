package se.cortado.ir.common;

import se.cortado.frame.Frame;
import se.cortado.frame.Proc;
import se.cortado.ir.tree.IR_Stm;
import se.cortado.ir.tree.IR_StmList;
import se.cortado.regalloc.Liveness;
import se.cortado.regalloc.RegAlloc;

public class SimpleFragment extends Fragment {
	
	/* Inherits */
//	public String labelName;
//	public Frame frame;
//	public Fragment next;
	
	public IR_Stm body;
	
	public SimpleFragment(IR_Stm body, Frame frame, String labelName) {
		this.body = body;
		this.frame = frame;
		this.labelName = labelName;
	}
	
}