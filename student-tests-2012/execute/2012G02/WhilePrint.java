class WhilePrint{
	public static void main(String[] args){
		int i;
		int j;
		boolean a;
		boolean b;

		i = 0;
		j = 0;
		a = true;
		b = false;

		while(i < 10){
			System.out.println(i);
			i = i + 1;
		}

		while(i < 100 && j < 20 && a){
			System.out.println(i);
			System.out.println(j);
			i = i + 1;
			j = j + 2;
		}
	}
}
