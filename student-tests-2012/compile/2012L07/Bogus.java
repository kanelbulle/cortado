class Bogus {
	public static void main(String[] ignore) {
		int[] a;
		boolean b;
		BogusType bt;

		a = new int[47 * 11];
		bt = new BogusType();

		{{{{
			b = false;
		}}}}

		a[bt.test(b)] = bt.test(!b);

		System.out.println(a[bt.test(b)]);
	}
}

class BogusType {
	BogusType bt;

	public int test(boolean arg) {
		int r;
		if (arg) {
			r = 13;
		} else {
			r = 37;
		}
		return r;
	}
}
