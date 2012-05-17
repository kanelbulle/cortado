class WrongReturn {
	public static void main(String[] ignore) {
		BogusType bt;
		bt = new BogusType();

		System.out.println(bt.test());
	}
}

class BogusType {
	public int test() {
		return true;
	}
}
