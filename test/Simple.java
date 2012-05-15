class Simple {
	public static void main(String[] args) {
		int i1;
		int i2;
		int i3;
		int resultat;
		int[] array;
		LillKlasse lk;
		
		lk = new LillKlasse();
		
		i1 = 3;
		i2 = 120;
		i3 = 6;
		
		array = new int[100];
		
		array[12] = 23;
		array[9] = 7;
		
		if (!(10 < 10) && 1 < 2) {
			resultat = 1;	
		} else {
			resultat = 0;
		}
		
		// resultat = i1 * i2 - i3 + i3;
		//resultat = lk.addera(array[12], i2);
		
		while (i1 < i3) {
			System.out.println(i1);
			i1 = i1 + 1;
		}
		
		System.out.println(resultat);
	}
}

class LillKlasse {
	
	public int addera(int a, int b) {
		return a + b;
	}
}
