class Sort {
	public static void main(String[] id) {
		QuickSort qs;
		Data data;
		int[] array;
		int i;
 		
		qs = new QuickSort();
		data = new Data();
		
		array = data.getData();
		array = qs.sort(array);
		
		i = 0;
		while (i < array.length) {
			System.out.println(array[i]);
			i = i + 1;
		}
	}
}

class QuickSort {
	
	public int[] sort(int[] array) {
		quicksort(array, 0, array.length - 1);
		
		return array;
	}
	
	public int quicksort(int[] v, int first, int last) {
		int pivot;
		int[] newPivotRange;
		
		if (first < last) {
			// poorly chosen pivot
			pivot = v[first];
			newPivotRange = this.partition(v, first, last, pivot);
			
			this.quicksort(v, first, newPivotRange[0] - 1);
			this.quicksort(v, newPivotRange[1] + 1, last);
		}
		
		return 0;
	}
	
	public int[] partition(int v[], int first, int last, int pivot) {
		int low = first;
		int mid = first;
		int high = last;
		int a;
		int ret[];
		
		while (mid < high + 1) {
			a = v[mid];
			if (a < pivot) {
				v[mid] = v[low];
				v[low] = a;
				low = low + 1;
				mid = mid + 1;
			} else {
				
			}
			if (a < (pivot + 1) && pivot < (a + 1)) {
				mid = mid + 1;
			} else {
				
			}
			if (pivot < a) {
				v[mid] = v[high];
				v[high] = a;
				high = high - 1;
			} else {
				
			}
		}
		
		ret = new int[2];
		ret[0] = low;
		ret[1] = high;
		
		return ret;
	}
}


class Data {
	public int[] getData() {
		int[] a;a = new int[50];a[0]=53860;a[1]=25467;a[2]=59253;a[3]=44577;a[4]=32382;a[5]=8670;a[6]=35386;a[7]=14434;a[8]=39257;a[9]=9519;a[10]=64979;a[11]=9042;a[12]=28817;a[13]=9918;a[14]=40917;a[15]=21949;a[16]=4554;a[17]=28998;a[18]=9182;a[19]=3312;a[20]=22232;a[21]=14209;a[22]=8221;a[23]=15334;a[24]=61352;a[25]=8805;a[26]=27149;a[27]=13983;a[28]=51970;a[29]=41975;a[30]=22369;a[31]=31340;a[32]=14098;a[33]=42817;a[34]=1648;a[35]=40974;a[36]=11535;a[37]=29889;a[38]=27444;a[39]=49485;a[40]=27235;a[41]=58266;a[42]=47626;a[43]=33159;a[44]=2789;a[45]=47576;a[46]=2863;a[47]=51140;a[48]=47275;a[49]=7936;return a;
	}
}
