class Bogus4 {
	public static void main(String[] args) {
		int a;
		a = (new A()).A(true);
	}
}

class A {
	public int A(int a) { // type mismatch
		return 1;
	}
}
