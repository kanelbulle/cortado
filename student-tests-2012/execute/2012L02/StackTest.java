class StackTest {
	public static void main(String[] args) {
		Stack s;
		int i;
		int slask;
		s = new Stack();
		slask = s.setSize(100);
		i = 0;
		while(i < 100)
		{
			slask = s.push(i);
			i = i + 1;
		}

		while(!s.isEmpty())
		{
			System.out.println(s.pop());
		}
	}
}

class Stack {
	int[] space;
	int used;
	public int setSize(int size) {
		used = 0;
		space = new int[size];
		return 0;
	}
	public int push(int i)
	{
		space[used] = i;
		used = used + 1;
		return 0;
	}
	public int pop()
	{
		used = used - 1;
		return space[used];
	}
	public boolean isEmpty(){
		return used < 1;
	}
}
