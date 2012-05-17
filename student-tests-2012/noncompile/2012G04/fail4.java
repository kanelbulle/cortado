class Main {
	public static void main(String[] args) {
		Flipper f;
		Flupper f2;
		int a;
		f = new Flipper();
		f2 = new Flupper();
		a = f2.flipp(f);
		a = f2.flupp();
		a = f.getNum(true, 3);
	}
}

class Flupper {
	Flipper flip;
	boolean f;
	public int flipp(Flipper fl){
		flip = fl;
		return 1;
	}

	public int flupp(){
		flip = new Flipper();
		f = true;
		return 100;
	}
}

class Flipper {
	boolean c;
	int b;
	public int getNum(boolean p, int q) {

		if(q < 24) {
			c = p;
		} else {
			b = q;
		}

		return q * 7;
	}

	public int getNum(boolean p, int q) {


		return 7;
	}
}

