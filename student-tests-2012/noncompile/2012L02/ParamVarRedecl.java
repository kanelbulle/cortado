class ParamVarRedecl {
    public static void main(String[] a){
    }
}

class PVD {
	public int foo(int bar) {
		int bar; // : bar is already defined in foo(int)
		return 0;
	}
}
