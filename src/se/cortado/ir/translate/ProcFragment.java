package se.cortado.ir.translate;

import se.cortado.frame.Frame;
import se.cortado.ir.tree.IR_Stm;

public class ProcFragment extends Fragment {
	public IR_Stm body;
	public Frame frame;
	public String labelName;

	public ProcFragment(IR_Stm body, Frame frame, String labelName) {
		this.body = body;
		this.frame = frame;
		this.labelName = labelName;
	}
	
}
