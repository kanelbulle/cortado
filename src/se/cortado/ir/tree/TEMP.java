package se.cortado.ir.tree;

public class TEMP extends Exp {
	public se.cortado.ir.temp.Temp temp;
	public TEMP(se.cortado.ir.temp.Temp t) {temp=t;}
	public ExpList kids() {return null;}
	public Exp build(ExpList kids) {return this;}
}

