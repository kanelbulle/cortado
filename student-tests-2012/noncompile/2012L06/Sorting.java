
class Sorting
{
	// Generate some sequence of integers and sort them by putting them in a binary tree and making an in-order traversal
	public static void main(String[] args)
	{

		Tree t;
		List sorted;
		Iterator it;
		Sorter s;

		int m; // Used to get some negative numbers
		int n; // Modulo divisor
		int i; // Counter and multiplier

		s = new Sorter();
		t = new Tree();

		m = 1;
		n = 27;
		i = 1000;
		System.out.println(-1);
		while (i < (10 * (10 * 10)) + (100*100))
		{
			t.insert(s.MOD((97 * i * s.MOD(i, n)), 9973) * m);
			m = m * (0 - 1);
			i = i + 1;
		}

		sorted = t.sorted();
		it = sorted.iterator();
		while (it.hasNext())
		{
			System.out.println(it.next().getValue());
		}
		System.out.println(-1);
	}
}

class Sorter
{
	public int DIV(int x, int y)
	{
		int q = 0;
		// int remainder = 0;
		while (y < x + 1)
		{
			x = x - y;
			q = q + 1;
		}
		// remainder = x - (y*q);
		return q;
	}

	public int MOD(int x, int y)
	{
		return x - (y * this.DIV(x, y));
	}
}

class Tree
{
	Node root;
	boolean hasRoot;
	int size;

	public boolean insert(int value)
	{
		Node f;

		if (!hasRoot)
		{
			f = new Node();
			root = f.create(value);
			size = 0;
			hasRoot = true;
		}
		else { size = size; }
		size = size + 1;
		return root.insert(value);
	}

	public List sorted()
	{
		List sorted;

		sorted = new List();
		//sorted.hasHead = false;
		root.inOrder(sorted);
		return sorted;
	}
}

class Node
{
	Node left;
	Node right;
	boolean hasLeft;
	boolean hasRight;
	int value;
	int count;

	public Node create(int v)
	{
		Node n;

		n = new Node();
		n.init(v);

		return n;
	}

	public boolean init(int v)
	{
		value = v;
		hasLeft = false;
		hasRight = false;
		count = 0;
		return true;
	}

	public boolean inOrder(List sorted)
	{
		int c;

		if (hasLeft)
		{
			left.inOrder(sorted);
		}
		else { c = 0; }
		c = 0;
		while (c < count)
		{
			sorted.add(value);
			c = c + 1;
		}
		if (hasRight)
		{
			right.inOrder(sorted);
		}
		else { c=c;	}
		return true;
	}

	public boolean setLeft(Node n)
	{

		if (!hasLeft)
		{
			left = n;
			hasLeft = true;
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean setRight(Node n)
	{

		if (!hasRight)
		{
			right = n;
			hasRight = true;
			return true;
		}
		else
		{
			return false;
		}
	}

	public int setValue(int i)
	{
		value = i;
		return value;
	}

	public int setCount(int i)
	{
		this.count = 0;
		return this.count;
	}

	public boolean insert(int value)
	{
		if (this.value < value)
		{
			return this.insertRight(value);
		}
		else
		{
			if (value < this.value)
			{
				return this.insertLeft(value);
			}
			else
			{
				count = count + 1;
				return true;
			}
		}
	}

	public boolean insertLeft(int value)
	{
		if (!hasLeft)
		{
			this.setLeft(this.create(value));
		}
		else
		{
			{
				{
					{
						{
							{
								{
									value = value;
								}
								{
								}
								{
								}
							}
						}
					}
				}
			}
		}
		return left.insert(value);
	}

	public boolean insertRight(int value)
	{
		if (!hasRight)
		{
			this.setRight(this.create(value));
		}
		else
		{
			{
				{
					{
						{
							{
								{
									value = value;
								}
								{
								}
							}
						}
					}
				}
			}
		}
		return right.insert(value);
	}
}

class List
{
	ListNode head;
	ListNode tail;
	boolean hasHead;
	int length;

	public int add(int value)
	{
		ListNode f;
		if (!hasHead)
		{
			f = new ListNode();
			head = f.create(value);
			tail = head;
			length = 1;
			hasHead = true;
		}
		else
		{
			tail.setNext(tail.create(value));
			tail = tail.getNext();
		}
		return length;
	}

	public Iterator iterator()
	{
		Iterator i;
		i = new Iterator();
		i.start(this.head);
		return i;
	}
}

class ListNode
{
	ListNode next;
	boolean hasNext;
	int value;

	public ListNode create(int val)
	{
		ListNode n;

		n = new ListNode();
		n.init(val);

		return n;
	}

	public boolean init(int v)
	{
		value = v;
		next = null;
		hasNext = false;
		return true;
	}

	public int getValue()
	{
		return value;
	}

	public int setValue(int v)
	{
		value = v;
		return v;
	}

	public boolean setNext(ListNode n)
	{
		next = n;
		hasNext = true;
		return true;
	}

	public ListNode getNext()
	{
		return next;
	}

	public boolean hasNext()
	{
		return hasNext;
	}
}

class Iterator
{
	ListNode next;
	boolean hasNext;

	public boolean start(ListNode start)
	{
		next = start;
		hasNext = true;
		return true;
	}

	public boolean hasNext()
	{
		return hasNext;
	}

	public ListNode next()
	{
		ListNode n;
		n = next;
		hasNext = next.hasNext();
		next = next.getNext();
		return n;
	}
}
