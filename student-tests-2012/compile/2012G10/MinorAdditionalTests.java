class MinorAdditionalTests {
	public static void main(String[] args) {
		int[] intArr;
		boolean bool;
		int retVal;

		intArr = new int[(((3)))];

		intArr[0] = 17;
		intArr[1] = 18;
		intArr[2] = 19;

		intArr[(((((2)))))] = ((((intArr[(((2)))]))));

		intArr[0] = intArr[1];

		bool = !false;
		bool = !bool;
		bool = !!!!(1 < 2);
		bool = ! !false;
		bool = !(!(!(1 < 2 && 2 < 3)));

		retVal = new MethodParamsTest().aMethod(15, bool, intArr);
	}
}

class MethodParamsTest {
	public int aMethod(int a, boolean b, int[] c) {
		return 0;
	}
}
