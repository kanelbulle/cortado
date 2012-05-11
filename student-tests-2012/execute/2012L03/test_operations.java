// EXT:CLE
// EXT:CGT
// EXT:CGE
// EXT:CEQ
// EXT:CNE
// EXT:IWE

class Operations {
    public static void main(String[] args) {

    	int greaterNumber;
        int lesserNumber;

		greaterNumber = 2000000;
        lesserNumber = 0;

        if(lesserNumber<=greaterNumber&&lesserNumber<=lesserNumber){
        	System.out.println(8);
        }

        if(greaterNumber>lesserNumber){
        	System.out.println(9);
        }

        if(greaterNumber>=lesserNumber&&lesserNumber>=lesserNumber){
        	System.out.println(10);
        }

        if(lesserNumber==lesserNumber){
        	System.out.println(11);
        }

        if(lesserNumber!=greaterNumber){
        	System.out.println(12);
        }

        if(greaterNumber<=lesserNumber){
        	System.out.println(13);
        }

        if(lesserNumber>greaterNumber){
        	System.out.println(14);
        }

        if(lesserNumber>=greaterNumber){
        	System.out.println(15);
        }

        if(greaterNumber==lesserNumber){
        	System.out.println(16);
        }

        if(lesserNumber!=lesserNumber){
        	System.out.println(17);
        }
    }
}
