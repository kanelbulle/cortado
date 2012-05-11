/*
* Mini Java Test-case
* Id:6
* Author: Pedro de Carvalho Gomes <pedrodcg@csc.kth.se>
*
* This case exploits the fact that String
* is a reserved word, and is not listed as a type.
* Thus, the only method that can have it as a parameter is main.
*/

class NoString {
   public static void main (String [ ] argv) {
      int a;
      NoString myobj;

      myobj = new NoString();

      myobj.printInvParameters( argv);

   }

   public boolean printInvParameters( String[] plist) {
      int numpar;
      numpar = plist.length;
      while ( 0 < numpar) {
         System.out.println( plist[numpar - 1]);
         numpar = numpar - 1;
      }

      return false;
   }

}
