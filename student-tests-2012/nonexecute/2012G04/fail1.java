class Main {
	public static void main(String[] args) {
		Flipper f;
		boolean a;
		f = new Flipper();
		a = f.flip(true, 0);
	}
}

class Flipper {


	public boolean flip(boolean p, int q) {
		return this.flip(p,q+1);
	}
}
