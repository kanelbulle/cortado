class Superklasse {
	public static void main(String[] args) {
		int resultat;
		LillKlasse lk;
		
		lk = new LillKlasse();
		
		resultat = lk.addera(7, 5);
		
		System.out.println(resultat);
	}
}

class LillKlasse {
	
	public int addera(int a, int b) {
		return a + b;
	}
}
