/*
 * Should output 28572
 * No extensions required
 */
class InsertionSort {

	public static void main(String[] args)
	{
		InsertionSort sorter;
		int i;
		int[] in;
		int out;

		sorter = new InsertionSort();
		in = new int[40000];

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
			if(in[i+1] < in[i] && !(out < 0))
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

	public int[] sort(int[] in)
	{
		int i;
		int j;
		int tmp;
		int k;

		k = 1;
		i = 1;
		while(i < in.length)
		{
			k = i;
			tmp = in[i];
			j = i-1;
			while(!(j < 0))
			{
				if(tmp < in[j])
				{
					in[j+1] = in[j];
					k = j;
					j = j - 1;
				}
				else
				{
					j = 0-1;
				}
			}
			in[k] = tmp;
			i = i + 1;
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
}
