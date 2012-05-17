class Quicksort {
	public static void main(String[] ignore) {
		Sorter s;
		boolean b;
		s = new Sorter();
		b = s.init();
		b = s.sort();
		b = s.print();
	}
}

class Sorter {
	int[] data;

	public boolean init() {
		data = new int[20];
		data[0] = 475025;
		data[1] = 39089371;
		data[2] = 98633482;
		data[3] = 18535809;
		data[4] = 6292128;
		data[5] = 45067761;
		data[6] = 39869153;
		data[7] = 63942395;
		data[8] = 48625988;
		data[9] = 95348451;
		data[10] = 59832692;
		data[11] = 8243805;
		data[12] = 78193854;
		data[13] = 265876;
		data[14] = 1337;
		data[15] = 41455669;
		data[16] = 62806689;
		data[17] = 78061171;
		data[18] = 68091460;
		data[19] = 48108071;

		return true;
	}

	public boolean sort() {
		int i;
		int t;
		boolean b;
		b = this.qs(0, 19);
		i = 0;
		while (i < 10) {
			t = data[i];
			data[i] = data[19 - i];
			data[19 - i] = t;
			i = i + 1;
		}
		return false;
	}

	public int select_pivot(int left, int right) {
		boolean l;
		l = true;
		while (left < right) {
			if (l) {
				left = left + 1;
			} else {
				right = right - 1;
			}

			l = !l;
		}

		return left;
	}

	public boolean qs(int left, int right) {
		int pivot;
		boolean b;
		if (left < right) {
			pivot = this.select_pivot(left, right);
			pivot = this.partition(left, right, pivot);
			b = this.qs(left, pivot - 1);
			b = this.qs(pivot + 1, right);
		} else {}

		return true;
	}

	public int partition(int left, int right, int pivot) {
		int v;
		int idx;
		int i;
		int t;

		v = data[pivot];
		data[pivot] = data[right];
		data[right] = v;

		idx = left;

		i = left;

		while (i < right) {
			if (data[i] < v) {
				t = data[i];
				data[i] = data[idx];
				data[idx] = t;
				idx = idx + 1;
			} else {}
			i = i + 1;
		}

		t = data[idx];
		data[idx] = data[right];
		data[right] = t;

		return idx;
	}

	public boolean print() {
		int i;
		i = 0;
		while (i < 20) {
			System.out.println(data[i]);
			i = i + 1;
		}
		return true;
	}
}
