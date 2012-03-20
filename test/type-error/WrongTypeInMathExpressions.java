// 3 errors

class Superklasse {
	public static void main(String[] args) {
	}
}

class Halvklasse {
	int a;
	boolean b;
	
	public int testMath() {
		int c;
		boolean d;
		
		// FAIL int = boolean + int
		a = b + 1;
		
		// OK boolean = boolean
		d = b;
		
		// FAIL boolean = boolean + int
		d = true + 1;
		
		// OK int + int * int
		c = 1 + 2 * 3 - c + a*a;
		
		return 1;
	}

}
