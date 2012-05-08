/*
* Mini Java Test-case
* Id:11
* Author: Pedro de Carvalho Gomes <pedrodcg@csc.kth.se>
*
* This case checks if some class is a subtype of the main class
* This should be an error. 
*
*/

// EXT:ISC 

class InhMain {
   public static void main (String [ ] argv) {
      AType myA;

      myA = new AType();

      myA.printOne(); 

   }

}

class AType extends InhMain {
   public boolean printOne() {
      System.out.println(1);
      return false;
   }
}

