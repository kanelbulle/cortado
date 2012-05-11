// EXT:CGT

/*
 * Should output 21425
 */
class MergeSortMC {
	public static void main(String[] args)
	{
		MergeSort sorter;
		int i;
		int[] in;
		int out;

		sorter = new MergeSort();
		in = new int[30000];

		i = 0;
		while(i < in.length)
		{
			in[i] = sorter.mod(i, 11);
			i = i + 1;
		}

		in = sorter.sort(in);

		out = 0;
		i = 0;
		while(i < in.length-1 && !(out < 0))
		{
			if(in[i] > in[i+1])
			{
				out = 0-1;
			} else {}
			i = i + 1;

			if(!(0 < sorter.mod(i, 7)))
			{
				out = out + in[i];
			} else {}
		}
		System.out.println(out);
	}
}

class MergeSort {
	public int[] sort(int[] in)
	{
		int ai;
		int bi;
		int[] a;
		int[] b;
		int tmp;

		if(!(2 < in.length))
		{
			if(!(in.length < 0) && !(0 < in.length))
			{
				in = new int[0];
			} else {
				if(!(in.length < 1) && !(1 < in.length))
				{
				} else {
					if(in[1] < in[0])
					{
						ai = in[1];
						in[1] = in[0];
						in[0] = ai;
					} else {}
				}

			}
		}
		else
		{
			tmp = this.div(in.length, 2);
			a = new int[tmp];
			b = new int[in.length - tmp];

			ai = 0;
			while(ai < a.length)
			{
				a[ai] = in[ai];
				ai = ai + 1;
			}
			bi = 0;
			while(bi < b.length)
			{
				b[bi] = in[bi+a.length];
				bi = bi + 1;
			}

			a = this.sort(a);
			b = this.sort(b);

			ai = 0;
			bi = 0;
			while(ai+bi < in.length)
			{
				if(ai < a.length)
				{
					if(bi < b.length)
					{
						if(!(b[bi] < a[ai]))
						{
							in[ai+bi] = a[ai];
							ai = ai + 1;
						}
						else
						{
							in[ai+bi] = b[bi];
							bi = bi + 1;
						}
					}
					else
					{
						in[ai+bi] = a[ai];
						ai = ai + 1;
					}
				}
				else
				{
					in[ai+bi] = b[bi];
					bi = bi + 1;
				}
			}
		}
		return in;
	}

	public int mod(int x, int n)
	{
		while(!(x < n))
		{
			x = x - n;
		}
		return x;
	}

	public int div(int x, int y)
	{
		int out;
		out = 0;

		while(!(x < y))
		{
			x = x - y;
			out = out + 1;
		}

		return out;
	}
}
