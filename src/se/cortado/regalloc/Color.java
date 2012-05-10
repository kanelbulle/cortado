package se.cortado.regalloc;

import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempList;
import se.cortado.ir.temp.TempMap;

public class Color implements TempMap {

	public Color(InterferenceGraph ig, TempMap initial, TempList registers) {

	}

	public TempList spills() {
		return null;
	}

	@Override
	public String tempMap(Temp t) {
		return null;
	}

}
