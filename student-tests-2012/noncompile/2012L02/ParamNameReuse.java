class ParamNameReuse {
    public static void main(String[] a){
    }
}

class PNR {
	public int foo(int bar, int bar) { // : bar is already defined in foo
		return 0;
	}
}
