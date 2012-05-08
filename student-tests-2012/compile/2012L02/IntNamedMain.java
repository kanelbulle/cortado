class IntNamedMain {
	public static void main(String[] args) {
		System.out.println(new Foo().bar());
	}
}

class Foo {
	public int bar() {
		int main;
		main = 5;
		return main;
	}
}
