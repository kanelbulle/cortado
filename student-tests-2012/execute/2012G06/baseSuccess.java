class Main
{
	public static void main(String[] args)
	{
		Fraction pi;
		int i;
		pi = new Fraction().set(312689, 99532); // Almost
		i = 0;
		while (i < 10)
		{
			System.out.println(new Fraction().div(pi.getDivident(), pi.getDivisor()));
			pi = pi.subtract(new Fraction().setInt(new Fraction().div(pi.getDivident(), pi.getDivisor())))
				.multiply(new Fraction().setInt(10));
			i = i + 1;
		}
	}
}

class Fraction
{
	int divident;
	int divisor;

	public int div(int a, int b)
	{
		int sign;
		int times;
		sign = 1;
		if (a < 0)
		{
			sign = sign * (0-1);
			a = a * (0-1);
		}
		else
			sign = sign;

		if (b < 0)
		{
			sign = sign * (0-1);
			b = b * (0-1);
		}
		else
			sign = sign;

		times = 0;
		while (!(a < b * times))
		{
			times = times + 1;
		}
		times = times - 1;

		return times * sign;
	}

	public int mod(int a, int b)
	{
		int sign;
		int times;
		sign = 1;
		if (a < 0)
		{
			sign = sign * (0-1);
			a = a * (0-1);
		}
		else
			sign = sign;

		if (b < 0)
		{
			sign = sign * (0-1);
			b = b * (0-1);
		}
		else
			sign = sign;

		times = 0;
		while (!(a < b * times))
		{
			times = times + 1;
		}
		times = times - 1;

		return (a - times * b) * sign;
	}

	public int GCD(int a, int b)
	{
		int ret;
		if (!(b < 0) && !(0 < b))
			ret = a;
		else
			ret = this.GCD(b, this.mod(a, b));
		return ret;
	}

	public Fraction setZero()
	{
		divident = 0;
		divisor = 1;

		return this;
	}

	public Fraction setInt(int value)
	{
		divident = value;
		divisor = 1;

		return this;
	}

	public Fraction set(int numerator, int denominator)
	{
		int sign;
		int gcd;

		divident = numerator;
		divisor = denominator;

		sign = 1;
		if (divisor < 0)
		{
			sign = sign * (0-1);
			divisor = divisor * (0-1);
		}
		else
			sign = sign;

		if (divident < 0)
		{
			sign = sign * (0-1);
			divident = divident * (0-1);
		}
		else
			sign = sign;

		gcd = this.GCD(divident, divisor);
		divident = this.div(divident, (gcd * sign));
		divisor = this.div(divisor, gcd);

		return this;
	}

	public int getDivident()
	{
		return divident;
	}

	public int getDivisor()
	{
		return divisor;
	}


	public Fraction multiply(Fraction other)
	{
		return new Fraction().set(divident * other.getDivident(), divisor * other.getDivisor());
	}

	public Fraction divide(Fraction other)
	{
		return new Fraction().set(divident * other.getDivisor(), divisor * other.getDivident());
	}

	public Fraction add(Fraction other)
	{
		return new Fraction().set(divident * other.getDivisor() + divisor * other.getDivident(), divisor * other.getDivisor());
	}

	public Fraction subtract(Fraction other)
	{
		return new Fraction().set(divident * other.getDivisor() - divisor * other.getDivident(), divisor * other.getDivisor());
	}

	public Fraction negate()
	{
		return new Fraction().set(0-divident, divisor);
	}

	public boolean equals(Fraction other)
	{
		return (!(divident < other.getDivident()) && !(other.getDivident() < divident) && !(divisor < other.getDivisor()) && !(other.getDivisor() < divisor));
	}


	public Fraction print()
	{
		System.out.println(divisor);

		return this;
	}
}
