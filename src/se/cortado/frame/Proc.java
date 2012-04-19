package se.cortado.frame;

import se.cortado.assem.*;

public class Proc 
{
	public String begin, end;
	public java.util.List<Instr> body;

	public Proc(String bg, java.util.List<se.cortado.assem.Instr> bd, String ed) {
		begin = bg; end = ed; body = bd;	
	}
}
