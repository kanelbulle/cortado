class Probability{
    public static void main(String[] a){
	System.out.println(new Prob().ComputeProb(12, 50));
	System.out.println(new Prob().NumTries(12, 50));
    }
}
//If there are errors with if/else extension, all probabilities will be 1
//Comment //Comment
class Prob {

    public long ComputeProb(int nums, int choices){
	long aux;
	if (1 < 2)
		if (100 < 100)
			return 99999;
		else
			if (nums < 1)
				aux = 1;
				if (choices < 1)
					aux = 1;
				else
					aux = (nums/choices) * this.ComputeProb(nums-1, choices-1);
	return aux;
    }

	public long NumTries(int nums, int choices){
	long aux;
	long auxProb;
	boolean a;
	auxProb = this.doubleCall(this.callTest());
	a=false;
	while (a)
		a=true;
	if (1000<999)
	{
		if (0 < 1)
		{}
	}
	else
	{
		if (nums < 1)
			aux = 1;
		else
		{
			if (choices < 1)
				aux = 1;
			else
				auxProb = (((((((choices/nums)))))));
				aux = this.Multiply(auxProb, this.NumTries(nums-1, choices-1));
		}
	}
	return aux;
    }

	public long Multiply(long num1, long num2){
	long result;
	result = this.callTest();
	while (0 < num1){
		result = result + num2;
		num1 = num1 - 1;
	}
	return result;
	}

	public long callTest(){
		return this.callTest2();
	}

	public long callTest2(){
		return this.callTest3();
	}

	public long callTest3(){
		return this.callTest4();
	}

	public long callTest4(){
		return this.callTest5();
	}

	public long callTest5(){
		return this.callTest6();
	}

	public long callTest6(){
		return 0;
	}

	public long doubleCall(long num){
		if (num < this.callTest3())
			return 100;
		else
			return 0;
	}
}
