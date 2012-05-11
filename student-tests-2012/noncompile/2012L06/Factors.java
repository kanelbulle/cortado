
class Factors
{
	public static void main(String[] args)
	{
		int n;
		int i;

		Factorer f;
		f = new Factorer();

		i = 2147483645;
		while (i < 2147483647)
		{
			n = i;
			f.factorize(n);
			System.out.println(-1);
			i = i + 1;
		}
	}
}

class Factorer
{
	public int factorize(int n)
	{
		System.out.println(n);

		for (int i = 2; i * i < n + 1; i++)
		{
			while (this.isFactor(i, n))
			{
				System.out.println(i);
				n = this.DIV(n, i);
			}
		}

		if (1 < n )	{ System.out.println(n);}
		else { n = n; }
		return -1;
	}

	public boolean isFactor(int i, int n)
	{
		return !(this.MOD(n, i) < 0) && !(0 < this.MOD(n, i));
	}

	public int DIV(int x, int y)
	{
		int q = 0;
		//int remainder = 0;
		while (y < x+1) {
			x = x - y;
			q = q + 1;
		}
		//remainder = x - (y*q);
		return q;
	}

	public int MOD(int x, int y)
	{
		return x - (y * this.DIV(x,y));
	}
}
