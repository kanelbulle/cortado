class Prime {
	public static void main(String[] ignore) {
		int[] sieve;
		int i;
		int n;
		Util u;
		int count;
		u = new Util();
		sieve = new int[100000];

		// Clear sieve.
		i = 0;
		while (i < 100000) {
			sieve[i] = 0;
			i = i + 1;
		}

		// Sieveing step
		n = 2;
		while (n < 100000) {
			i = n * 2;
			while (i < 100000) {
				sieve[i] = 1;
				i = i + n;
			}

			n = n + 1;
			while (n < 100000 && u.eq(sieve[n], 1)) {
				n = n + 1;
			}
		}

		// Take out the primes from the sieve.
		i = 2;
		count = 0;
		while (i < 100000) {
			if (u.eq(sieve[i], 0)) {
				sieve[count] = i;
				count = count + 1;
			} else {}
			i = i + 1;
		}

		// Print some primes.
		System.out.println(sieve[42]);
		System.out.println(sieve[1337]);
		System.out.println(sieve[4711]);
		System.out.println(sieve[42 + 1337]);
		System.out.println(sieve[1337 + 4711]);
		System.out.println(sieve[42 + 4711]);
	}
}

class Util {
	public boolean eq(int a, int b) {
		return !(a < b) && !(b < a);
	}

	public boolean divides(int a, int b) {
		while (a < b) {
			a = a - b;
		}

		return this.eq(a, b);
	}
}
