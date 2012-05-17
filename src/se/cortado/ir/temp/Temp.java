package se.cortado.ir.temp;

public class Temp  {
	private static int count;
	private int num;
	
	public Temp() { 
		num = count++;
	}
	
	public String toString() {
		return "t" + num;
	}
}

