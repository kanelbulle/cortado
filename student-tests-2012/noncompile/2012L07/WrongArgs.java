class WrongArgs {
	public static void main(String[] ignore) {
		BogusType bt;
		bt = new BogusType();

		System.out.println(bt.test(1337, 4711));
	}
}

class BogusType {
	public int test(int a, boolean b) {
		return 42;
	}
}
