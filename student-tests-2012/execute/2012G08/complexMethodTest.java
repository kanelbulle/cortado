// EXT:CGE
class Foo {

	public static void main(String[] args) {
		A a;
		int x;
		a = new A();
		x = a.foo();
	
		System.out.println(x);
	}
}

class A {

	int aInt;

	public int foo() {
		
		A a;
		int x;
		int i;
		
		aInt = 423424;
		System.out.println(aInt);
				
		a = new A();
		
		x = a.one();
		
		System.out.println(aInt);
		
		i = 10;
		x = this.two(i, 13, 18);
		
		return 12345;
	}
	
	public int one() {
			aInt = 99;
			return 0;
	}
	
	public int two(int i, int a, int b) {
		int x;
		
		System.out.println(a);
		System.out.println(b);

		if(i > 0) {
			System.out.println(i);
			x = this.three(i-1, i < 5, i >= 2);
		}
		
		return 0;
	}
	
	public int three(int i, boolean a, boolean b) {
		int x;
		boolean z;
		
		z = a && b;
		System.out.println(z);	
	
		if(i > 0) {
			System.out.println(i);
			x = this.two(i-1, i+1, i+2);
		}
		
		return 0;
	}

}
