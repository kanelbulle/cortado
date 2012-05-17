// EXT:BDJ
class Foo {

	public static void main(String[] args) {

		A a;
		int x;
		boolean b;
		boolean c;

		a = new A();
		x = a.setVal();

		b = true;
		c = b || a.sideEffect();

		System.out.println(a.getVal());

		b = false;
		c = b || a.sideEffect();

		System.out.println(a.getVal());

	}

}

class A {
	int val;

	public int setVal() {
		val = 102;
		return val;
	}

	public int getVal() { return val; }

	public boolean sideEffect() {
		val = 4712;
		return true;
	}
}
