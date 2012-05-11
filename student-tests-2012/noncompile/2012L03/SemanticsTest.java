/*Minijava does not support method overloading*/
class SemanticsTest {

    public static void main2(String[] args) {

    	int a;
    	boolean a; //fail here
    }
}

class Methods {

	int a;
	int a;//fail here
	int b;

	public int a(boolean a){
		Methods a;//fail here
		return a;
	}

	public int a(){//fail here
		return 0;
	}
}

class Methods {//fail here


}
