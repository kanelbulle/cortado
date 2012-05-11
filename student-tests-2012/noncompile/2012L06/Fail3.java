class Fail3
{
	public static void main(String[] args) {
		int[] arr;
		boolean meep;

		arr = new int[17];

		meep = false;

		System.out.println(arr[meep && meep]);
	}
}
