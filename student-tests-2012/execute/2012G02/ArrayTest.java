class ArrayTest{
	public static void main(String[] args){
		int[] a;
		int i;
		a = new int[5];

		i = 0;
		while(i<a.length){
			a[i] = i;
			i = i + 1;
		}

		i = 0;
		while(i<a.length){
			System.out.println(a[i]);
			i = i + 1;
		}
	}
}
