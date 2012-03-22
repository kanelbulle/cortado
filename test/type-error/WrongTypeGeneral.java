// x errors

class Superklasse {
	public static void main(String[] args) {
	}
}

class Halvklasse {
	
	public int testIf() {
		int a;
		boolean b;
		Superklasse sk;
		
		// OK
		if (b) {
			b = b;
		} else {
			b = b;
		}
		
		// FAIL
		if (b + 1) {
			b = b;
		} else {
			b = b;
		}
		
		// OK
		if (1 < 2) {
			b = b;
		} else {
			b = b;
		}
		
		// OK
		if ((1 + 3) < 4) {
			b = b;
		} else {
			b = b;
		}
		
		// OK
		if (1 + 3 < 4) {
			b = b;
		} else {
			b = b;
		}
		
		return 1;
	}

}
