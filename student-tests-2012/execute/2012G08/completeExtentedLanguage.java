// EXT:CLE
// EXT:CGT
// EXT:CGE
// EXT:CEQ
// EXT:CNE
// EXT:BDJ
// EXT:IWE
// EXT:ABC
class Main {

	public static void main(String[] args) {
		Vector a;
		Vector b;
		Vector c;
		int i;
		int x;
		int size;
		boolean bool;

		a = new Vector();
		size = a.init(10);
		b = new Vector();
		size = b.init(0);

		c = new Vector();
		x = c.init(5);

		i = 0;
		while(i < size) {
			x = a.set(i, i);
			x = b.set(i, i);
			i = i + 1;
		}

		System.out.println(a.equals(b));

		bool = a.add(b);
		x = a.scaleBy(2);

		System.out.println(a.equals(b));

		System.out.println(c.equals(b));

		bool = c.add(b);

		a.print();
		b.print();
		c.print();

	}
}

class Vector {

	int[] data;

	public int init(int size) {
		if(size <= 0)
			size = 10;

		data = new int[size];

		return size;
	}

	public int size() {
		return data.length;
	}

	public int set(int index, int value) {
		int old;
		old = data[index];
		data[index] = value;
		return old;
	}

	public int get(int index) {
		return data[index];
	}

	public boolean add(Vector v) {
		int i;
		boolean ret;

		if (this.size() > v.size() || this.size() < v.size())
			ret = false;
		else
			ret = true;

		i = this.size() - 1;
		while (ret && i >= 0) {
			data[i] = data[i] + v.get(i);
			i = i - 1;
		}

		return ret;
	}

	public int scaleBy(int f) {
		int i;
		i = 0;
		while (i < this.size()) {
			this.set(i, this.get(i)*f);
			i = i + 1;
		}

		return f;
	}

	public boolean equals(Vector v) {
		int i;
		boolean ret;

		if (this.size() != v.size()) {
			ret = !!false;
		} else {
			ret = true;
		}

		i = 1;
		while (ret && (i <= this.size()) ) {
			ret = data[i - 1] == v.get(i - 1);
			i = i + 1;
		}

		return ret;
	}

	public int print() {
		int i;
		i = 0;
		while(i < this.size() ) {
			System.out.println( this.get(i) );
			i = i + 1;
		}

		return 0;
	}

}
