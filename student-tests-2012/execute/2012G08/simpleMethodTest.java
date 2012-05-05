class Foo {

	public static void main(String[] args) {
		A a;
		int[] d;
		int x;
		
		a = new A();
		d = new int[10];
		d[3] = 1337;
		
		x = a.foo(10, 20, true, d);
		System.out.println(x);
	
	}
}

class A {

	public int foo(int a, int b, boolean c, int[] d) {
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d[3]);
	
		return 4712;
	}

}
