class Foo {

	public static void main(String[] args) {

			A a;
			B b;
			C c;
			D d;
			E e;

			a = new A();
			b = new B();
			c = new C();
			d = new D();
			e = new E();

			System.out.println(a.foo());
			System.out.println(b.foo());
			System.out.println(c.foo());
			System.out.println(d.foo());
			System.out.println(e.foo());

	}

}

class A {

	public int foo() {
		return 9595;
	}

}

class B {
	public int foo() {
		return 4234234;
	}
}

class C {
	public int foo() {
		return 243423;
	}
}

class D {
	public int foo() {
		return 438432;
	}
}

class E {
	public int foo() {
		return 4712;
	}
}
