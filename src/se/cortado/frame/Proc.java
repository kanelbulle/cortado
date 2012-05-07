package se.cortado.frame;

import se.cortado.assem.*;
import se.cortado.ir.temp.TempMap;

public class Proc 
{
	public String begin, end;
	public java.util.List<Instr> body;

	public Proc(String bg, java.util.List<se.cortado.assem.Instr> bd, String ed) {
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
