class MultiplicationTable {
	public static void main(String[] a) {
		Multi m;
		int maxMulti;
		int i;
		int j;

		maxMulti = 11;
		i = 1;
		j = 1;

		m = new Multi();

		while (i < maxMulti) {
			while (j < maxMulti) {
				System.out.println(m.Print(i, j));
				j = j + 1;
			}
			i = i + 1;
			j = 1;
		}
	}
}

class Multi {

	public int Print(int x, int y) {
		int product;

		product = x * y;

		return product;
	}

}
