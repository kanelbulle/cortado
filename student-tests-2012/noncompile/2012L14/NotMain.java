/*
* Mini Java Test-case
* Id:1
* Author: Pedro de Carvalho Gomes <pedrodcg@csc.kth.se>
*
* This test-case exploits the fact that main is not a reserved word.
* Thus, any ID could use such name.
*/

// tege: invalid syntax with return

class NotMain {
   public static void main ( String [] argv) {
      int[] array1;
      int[] array2;
      int len;
      main myobj;

      myobj = new main();

      len = 10;

      if ( ( len != 0)) {array1 = new int [len]; array2 = new int [len+1];} if ((( len >= 1) || len <= 2 )) {array1 = new int[len*2]; array2 = new int[len-2];} else {array1 = new int[len*2]; array2 = new int[len+3];}

      if (! (myobj.countArray( array1, array2))) {
           System.out.println(0);
      }

   }
}

class main {
   public boolean countArray( int[] dimen1, int[ ] dimen2) {
      int one;
      int two;
      one = dimen1.length;
      two = dimen2.length;

      if ((one <= 0) || (two <=0)) {
         return (false);
      } else {
         System.out.println(one*two);
         return(true);
      }
   }
}

