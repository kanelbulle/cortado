class Pollard {
	public static void main(String[] args){
		PollardRho pollard;
		int ret;
		int i;
		int[] testnumbers;

		pollard = new PollardRho();
		testnumbers = new int[4];

		testnumbers[0] = 229 * 233; // 229 * 233
		testnumbers[1] = 197 * 241; // 197 * 241
		testnumbers[2] = 227 * 223; // 227 * 223
		testnumbers[3] = 211 * 199; // 211 * 199

		i = 0;
		while(i < testnumbers.length){
			ret = pollard.p(testnumbers[i]);
			System.out.println(testnumbers[i]);
			System.out.println(ret);
			System.out.println(pollard.div(testnumbers[i], ret));

			i = i + 1;
		}
	}
}

class PollardRho{
	// Try to find a prime factor in n, returns 0 if fail, otherwise returns one factor
	public int p(int n){
		int x;
		int y;
		int d;
		int retu;

		x = 2;
		y = 2;
		d = 1;
		while(this.intEquals(d,1)){
			x = this.f(x, n);
			y = this.f(this.f(y, n), n);
			d = this.gcd(this.abs(x - y), n);
		}

		if(this.intEquals(d, n)){
			retu = 0;
		}
		else{
			retu = d;
		}
		return retu;
	}

	// f function used by pollard rho
	public int f(int x, int n){
		int retu;

		retu = this.modulo((x * x + 5), n);

		return retu;
	}

	// returns a % n
	public int modulo(int a, int n){
		int retu;

		retu = a - (n * this.div(a, n));

		return retu;
	}

	//Calculates the greatest common divisor (gcd) of a and b
	public int gcd(int a, int b){
		int t;
		while(!this.intEquals(b, 0)){
			t = b;
			b = this.modulo(a, b);
			a = t;
		}
		return a;
	}

	// computes i / j
	public int div(int i, int j){
		int q;
		q = 1;

		while(q*j < i+1){
			q = q+1;
		}

		q = q-1;
		return q;
	}

	// True if i == j
	public boolean intEquals(int i, int j){
		boolean retu;

		if(!(i < j) && !(j < i)){
			retu =  true;
		}
		else{
			retu = false;
		}

		return retu;
	}

	// Returns abs value of n
	public int abs(int n){

		if(n < 0){
			n = 0 - n;
		}
		else{

		}

		return n;
	}
}
