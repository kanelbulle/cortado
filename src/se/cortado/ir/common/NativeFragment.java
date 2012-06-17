package se.cortado.ir.common;

import java.util.List;

import se.cortado.assem.Instr;
import se.cortado.frame.Frame;
import se.cortado.frame.Proc;
import se.cortado.ir.tree.IR_Stm;
import se.cortado.ir.tree.IR_StmList;
import se.cortado.regalloc.Liveness;
import se.cortado.regalloc.RegAlloc;

public class NativeFragment extends Fragment {
	
	/* Inherits */
//	public String labelName;
//	public Frame frame;
//	public Fragment next;
	
	public Proc proc;
	List<Instr> body;
	public Liveness liveness;
	public RegAlloc regalloc;
	
	public NativeFragment(List<Instr> body, Frame frame, Proc proc) {
		this.body = body;
		this.frame = frame;
		this.proc = proc;
		// dont think label is needed in native code anymore.. safe to remove?
//		this.labelName = labelName;
	}
	
}
