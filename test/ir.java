class Superklasse {
	public static void main(String[] args) {
		TestClass c;
		c = new TestClass();
		System.out.println(c.myFunc(10));
	}
}

class TestClass {
	public int myFunc(int i) {
		return i*2;
	}
}
