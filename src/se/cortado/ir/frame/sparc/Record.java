package se.cortado.ir.frame.sparc;

/**
   An implementation of the Record interface for the Sun
   processor architecture (SPARC).
   @see frame.Record
 */
public class Record implements se.cortado.ir.frame.Record
{
	private int offset = 0;
	private String n;

	public Record(String name) {
		this(name, 0);
	}

	public String toString() {
		return "sparc.Record(" + n + "; " + offset + ")";
	}

	public Record(String name, int firstOffset) {
		n = name;
		offset = firstOffset;
	}

	public se.cortado.ir.frame.Access allocField() {
		se.cortado.ir.frame.Access a = new InFrame(offset);
		offset += Hardware.wordSize;
		return a;
	}

	public int size() {
		return offset;
	}

}