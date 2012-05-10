package se.cortado.regalloc;

import java.util.List;

import se.cortado.assem.Instr;
import se.cortado.frame.Frame;
import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempMap;

public class RegAlloc implements TempMap {

	public List<Instr>	instructions;

	public RegAlloc(Frame frame, List<Instr> instrList) {

	}

	@Override
	public String tempMap(Temp t) {
		// TODO Auto-generated method stub
		return null;
	}

}
