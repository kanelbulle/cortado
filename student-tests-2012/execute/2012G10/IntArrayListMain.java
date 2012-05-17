class IntArrayListMain {
	public static void main(String[] args) {
		IntArrayList intList;
		int i;
		int makeThisAStmt;

		intList = new IntArrayList();
		makeThisAStmt = intList.init(2);

		makeThisAStmt = intList.add(53);
		makeThisAStmt = intList.add(4563);
		makeThisAStmt = intList.add(456);
		makeThisAStmt = intList.add(347);
		makeThisAStmt = intList.add(56);
		makeThisAStmt = intList.add(4563);
		makeThisAStmt = intList.add(0);
		makeThisAStmt = intList.add(0 - 342587);
		makeThisAStmt = intList.add(85854);
		makeThisAStmt = intList.add(35747457);

		i = 0;
		while(i < intList.size()) {
			System.out.println(intList.get(i));

			i = i + 1;
		}
	}
}

/**
 * A fast auto-resizing list of ints that avoids the auto-boxing and unboxing of the java Collections Lists.
 */
class IntArrayList {
	int[] intArr;
	int nextFreePos;

	public int init(int size) {
		intArr = new int[size];

		return 0;
	}

	public int get(int index) {
		return intArr[index];
	}

	public int add(int value) {
		int whatever;

		if(intArr.length < nextFreePos) {
			whatever = this.resize();
		} else {
		}

		if(!(intArr.length < nextFreePos) && !(nextFreePos < intArr.length)) {
			whatever = this.resize();
		} else {
		}

		intArr[nextFreePos] = value;

		nextFreePos = nextFreePos + 1;

		return 0;
	}

	public int resize() {
		int[] newArr;
		int i;

		newArr = new int[intArr.length * 2];

		i = 0;
		while(i < intArr.length) {
			newArr[i] = intArr[i];
			i = i + 1;
		}

		intArr = newArr;

		return 0;
	}

	public int size() {
		return nextFreePos;
	}
}
