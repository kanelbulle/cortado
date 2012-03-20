// 4 errors

class Superklasse {
	public static void main(String[] args) {
	}
}

class Halvklasse {
	int a;
	int j;
	
	public Superklasse fnurf3() {
		return new Halvklasse();
	}
	
	public int fnurf(int ss) {
		return true;
	}

	public int fnurf(int ss, int a) {
		int j;
		return new int[5];
	}
	
	public int fnurf2(int ss, int a) {
		int j;
		return new int[5];
	}

}
