// EXT:CLE
// EXT:CGT
// EXT:CGE
// EXT:CEQ
// EXT:CNE
// EXT:BDJ
// EXT:NBD
// EXT:ABC
// EXT:IWE
// EXT:LONG
// EXT:ICG
// EXT:ISC

class Full{
	public static void main(String [] args){
		B b;
		int a;
		int ret;
		b = new B();

		ret = b.set(0);
		ret = b.print();
		ret = b.foo();
		{
			ExtendsTest et;
			et = new ExtendsTest();

			ret = et.Test();
		}
		{
			long l;
			l= 9223372036854775807L;
			System.out.println(l+1);
			if (l <= 123){
				System.out.println(1);
			}
		}
		System.out.println((new Scope()).f());
		System.out.println(0);
		{
			CsToEconomy cse;
			int asdasd;
			cse = new CsToEconomy();
			asdasd = cse.init(7000);
			asdasd = cse.badSolve();
			System.out.println(asdasd);
		}
	}
}

class Scope {
	int a;
	public int f() {
		a = 1;
		System.out.println(a);
		{
		    int a;
		    a = 2;
			System.out.println(a);
		}
		System.out.println(a);
		return a;
	}
}

class ExtendsTest{

	public int ExtendsTestMethod(B a){
		int ret;

		ret = a.print();
		{
			int ab;
			ab= 9898000;
			ret = ab;

		}

		return ret;
	}

	public int Test(){
		B b;
		int ret;
		b = new B();
		ret = b.set(6789);
		if(1 < 2 && 2 > 0 && 1==1 && 0L!=1L && 0<=1634L && 1>=0 || true)
			ret = this.ExtendsTestMethod(b);
		System.out.println(ret);
		return 1999;
	}
}

class A{
	int a;
	int b;
	public int print(){
		System.out.println(a);
		return 0;
	}
	public int set(int x){
		a=x;
		return 0;
	}

}

class B extends A{

	public int foo(){
		int ret;
		ret=1000;
		System.out.println(ret);
		return ret;
	}
}


class CsToEconomy{
	int [] arrCS;
	int [] arrEC;
	int students;
	public int init(int numStudents){
		int i;
		i=0;
		students = numStudents;
		arrCS=new int[students];
		arrEC=new int[students];
		while(i< students){
			int iqcs;
			int iqec;
			iqcs = i*i*i*i*i;
			iqec = i*i*i*i*i*i;
			if(iqcs < 0)
				iqcs=i;
			if(iqec < 0)
				iqec=i;
			arrCS[i]=iqcs;
			arrEC[i]=iqec;
			i=i+1;
		}
		return 0;
	}

	public int badSolve(){
		int i;
		int sumcs;
		int sumec;
		int cnt;
		i=0;
		sumcs=0;

		while(i<students){
			sumcs= sumcs+ arrCS[i];
			i=i+1;
		}
		i=0;
		cnt=0;
		while(i<students){
			int j;
			j=0;
			sumec=0;
			while(j<students){
				sumec= sumec+ arrEC[i];
				j=j+1;
			}
			if(arrCS[i]*students < sumcs && arrCS[i]*students < sumec)
				cnt = cnt +1;
			i=i+1;

		}
		return cnt;
	}

}
