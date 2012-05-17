class NoComp {
	public static void main(String[] a) {
		int i;
		int o;
		int sideA;
		int sideB;
		int squareA;
		int squareB;
		int rectAB;
		squareA = 0;
		squareB = 0;
		rectAB = 0;

		o = 1 = 2;  // error here
		i = 10;
		while (1<i) {
			o = i;
			i = i+1;
		}
		if (o = 1){
		System.out.println(o+o-(o+42)*0);
		}
		i = 1+2+3+4+5	//error here

		if (0 < sideA){
			o = i; //save value
			i = sideA;
			while (0 < i){
				squareA = squareA + sideA;
				i = i-1;
			}
			if (0 < sideB){
				i = sideB;
				while (0 < i){
					squareB = squareB + sideB;
					i = i-1;
				}
				i = sideA;
				while (0 < i){
					rectAB = rectAB + sideB;
					i = i-1;
				}
			}
			else{
			squareB = 0;
			rectAB = 0;
			}
		}
		else{
		squareA = 0;
		}
		i = o;   //recover value
	}
}
