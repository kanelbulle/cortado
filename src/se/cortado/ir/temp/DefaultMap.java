package se.cortado.ir.temp;

public class DefaultMap implements TempMap {
	public String tempMap(Temp t) {
	   return t.toString();
	}

	public DefaultMap() {}
}
