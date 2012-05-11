class MethodTest{
	public static void main(String[] args){
	Test2 t1;
	Test2 t2;
	int k;

	t1 = new Test2();
	t2 = new Test2();

	k = t2.setJ(5);
	k = t1.testMethod(t2, 1);
	}
}

class Test2{
	int j;
	public int setJ(int v){
		j = v;
		return j;
	}
	public int getJ(int v){
		return j;
	}
	public int testMethod(Test2 t, int i){
		System.out.println(t.getJ(0));
		System.out.println(i);
		return 0;
	}
}
