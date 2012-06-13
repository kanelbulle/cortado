package se.cortado.ir.translate;

import se.cortado.frame.Frame;
import se.cortado.frame.Proc;
import se.cortado.ir.tree.IR_Stm;
import se.cortado.ir.tree.IR_StmList;
import se.cortado.regalloc.Liveness;
import se.cortado.regalloc.RegAlloc;

public class ProcFragment extends Fragment {
	
	public IR_Stm body;
	public Frame frame;
	public String labelName;
	public IR_StmList canonicalized;
	public Proc proc;
	public Liveness liveness;
	public RegAlloc regalloc;
	
	public ProcFragment(IR_Stm body, Frame frame, String labelName) {
		this.body = body;
		this.frame = frame;
		this.labelName = labelName;
	}
	
}
