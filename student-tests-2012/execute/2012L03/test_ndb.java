// EXT:NBD

class NDB {
    public static void main(String[] args) {

    	int a;

		a = 1;

		System.out.println(a);

		{
			int b;
			b = 2;
			a = b;
		}

		System.out.println(a);
    }
}
