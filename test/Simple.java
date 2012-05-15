class Simple {
	public static void main(String[] args) {
		int i1;
		int i2;
		int i3;
		int resultat;
		LillKlasse lk;
		
		lk = new LillKlasse();
		
		i1 = 3;
		i2 = 120;
		i3 = 5;
		// 
		// resultat = i1 * i2 - i3 + i3;
		resultat = lk.addera(i1, i2);
		
		System.out.println(resultat);
	}
}

class LillKlasse {
	
	public int addera(int a, int b) {
		return a + b;
	}
}
