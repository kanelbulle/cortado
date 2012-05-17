class Superklasse {
	public static void main(String[] args) {
		MyClass m;
		int j;
		m = new MyClass();
		j = m.set(4711);
	}
}

class MyClass {
	int i;
	
	public int set(int val) {
		i = val;
		return i;
	}
	public int pr() {
		System.out.println(i);
		return 0;
	}
}
