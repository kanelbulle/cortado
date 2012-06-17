class Superklasse {
	public static void main(String[] args) {
		TestClass c;
		int res;
		c = new TestClass();
		res = c.myFunc(10);
		System.out.println(res);
	}
}

class TestClass {
	public int myFunc(int i) {
		int j;
		j = 2*i;
		return j;
	}
}
