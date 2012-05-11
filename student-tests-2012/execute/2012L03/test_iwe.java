// EXT:IWE

class IWE {
    public static void main(String[] args) {

        if(true)
		if(false)
			System.out.println(false);
		else
			System.out.println(0);

		if(false)
		if(false)
			System.out.println(false);
		else
			System.out.println(false);

		if(true)
    		if(true)
    			if(true)
    				if(true){
    					if(true)
    						if(true)
    							if(true) {}
    							else {}
    						else {}
    				} else {}
    			else {}
    }
}
