class Primes {
	public static void main(String[] args){
		Prime p;
		int retu;

		p = new Prime();
		retu = p.calc(10000);
	}
}

class Prime{
	public int calc(int max){
		int i;
		int j;
		boolean stop;
		i = 3;

		//2 is prime but this algorithm only finds primes > 2
		System.out.println(2);

		while(i < max){
			j = 2;
			stop = false;
			//System.out.println("i = "+i+" sqrt = "+ this.intSqrt(i));
			while (j < this.intSqrt(i)+1 && !stop){
				if(this.divisor(i, j)){
					//not prime
					stop = true;
				}
				else{

				}

				j = j+1;
			}

			if(!stop){
				//prime
				System.out.println(i);
			}
			else{

			}

			i = i+1;
		}
		return 0;
	}

	public int intSqrt(int i){
		int n;
		int next;

		n = 1;
		next = this.div(n+this.div(i,n),2);

		while(1 < this.abs(next - n)){
			n = next;
			next = this.div(n+this.div(i,n),2);
		}

		next = next +1;
		while(i < next * next){
			next = next -1;
		}
		return next;
	}

	// true if j divides i
	public boolean divisor(int i, int j){
		int q;
		boolean retu;
		q = this.div(i, j);
		//System.out.println("i = "+i+" j = "+j+" q = "+q);

		if(this.intEquals(q*j, i)){
			retu = true;
		}
		else{
			retu = false;
		}

		return retu;
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

	public int abs(int n){

		if(n < 0){
			n = 0 - n;
		}
		else{

		}

		return n;
	}
}
