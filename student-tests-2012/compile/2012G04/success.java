// EXT:CGT

class Main {
	public static void main(String[] args) {
		Flipper f;
		int a;
		boolean b;
		LCDTV tv;

		tv = new LCDTV();
		//Let's watch TV, is it on?
		if(!tv.turnedOn()) {
			b=tv.setOn(true);
		} else {}
		//Channel 6 always brings the good stuff
		b=tv.chooseChannel(6);
		//Did it really change, let's try til it does
		while(tv.getChannel() < 6 && 6 < tv.getChannel()) {
			b = tv.chooseChannel(6);
		}
		System.out.println(tv.getChannel());
		a=10000;
		//Let's watch for a bit
		while(a>0) {
			a=a-1;
		}
		//Turn the TV back off
		b=tv.setOn(false);


		//Let's try some other stuff
		a=0;
		b=true;
		f = new Flipper();
		a = f.getNum(b,a);
	}
}

class Flipper {
	// NEDAN AER MIN BOOLEAN
	boolean a;
	/*
	HAR AER MIN MULTILINE COMMENT med ^allmojliåäöga special`?-tecke´´n, but heyidn't care okay?!
	boolean QRUTT
	titta tyska üüüüü och ett 
	*/
	boolean b;
	int d;
	/* annoying comment */
	int[] arr;

	public int getNum(boolean p, int q) {
		boolean c;
		int __aa;
		__aa=5;
		c=false;
		if(arr.length < 24) {
			d = q;
		} else {
			d = arr.length;
		}
		{{{{{{{{{System.out.println(1);}}}}}}}}}
		if(arr.length < 24 && !(arr.length+2<4)) {
			c			=				c;
		} else {
			q = arr.length;
		}
		arr = new int[5+24+arr.length];


		q = 24+13+13*(5+2+3*1323*23+(123*123+(123)));

		System.out.println(arr.length);

		c = a;

		return 7;
	}
}

class LCDTV {
	boolean _is_on;
	int _channel;

	public boolean turnedOn() {
		return _is_on;
	}

	public boolean setOn(boolean new_state) {
		_is_on = new_state;
		return new_state;
	}

	public boolean chooseChannel(int channel) {
		_channel = channel;
		return true;
	}

	public int getChannel() {
		return _channel;
	}
}

