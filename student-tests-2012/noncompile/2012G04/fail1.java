class Main {
	public static void main(String[] args) {
		Flipper f;
		int a;
		f = new Flipper();
		a = f.getNum(true, 3);
	}
}

class Flipper {


	public int getNum(boolean p, int q) {
		boolean c;

		if(q < 24) {
			c = p;
		} else {
			c = q;
		}

		return q * 7;
	}
}
