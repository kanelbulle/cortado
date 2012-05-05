// EXT:LONG

class Main
{
	public static void main(String [ ]args)
	{
		long[] la;
		
		// long index is bad.
		la = new long[new Long().Long()];
		
		// Still bad.
		la[new Long().Long()] = 5;
	}
}

class Long
{
	public long Long()
	{
		return 4;
	}
}
