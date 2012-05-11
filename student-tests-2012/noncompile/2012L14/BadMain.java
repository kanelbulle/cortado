/*
* Mini Java Test-case
* Id:3
* Author: Pedro de Carvalho Gomes <pedrodcg@csc.kth.se>
*
* This case exploits the fact that the only method in
* the MainClass is main itself.
* The other should cause error.
*/

// EXT:CGE
// EXT:CLE
// EXT:CNE
// EXT:BDJ

class BadMain {
   public static void main (String [ ] argv) {
      int a;
      BadMain myobj;

      myobj = new BadMain();

      if (argv.length < 0) { a = argv.length * (0 - 1); } else a = argv.length;


      while ( myobj.checkCond(a)) {
         System.out.println( argv[a]);
         a = a - 1;
      }

   }

   public boolean checkCond(int num) {
        boolean myret;
	if (  (num >= 0) || (num <= 10) && (num != 5) ) {
           myret = true;
        } else {
           myret = false;
        }
        return myret;
   }

}
