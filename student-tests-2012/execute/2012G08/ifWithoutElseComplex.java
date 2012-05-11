// EXT:IWE
class Foo {

	public static void main(String[] args) {

			boolean a;
			boolean b;
			boolean c;

			a = true;
			b = false;
			c = true;

			if(a) {
				System.out.println(1);
			}

			System.out.println(2);

			if(b)
				if(c)
					System.out.println(93);
				else
					System.out.println(94);
			else
					System.out.println(3);

			if(a) {
				if(b)
					System.out.println(112);
			} else {
				System.out.println(4711);
			}

			if(a)
				if(b)
					System.out.println(93);
				else
					System.out.println(4);

			if(b)
				System.out.println(890);
			System.out.println(5);
	}

}
