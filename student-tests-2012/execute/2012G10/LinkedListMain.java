class LinkedListMain {
	public static void main(String[] args) {
		LinkedList list;
		int i;
		int retVal;

		list = new LinkedList();
		list = list.init();
		
		list = list.addLast(42);
		list = list.addLast(235 - 48923);
		list = list.addLast(4711);
		list = list.addLast(1337);
		list = list.addLast(2147483647);
		list = list.addLast(0);
		list = list.addLast(1);
		list = list.addLast(0-1);
		list = list.addLast(4 * 4 * 4 * 4);
        
		i = 0;
		while(i < list.size()) {
			System.out.println(list.get(i));
			i = i + 1;
		}
		
		System.out.println(list.size());
		System.out.println(list.isEmpty());
		
		while(0 < list.size()) {
			retVal = list.removeFirst();
			System.out.println(retVal);
			System.out.println(list.size());
			System.out.println(list.isEmpty());
		}
		
		retVal = list.removeFirst();
		System.out.println(retVal);
		System.out.println(list.size());
		System.out.println(list.isEmpty());
		
		retVal = list.removeFirst();
		System.out.println(retVal);
		System.out.println(list.size());
		System.out.println(list.isEmpty());
	}
}

class ListElement {
	int data;
	ListElement next;

	public ListElement init(int newData) {
		data = newData;

		return this;
	}
	
	public int getData() {
		return data;
	}
	
	public ListElement setData(int newData) {
		data = newData;
		
		return this;
	}
	
	public ListElement getNext() {
		return next;
	}
	
	public ListElement setNext(ListElement newNext) {
		next = newNext;
		
		return this;
	}
}

class LinkedList {
	ListElement first;
	ListElement last;
	int size;
	int nullValue;
	ListElement nullElement;
	LinkedList returnVal;
	
	public LinkedList init() {
		nullValue = 0 - 2147483647 - 1;
		nullElement = new ListElement();
		nullElement = nullElement.init(nullValue);
		
		return this;
	}

	/**
	 * Inserts the given element at the beginning of this list.
	 * Worst case running time: O(1)
	 */
	public LinkedList addFirst(int element) {
		ListElement temp;
		
		if(!(0 < size) && !(size < 0)) {
			first = new ListElement().init(element);
			last = first;
		} else {
			temp = first;
			first = new ListElement().init(element);
			first = first.setNext(temp);
		}
		size = size + 1;
		
		return this;
	}

	/**
	 * Inserts the given element at the end of this list.
	 * Worst case running time: O(1)
	 */
	public LinkedList addLast(int element) {
		ListElement temp;
		
		if(!(0 < size) && !(size < 0)) {
			returnVal = this.addFirst(element);
		} else {
			temp = last;
			last = new ListElement().init(element);
			temp = temp.setNext(last);
			size = size + 1;
		}
		
		return this;
	}

	/**
	 * Returns the first element of this list.
	 * Returns <code>nullValue</code> if the list is empty.
	 * Worst case running time: O(1)
	 */
	public int getFirst() {
		int retVal;
		
		if(!(0 < size) && !(size < 0)) {
			retVal = nullValue;
		} else {
			retVal = first.getData();
		}
		
		return retVal;
	}

	/**
	 * Returns the last element of this list.
	 * Returns <code>nullValue</code> if the list is empty.
	 * Worst case running time: O(1)
	 */
	public int getLast() {
		int retVal;
		
		if(!(0 < size) && !(size < 0)) {
			retVal = nullValue;
		} else {
			retVal = last.getData();
		}
		
		return retVal;
	}

	/**
	 * Returns the element at the specified position in this list.
	 * Returns <code>nullValue</code> if <code>index</code> is out of bounds.
	 * The first element in the list has index 0.
	 * Worst case running time: O(n)
	 */
	public int get(int index) {
		int retVal;
		ListElement element;
		int i;
		
		if(index < 0) {
			retVal = nullValue;
		} else {
			if(size < index) {
				retVal = nullValue;
			} else {
				element = first;
				i = 0;
				while(i < index) {
					element = element.getNext();
					i = i + 1;
				}
				retVal = element.getData();
			}
		}
		
		return retVal;
	}

	/**
	 * Removes and returns the first element from this list.
	 * Returns <code>nullValue</code> if the list is empty.
	 * Worst case running time: O(1)
	 */
	public int removeFirst() {
		int retVal;
		ListElement temp;
		
		if(!(0 < size) && !(size < 0)) {
			retVal = nullValue;
		} else {
			temp = first;
			first = first.getNext();
			if(!(1 < size) && !(size < 1)) {
				last = nullElement;
			} else {
			}
			size = size - 1;
			
			retVal = temp.getData();
		}
		
		return retVal;
	}

	/**
	 * Returns the number of elements in this list.
	 * Worst case running time: O(1)
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns <code>true</code> if this list contains no elements.
	 * Worst case running time: O(1)
	 */
	public boolean isEmpty() {
		return (!(0 < size) && !(size < 0));
	}
}
