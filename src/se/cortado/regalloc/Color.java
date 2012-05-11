package se.cortado.regalloc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempList;
import se.cortado.ir.temp.TempMap;
import se.cortado.liveness.Node;

public class Color implements TempMap {

	TempMap				mInitial;
	HashMap<Temp, Temp>	mColorMap			= new HashMap<Temp, Temp>();

	Set<Node>			mSimplifyWorklist	= new HashSet<Node>();
	Set<Node>			mColoredNodes		= new HashSet<Node>();
	Set<Temp>			mSelectStack		= new HashSet<Temp>();

	public Color(InterferenceGraph ig, TempMap initial, TempList registers) {
		mInitial = initial;
	}

	public TempList spills() {
		return null;
	}

	@Override
	public String tempMap(Temp t) {
		String s = mInitial.tempMap(t);
		if (s == null) {
			s = mColorMap.get(t).toString();
		}
		return s;
	}

}
