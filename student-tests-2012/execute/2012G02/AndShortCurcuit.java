class AndShortCurcuit{
	public static void main(String[] args){
		int ret;
		Test t;
		t = new Test();
		ret = t.set(1);

		if(false && 1 < t.set(2)){
		}
		else{
		}

		ret = t.print(1);
	}
}

class Test{
	int value;
	public int set(int v){
		value = v;
		return 0;
	}

	public int print(int v){
		System.out.println(value);
		return 0;
	}
}
