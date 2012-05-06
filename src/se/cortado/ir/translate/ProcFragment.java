package se.cortado.ir.translate;

import se.cortado.frame.Frame;
import se.cortado.ir.tree.IR_Stm;
import se.cortado.ir.tree.IR_StmList;

public class ProcFragment extends Fragment {
	public IR_Stm body;
	public Frame frame;
	public String labelName;
	public IR_StmList canonicalized;
	public int maxCallParams;

	public ProcFragment(IR_Stm body, Frame frame, String labelName, int maxCallParams) {
		this.body = body;
		this.frame = frame;
		this.labelName = labelName;
		this.maxCallParams = maxCallParams;
	}
	
}
