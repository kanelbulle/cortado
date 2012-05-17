class MethodRedecl{
	public static void main(String[] args) {
	}
}

class Foo {
	public int bar() {
		return 0;
	}

	// MethodRedecl.java:11: bar() is already defined in Foo
	public int bar() {
		return 0;
	}
}
