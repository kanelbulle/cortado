class NoComp2 {
	public static void main(String[] a) {
		int i;
		int[] arr;
		int notsamevariablename;

		notsamevariablename = 99;
		notsamevariablename = 99;
		arr = new int[notsamevariablename];

		i = 0;
		while (i=1) { // error here
			arr[i] = i;
			i = i+1;
		}
	}
}
