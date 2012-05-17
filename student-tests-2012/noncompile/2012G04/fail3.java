class Main {
	public static void main(String[] args) {
		Flipper f;
		Cat c;
		int a;
		f = new Flipper();
		a = f.getNum(true, 3);

		c = new Cat();

		System.out.println(c.meow());
	}
}

class Flipper {
	boolean c;

	public int getNum(boolean p, int q) {
		boolean p;

		if(q < 24) {
			c = p;
		} else {
			c = q;
		}

		return q * 7;
	}
}

class Cat {
	boolean meow() {
		int sound = 771;

		sound = !sound;

		return sound + 134;
	}

	int sing() {
		boolean sound = true;

		sound = !sound;

		return sound - 5;
	}
}

class Car {
	int MODEL;

	boolean setModel(int model) {
		MODEL = model;
		return true;
	}

	int getModel() {
		return MODEL;
	}
}
