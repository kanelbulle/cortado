/*
* Mini Java Test-case
* Id:9
* Author: Pedro de Carvalho Gomes <pedrodcg@csc.kth.se>
*
* This case type-check if two inheritance classes are
* multually sub-types. This is a type-error.
*
*/

// EXT:ISC

class CrossInh {
   public static void main (String [ ] argv) {
      AType myA;
      BType myB;

      myA = new AType();
      myB = new BType();

      myA.printTwo();
      myB.printOne();

   }

}

class AType extends BType {
   public boolean printOne() {
      System.out.println(1);
      return false;
   }
}

class BType extends AType {
   public boolean printTwo() {
      System.out.println(2);
      return false;
   }
}
