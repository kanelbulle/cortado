package se.cortado.assem;

import se.cortado.ir.temp.*;

public class LABEL extends Instr {
	public Label	label;

	public LABEL(String a, Label l) {
		assem = a;
		label = l;
	}

	public TempList uses() {
		return null;
	}

	public TempList defines() {
		return null;
	}

	public Targets jumps() {
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LABEL) {
			return ((LABEL)obj).assem.equals(assem);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return assem.hashCode();
	}
}
