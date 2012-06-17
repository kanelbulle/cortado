package se.cortado.ir.common;

import se.cortado.frame.Frame;
import se.cortado.frame.Proc;
import se.cortado.ir.tree.IR_Stm;
import se.cortado.ir.tree.IR_StmList;
import se.cortado.regalloc.Liveness;
import se.cortado.regalloc.RegAlloc;

public class SimpleFragment extends Fragment {
	
//	public IR_StmList canonicalized;
//	public Proc proc;
//	public Liveness liveness;
//	public RegAlloc regalloc;
	
	public SimpleFragment(IR_Stm body, Frame frame, String labelName) {
		this.body = body;
		this.frame = frame;
		this.labelName = labelName;
	}
	
}
