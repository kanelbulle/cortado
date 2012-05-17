class Foo {

	public static void main(String[] args) {
		A a;
		int arrSize;
		int sum;

		a = new A();
		sum = a.exec(100, 5);
		System.out.println(sum);
	}
}

class A {

	int size;
	int[] array;
	boolean usedDefault;

	public int exec(int x, int mul) {
		int i;

		if( (x < 0) && (mul < 0) ) {
			size = 10;
			mul = 1;
			usedDefault = true;
		} else {
			size = x;
			usedDefault = false;
		}

		array = new int[size];
		i = 0;
		while(i < array.length) {
			array[i] = (i+1)*mul;
			i = i + 1;
		}

		return this.sum();
	}

	public int sum() {
		int sum;
		int i;

		i = array.length - 1;
		sum = 0;
		while(!(i < 0)) {
			sum = sum + array[i];
			i = i - 1;
		}

		return sum;
	}

}
