package se.cortado.regalloc;

import java.util.List;

import se.cortado.assem.Instr;
import se.cortado.frame.Frame;
import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempMap;

public class RegAlloc implements TempMap {

	public List<Instr>	instructions;
	private Color		color;

	public RegAlloc(Frame frame, List<Instr> instrList, Liveness liveness) {
		color = new Color(liveness, frame, frame.registers());
	}

	@Override
	public String tempMap(Temp t) {
		return color.tempMap(t);
	}

}
