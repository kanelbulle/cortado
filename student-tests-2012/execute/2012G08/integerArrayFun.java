class Foo {

	public static void main(String[] args) {

			int[] arr;
			int i;

			arr = new int[100];
			i = 0;

			while(i < arr.length) {
				arr[i] = i;
				i = i + 1;
			}

			i = 0;
			while(i < arr.length) {
				System.out.println(arr[i]);
				i = i + 1;
			}

	}

}
