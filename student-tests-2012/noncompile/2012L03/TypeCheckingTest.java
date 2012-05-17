/*Minijava does not support method overloading*/
class TypeCheckingTest {

    public static void main(String[] args) {
    		Methods m;
    		Method n; //fail here
    		boolean b;
    		m = new Method();


    		n = m.a();//fail here

    		if(true<1){//fail here

    		}

    		if(true<true){//fail here

    		}

    		m = true + true;//fail here
    		m = true - false;//fail here
    		m = false * false;//fail here
    		m = 1 && m;//fail here
    		b = !m;//fail here
    		b = 1 < m;//fail here
    		b = m < b;//fail here
    		b = m < 1;//fail here
    		b = b < m;//fail here
    }
}

class Methods{

	int a;

	public int a(){
		return true;//fail here
	}

	public boolean b(){
		return a;//fail here
	}

	public Method c(){//fail here
		return this;//fail here
	}
}
