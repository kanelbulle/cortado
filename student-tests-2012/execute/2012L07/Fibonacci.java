class Fibonacci {
	public static void main(String[] ignore) {
		FibGen fib;
		int i;
		fib = new FibGen();

		i = 0;
		while (i < 20) {
			System.out.println(fib.gen(i));
			i = i + 1;
		}
	}
}

class FibGen {
	// Return n'th fibonacci number.
	public int gen(int n) {
		int r;
		if (n < 2) {
			r = n;
		} else {
			r = this.gen(n - 1) + this.gen(n - 2);
		}
		return r;
	}
}
