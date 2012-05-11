
/*
 * Should output 101
 */

// EXT:LONG
// EXT:CEQ
// EXT:CNE
// EXT:CGE

class MainClassPollardsRho
{
	public static void main(String[] args) {
		PollardsRho pr;
		pr = new PollardsRho();
		System.out.println(pr.calc(30738341111L));
	}
}

class PollardsRho
{
	public long calc(long n)
	{
		long x;
		long y;
		long d;
		long i;
		long ret;

		x = 2L;
		y = 2L;
		d = 1L;
		i = 1L;
		ret = 0L-1L;

        if (n == 1L)
		{
        	ret = 1L;
		}
		else
		{
           	while(i < 1000)
           	{
           		while (d == 1L)
                {
           			x = this.mod((x * x + i), n);
                    y = this.mod((y * y + i), n);
                    y = this.mod((y * y + i), n);
                    d = this.gcd(this.abs(x-y), n);
                }

                if (d == n)
                {
                	i = i + 1L;
                }
                else
                {
                	ret = d;
                	i = 10001L;
                }
            }
        }
        return ret;
	}

    public long gcd(long a, long b)
    {
        long remainder;

        while (b != 0L)
        {
            remainder = this.mod(a, b);
            a = b;
            b = remainder;
        }

        return a;
    }

	public long mod(long x, long n)
	{
		while(x >= n)
		{
			x = x - n;
		}
		return x;
	}

	public long abs(long x)
	{
		if(x < 0L)
		{
			x = 0L - x;
		}
		else
		{
		}
		return x;
	}
}
