/*
 * Mini Java Test-case
 * Id:8
 * Author: Pedro de Carvalho Gomes <pedrodcg@csc.kth.se>
 *
 * This test-case is a simple recursive implementation
 * of fibonacci calculation in Minijava.
 * */


class Fibonacci {

   public static void main (String [   ] argv) {
       System.out.println( (new FibCalc()).fibNum(20)) ;


   }

}

class FibCalc {
    public int fibNum ( int num) {
       int retvalue;

       if ( num == 0)
          retvalue = 0;
       else if ( num == 1)
          retvalue = 1;
       else
          retvalue = fibNum( num - 1) + fibNum( num - 2 );
       return retvalue;
    }
}
