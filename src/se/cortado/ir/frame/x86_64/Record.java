package se.cortado.ir.frame.x86_64;

import se.cortado.ir.frame.Access;

public class Record implements se.cortado.ir.frame.Record {
	private String name;

	public Record(String name) {
		this.name = name;
	}

	@Override
	public Access allocField() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		return name;
	}
}
