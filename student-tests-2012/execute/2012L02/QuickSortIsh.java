class QuickSortIsh
{
	public static void main(String[] args) {
		QS foo;
		int [] bar;
		int i;
		int len;
		bar = new int[1000];

		i = 0;
		while(i < bar.length) {
			bar[i] = bar.length - i;
			i = i + 1;
		}

		foo = new QS();
		i = foo.sort(bar);

		i = 0;
		while(i < bar.length) {
			System.out.println(bar[i]);
			i = i + 1;
		}
	}
}

class QS {
	public int sort(int[] array) {
		return this.qsort(array, 0, array.length-1);
	}
	public int qsort(int[] array, int start, int end) {
		int mid;
		int mid_idx;
		int tmp;
		int tmp_start;
		int tmp_end;
		if(!(start < end)) // start >= end
		{}
		else {

			mid_idx = start + this.slowdiv(end-start, 2);
			mid = array[mid_idx];

			tmp_start = start;
			array[mid_idx] = array[end];
			tmp_end = end-1;
			while(tmp_start < tmp_end)
			{
				while(array[tmp_start] < mid && tmp_start < tmp_end)
				{
					tmp_start = tmp_start + 1;
				}

				while(!(array[tmp_end] < mid) && tmp_start < tmp_end)
				{
					tmp_end = tmp_end - 1;
				}
				if(tmp_start < tmp_end)
				{
					tmp = array[tmp_start];
					array[tmp_start] = array[tmp_end];
					array[tmp_end] = tmp;
				}
				else {}
			}
			tmp = this.qsort(array, start, tmp_start);
			array[end] = array[tmp_end];
			array[tmp_end] = mid;
			tmp = this.qsort(array, tmp_end + 1, end);
		}
		return 0;
	}

	public int slowdiv(int n, int div) {
		int i;
		i = 0;
		while(div < n)
		{
			n  = n - div;
			i = i + 1;
		}
		return i;
	}
}
