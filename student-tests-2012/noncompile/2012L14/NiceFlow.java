/*
* Mini Java Test-case
* Id:4
* Author: Pedro de Carvalho Gomes <pedrodcg@csc.kth.se>
*
* This test-case tests if the correct scanning of comments.
* Also, test some exp constructions and passing of parameters
*/


class NiceFlow {

   public static void main (String [   ] argv) {
      int numpar;
      numpar = 30;

      while ( 0 < numpar) {
         /* // System.out.println( plist[numpar - 1]); */
         (new FirstOne()).arraySize( new int[numpar]);
         numpar = numpar - 1;
      }
   }
}

class FirstOne {
   public boolean arraySize( int[   ] iarr) {
      System.out.println(iarr.length);
      return false;
   }
}
