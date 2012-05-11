package se.cortado.regalloc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempList;
import se.cortado.ir.temp.TempMap;
import se.cortado.liveness.Node;

public class Color implements TempMap {

	HashMap<Temp, Temp>	mColorMap		= new HashMap<Temp, Temp>();

	Set<Node>			mSimplifyWorklist	= new HashSet<Node>();
	Set<Node>			mColoredNodes		= new HashSet<Node>();
	Set<Temp>			mSelectStack		= new HashSet<Temp>();
	
	

	public Color(InterferenceGraph ig, TempMap initial, TempList registers) {

	}

	public TempList spills() {
		return null;
	}

	@Override
	public String tempMap(Temp t) {
		return mColorMap.get(t).toString();
	}

}
