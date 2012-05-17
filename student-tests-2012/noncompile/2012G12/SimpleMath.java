class SimpleMath {
	public static void main(String[] a) {
		int i;
		int theMeaningOfLifeTheUniverseAndEverything;
		int theNumberOfHornsOnAUnicorn;

		theMeaningOfLifeTheUniverseAndEverything = 42;

		i = 0;
		while (i < theMeaningOfLifeTheUniverseAndEverything) {
			i=i+1;
		}

		theNumberOfHornsOnAUnicorn = i*0+1;

		System.out.println(theNumberOfHornsOnAUnicorn*theMeaningOfLifeTheUniverseAndEverything);
	}
}

	class Math{
		public int square(int x){
			int result;
			result = x*x;
			return result;
		}

		public int cube(int x){
			int result;
			result = x*x*x;
			return result;
		}

		public int fac(int x){
			int result;
			result = 1;
			while(1<x){
				result = result*x;
				x=x-1;
			}
			return result;
		}

		public int sigma(int x){
			if(0<x){
				return 1;
			}else{
				if(x<0){
					return -1;
				}else{
					return 0; // x==0
				}
			}
		}

		public boolean xor(boolean a, boolean b){
			if(a&&!b){
				return true;
			}
			else{
				///nothing
			}
			if(!a&&b){
				return true;
			}
			else{
				return false;
			}
		}

		public boolean greaterThan(int a, int b){
			if(a<b){
				return false;
			}
			else{
				return true;
			}
		}

		public boolean or(boolean a, boolean b){
			if(a){
				return true;
			}
			else{
				//nothing
			}
			if(b){
				return true;
			}
			else{
				return false;
			}
		}

		public int pythagoras(int a, int b){
			int c;
			c = this.square(a) + this.square(b);
			return c;
		}

		public int powersOf10(int a, int power){
			while(0<power){
				a = a*10;
				power = power-1;
			}
			return a;
		}

		public int power(int a, int power){
			while(0<power){
				a = a*a;
				power = power-1;
			}
			return a;
		}

		public int areaRectangle(int base, int height){
			return base * height;
		}

		public int circumferenceRectangle(int base, int height){
			return base + base + height + height;
		}



	}
