// EXT:ISC
// EXT:ICG
// EXT:LONG
// EXT:IWE
// EXT:NBD
// EXT:CLE
// EXT:CGT
// EXT:CGE
// EXT:CEQ
// EXT:CNE
// EXT:BDJ

class _1g64r976g34f4397gf7g94fg3gy3Main
{
    public static void main/*main*/(String[/**/] args)//Â‰ˆ≈ƒ÷~\1ß
    {
    	int     i    ;
    	int[   ]ia;
    	long l;
    	long[]la;
    	{
    		boolean b;
    		_1g64r976g34f4397gf7g94fg3gy3Main _1g64r976g34f4397gf7g94fg3gy3Main;
    		MainChild mc;
    		_1g64r976g34f4397gf7g94fg3gy3Main = new _1g64r976g34f4397gf7g94fg3gy3Main();

    		mc = new MainChild();
    		b = mc.initialize();
    		l = mc.dostuff(new MainGrandChild());
    		mc = new MainGrandChild();
    		b = mc.initialize();
    		l = mc.dostuff(new MainGrandChild());
    		System.out.println(mc.fibonacci(37));
    	}
    }
}

class MainChild extends/**/_1g64r976g34f4397gf7g94fg3gy3Main
{
	int i;
	long l;

	public boolean initialize()
	{
		i = 1337;
		l = 133L;
		return true;
	}

	public long dostuff(MainGrandChild mgc)
	{
		boolean b;
		b = true;
		i = i * (((((i)))) + i) - i;
		if (i < 5l && i<=4L || i>  7l && i >= 8L || i == 5L && i!=140737488355328L && b || false)
			if (true)
				b = !true;
			else if (false)
				if (true)
					b = b;
				else
					b = !b;
		{
			long i;
			{
				int l;
				i = 0;
				l = 0;
				{
					i = this.getl();
					l = this.geti();
					System.out.println(this.geti());
					System.out.println(this.getl());
				}
				i = 1;
				l = 1;
				System.out.println(this.geti());
				System.out.println(this.getl());
			}
			i = 2;
			l = 2;
			System.out.println(this.geti());
			System.out.println(this.getl());
		}
		i = 3;
		l = 3;
		System.out.println(this.geti());
		System.out.println(this.getl());

		return i;
	}

	public int geti()
	{
		return i;
	}

	public long getl()
	{
		return l;
	}

	public int seti(int newi)
	{
		i = newi;
		return i;
	}

	public long setl(int newl)
	{
		l = newl;
		return l;
	}

	public long fibonacci(int i)
	{
		return i;
	}
}

class MainGrandChild extends MainChild
{
	int l;
	public long dostuff(MainGrandChild arne)
	{
		arne = this;
		i = 6;
		l = i;
		return l;
	}

	public long fibonacci(int i)
	{
		long ans;
		if (i <= 1)
			ans = new MainChild().fibonacci(i);
		else
			ans = this.fibonacci(i - 2) + new MainGrandChild().fibonacci(i - 1);
		return ans;
	}
}

