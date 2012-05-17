// EXT:BDJ
// EXT:IWE

class BDJ {
    public static void main(String[] args) {

    	Methods m;

    	m = new Methods();

    	if(m.print(1,false) || m.print(2,true) && m.print(3,true)){

		}

		if(m.print(4,false) && m.print(1000,true) || m.print(5,true)){ //if || doesn't bind looser then we won't evaluate print(5,true)

		}
    }
}

class Methods{
	public boolean print(int i, boolean b){
		System.out.println(i);
		return b;
	}
}
