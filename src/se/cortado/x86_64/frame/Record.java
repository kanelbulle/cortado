package se.cortado.x86_64.frame;

import se.cortado.frame.Access;

public class Record implements se.cortado.frame.Record {
	private int offset;
	private String name;

	public Record(String name, int offset) {
		this.name = name;
		this.offset = offset;
	}

	public Record(String name) {
		this(name, 0);
	}

	@Override
	public String toString() {
		return "x86_64.Record(" + name + "; " + offset + ")";
	}

	@Override
	public Access allocField() {
		Access a = new InFrame(offset);
		offset += Hardware.wordSize;
		return a;
	}

	@Override
	public int size() {
		return offset;
	}

	public String getName() {
		return name;
	}
}
