class Superklasse {
	public static void main(String[] args) {
		Klasse klass;
		int c;
		
		klass = new Klasse();
		c = klass.f(1, 2);
	}
}

class Klasse {
	
	public int f(int a, int b) {
		int d;
		int e;
		
		d = 0;
		e = a;
		while (0 < e + 1) {
			e = e - 1;
		}
		
		return d;
	}
}


