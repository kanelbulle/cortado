package se.cortado.assem;

import se.cortado.ir.temp.*;

public class MOVE extends Instr {
	public Temp	dst;
	public Temp	src;

	public MOVE(String a, Temp d, Temp s) {
		assem = a;
		dst = d;
		src = s;
	}

	public TempList uses() {
		if (src == null) return null;
		
		return new TempList(src, null);
	}

	public TempList defines() {
		if (dst == null) return null;
		
		return new TempList(dst, null);
	}

	public Targets jumps() {
		return null;
	}

}
