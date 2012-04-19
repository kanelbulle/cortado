package se.cortado.ir.translate;

import se.cortado.ir.frame.Frame;
import se.cortado.ir.tree.IR_Stm;

public class ProcFragment extends Fragment {
	IR_Stm body;
	Frame frame;

	public ProcFragment(IR_Stm body, Frame frame) {
		this.body = body;
		this.frame = frame;
	}
	
}
