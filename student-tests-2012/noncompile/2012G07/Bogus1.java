
class Bogus1 {
	public static void main(String [] args){
		(new A()).a(); // ingen tildelning
	}
}

class A{

	public int a(){
		return 1;
	}
}
