/*
* Mini Java Test-case
* Id:2
* Author: Pedro de Carvalho Gomes <pedrodcg@csc.kth.se>
*
* This case may try to print an empty array.
*/

// EXT:CGT
// EXT:CLE
// EXT:CEQ

class ArrayOut {
   public static void main ( String [] argv) {
      Aux myvar = new ArrayOut();
      int argsize;

      argsize = 10;

      if ( myvar.isInversible(argsize)    ) {
         while (argsize > 0) {   
            System.out.println( argv[argsize ] );
		argsize =  argsize - 1;
         }
      } else {
         System.out.println( argv[0]);
      }

   }

}

class Aux {
   // It checks if there are at leat two elements
   public boolean isInversible( int num) {
      boolean myret;
      if (num >=2 ) myret = true; else myret = false;
      return myret;
   }
}

