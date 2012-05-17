class MultiClass {
	public static void main(String[] args) {
		System.out.println(new class2().run());
	}
}

class class2 {
	public int run() {
		return new class3().run();
	}
}

class class3 {
	public int run() {
		return 42;
	}
}
