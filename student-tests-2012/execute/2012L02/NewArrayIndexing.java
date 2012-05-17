// Tests that you can index newly-created int vectors.
// This could trigger an error depending on how the
// compiler rejects "new int[][]".

class NewArrayIndexing {
	public static void main(String[] args) {
		System.out.println((new int[1])[0]);
	}
}
