// 7 errors

class Superklasse {
	public static void main(String[] args) {
	}
}

class Halvklasse {
	int a;
	int j;
	boolean b;
	int[] c;
	
	public Superklasse fnurf3() {
		b = new Superklasse();
		b = 1;
		a = true;
		a = false;
		c = new Superklasse();
		c = 1;
		
		return new Superklasse();
	}
	
	public int test() {
		Halvklasse hk;
		Halvklasse hk2;
		
		hk = hk2.fnurf3();
		
		return 1;
	}

}
