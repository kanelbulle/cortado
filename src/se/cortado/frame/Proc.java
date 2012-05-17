package se.cortado.frame;

import java.util.List;

import se.cortado.assem.Instr;
import se.cortado.ir.temp.TempMap;

public class Proc 
{
	public String begin, end;
	public java.util.List<Instr> body;

	public Proc(String bg, List<Instr> bd, String ed) {
		begin = bg; end = ed; body = bd;
	}
	
	public String toString(TempMap tm) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(begin);
		
		for (Instr i : body) {
			sb.append(i.format(tm));
		}
		
		sb.append(end);
		
		return sb.toString();
	}
}
