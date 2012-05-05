class Operators{
	public static void main(String[] args){
		int i;
		int j;
		int res;
		
		boolean a;
		boolean b;
		boolean c;

		j = 10;
		i = 5;
		
		a = true;
		b = false;
		c = true;
		
		//Test addition
		System.out.println(i);
		System.out.println(j);
		res = i + j;
		System.out.println(res);
		
		System.out.println(5);
		System.out.println(2);
		res = 5 + 2;
		System.out.println(res);
		
		System.out.println(i);
		System.out.println(2);
		res = i + 2;
		System.out.println(res);
		
		//Test subtraction
		System.out.println(i);
		System.out.println(j);
		res = i - j;
		System.out.println(res);
		
		System.out.println(5);
		System.out.println(2);
		res = 5 - 2;
		System.out.println(res);
		
		System.out.println(i);
		System.out.println(2);
		res = i - 2;
		System.out.println(res);
		
		//Test multiplication
		System.out.println(i);
		System.out.println(j);
		res = i * j;
		System.out.println(res);
		
		System.out.println(5);
		System.out.println(2);
		res = 5 * 2;
		System.out.println(res);
		
		System.out.println(i);
		System.out.println(2);
		res = i * 2;
		System.out.println(res);
		
		//Test and
		if(a && b){
			System.out.println(b);
		}
		else{
			System.out.println(a);
		}
		
		if(a && c){
			System.out.println(c);
		}
		else{
			System.out.println(b);
		}
		
		//Test less then
		if(i < j){
			System.out.println(i);
		}
		else{
			System.out.println(j);
		}
		
		if(j < i){
			System.out.println(j);
		}
		else{
			System.out.println(i);
		}
		//Test combinations of less then + and 
		
		if(i < j && a){
			System.out.println(i);
		}
		else{
			System.out.println(j);
		}
		
		if(j < i && b){
			System.out.println(j);
		}
		else{
			System.out.println(i);
		}
		
		if(i < j && b){
			System.out.println(i);
		}
		else{
			System.out.println(j);
		}
		
		if(j < i && a){
			System.out.println(j);
		}
		else{
			System.out.println(i);
		}
		
		// Test NOT 
		
		if(!b){
			System.out.println(!b);
		}
		else{
			System.out.println(b);
		}
		
		if(i < j && !b && !(10 < 5)){
			System.out.println(i);
		}
		else{
			System.out.println(j);
		}
		
		
	}
}