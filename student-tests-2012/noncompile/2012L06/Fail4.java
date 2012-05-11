class Fail4
{
	public static void main(String[] args) {
		int[] arr;
		T t;

		arr = new int[24];
		t = new T();
		t.setLength(7);

		// All good, I suppose
		System.out.println(arr.length);

		// Dot operator for member access is not
		// supported, so this yields a compiler error
		System.out.println(t.length);
	}
}

class T
{
	int length;

	public T setLength(int l)
	{
		length = l;
		return this;
	}
}
