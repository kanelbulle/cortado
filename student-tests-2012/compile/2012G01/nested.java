
class Nested {

	public static void main(String[] GoodGuy) {
		
	}
	
}

// Test long class names
class ThisIsTheEpicBossClassIfYouFeelThatYouAreOneOfTheStrongYouBetterBePreparedThisIsGoingToBeTheBattleOfALifeTimeYouHaveNoIdeaWhatYouAreUpAgainst

{
	int Vile				;
	int FlameMammoth		;
	int ChillPenguin 		;
	int SparkMandrill		;
	int ArmoredArmadillo	;
	int LaunchOctopus		;
	int BoomerKuwanger		;
	int StingChameleon		;
	int StormEagle			;
	int MaverickSpider		;
	int RangdaBangda		;
	int MaverickTank		;
	int Velgauder			;
	
	// Test possible out of bounds for &&
	public boolean x(int i) {
		return  this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) &&
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1) && this.x(1) && this.x(1) && this.x(1) && 
				this.x(1) && this.x(1);	
	}
	
	// Test empty FormalList
	// public int x() {
		// return 1;
	// }
	
	// Test forward declaration
	public int x1(int j) {
		return this.x2(1);
	}
	
	// Test backward declaration
	public int x2(int j) {
		return this.x1(1);
	}
	
	// Test long FormalList
	public int x3(
	int x, int x1, int x2, int x3, int x4, int x5, int x6, int x7, int x8, 
	int x9, int x10, int x11, int x12, int x13, int x14, int x15, int x16, 
	int x17, int x18, int x19, int x20, int x21, int x22, int x23, int x24, 
	int x25, int x26, int x27, int x28, int x29, int x30, int x31, int x32 ) {
		return x;
	}


}