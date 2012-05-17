// EXT:ABC
class ArrayOutOfBounds {
	public static void main(String[] a) {
		int i;
		int[] arr;
		int theMeaningOfLifeTheUniverseAndEverything;

		theMeaningOfLifeTheUniverseAndEverything = 42;
		arr = new int[theMeaningOfLifeTheUniverseAndEverything];

		i = 0;
		while (i < (arr.length + 1)) {
			arr[i] = i; // error here
			i = i+1;
		}
	}
}
