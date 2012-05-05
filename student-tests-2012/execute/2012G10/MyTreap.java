// EXT:INT32

class MyTreap {
	public static void main(String[] args) {
		System.out.println(new Treap().test());
	}
}

class Treap {
	TreapNode root;
	MyTreap notVeryUseful;
	boolean isEmpty;
	int seed;

	public int test() {
		boolean dontCare;
		System.out.println(this.init());

		dontCare = this.insert(31);
		dontCare = this.insert(37);

		dontCare = this.insert(23);
		dontCare = this.insert(29);

		dontCare = this.visit();
		System.out.println(this.find(23));
		System.out.println(this.find(11));

		dontCare = this.insert(11);
		dontCare = this.insert(13);
		dontCare = this.insert(17);
		dontCare = this.insert(19);

		dontCare = this.insert(2);
		dontCare = this.insert(3);
		dontCare = this.insert(5);
		dontCare = this.insert(7);

		dontCare = this.insert(41);
		dontCare = this.insert(43);
		dontCare = this.insert(47);
		dontCare = this.insert(47);

		dontCare = this.visit();
		System.out.println(this.find(23));
		System.out.println(this.find(11));
		System.out.println(this.find(47));
		System.out.println(this.find(0 - 1));

		return 0;
	}

	public boolean visit() {
		boolean dontCare;
		if (!isEmpty) {
			dontCare = root.visit();
		} else {
		}
		return true;
	}

	public boolean init() {
		notVeryUseful = new MyTreap();
		isEmpty = true;
		seed = 1337;
		return true;
	}

	public boolean insert(int number) {
		TreapNode newNode;
		int rand;
		newNode = new TreapNode();
		rand = this.nextRandom();
		newNode = newNode.init(number, rand);
		if (isEmpty) {
			isEmpty = false;
			root = newNode;
		} else
			root = root.insert(newNode);
		return true;
	}

	public boolean find(int number) {
		boolean found;
		boolean done;
		TreapNode current;
		found = false;
		done = false;
		if (isEmpty) {
		} else {
			found = root.find(number);
		}
		return found;
	}

	public int nextRandom() {
		seed = seed * 10320 + 50612;
		return seed;
	}
}

class TreapNode {
	int number;
	int prio;
	TreapNode left;
	boolean hasLeft;
	TreapNode right;
	boolean hasRight;

	public TreapNode insert(TreapNode n) {
		TreapNode root;
		root = this;
		if (n.getNumber() < number) {
			// Insert in left subtree
			if (!hasLeft) {
				hasLeft = true;
				left = n;
			} else
				left = left.insert(n);
			// Rotate to satisfy heap condition
			if (prio < left.getPrio())
				root = this.rotateRight();
			else {
				// Already a heap, do nothing
			}
		} else {
			if (number < n.getNumber()) {
				// Insert in right subtree
				if (!hasRight) {
					hasRight = true;
					right = n;
				} else
					right = right.insert(n);
				// Rotate to satisfy heap condition
				if (prio < right.getPrio())
					root = this.rotateLeft();
				else {
					// Already a heap, do nothing
				}
			} else {
				// Already in tree, do nothing
			}
		}
		return root;
	}

	public boolean find(int n) {
		boolean found;
		found = false;
		if (n < number) {
			if (hasLeft)
				found = left.find(n);
			else {
			}
		} else {
			if (number < n) {
				if (hasRight)
					found = right.find(n);
				else {
				}
			} else {
				found = true;
			}
		}

		// TODO Auto-generated method stub
		return found;
	}

	public boolean visit() {
		boolean dontCare;
		System.out.println(number);
		if (hasLeft) {
			dontCare = left.visit();
		} else {
		}
		if (hasRight) {
			dontCare = right.visit();
		} else {
		}
		return true;
	}

	/**
	 * Rotates left.
	 * 
	 * @return the new root of the subtree.
	 */
	public TreapNode rotateLeft() {
		TreapNode root;
		TreapNode dontCare;
		root = right;
		if (right.hasLeft())
			right = right.getLeft();
		else
			hasRight = false;
		dontCare = root.setLeft(this);
		return root;
	}

	/**
	 * Rotates right.
	 * 
	 * @return the new root of the subtree.
	 */
	public TreapNode rotateRight() {
		TreapNode root;
		TreapNode dontCare;
		root = left;
		if (left.hasRight())
			left = left.getRight();
		else
			hasLeft = false;
		dontCare = root.setRight(this);
		return root;
	}

	public TreapNode init(int n, int p) {
		number = n;
		prio = p;
		hasLeft = false;
		hasRight = false;
		return this;
	}

	public boolean hasLeft() {
		return hasLeft;
	}

	public boolean hasRight() {
		return hasRight;
	}

	public int getNumber() {
		return number;
	}

	public int getPrio() {
		return prio;
	}

	public TreapNode getLeft() {
		return left;
	}

	public TreapNode setLeft(TreapNode l) {
		left = l;
		hasLeft = true;
		return left;
	}

	public TreapNode getRight() {
		return right;
	}

	public TreapNode setRight(TreapNode r) {
		right = r;
		hasRight = true;
		return right;
	}
}
// Evil comment