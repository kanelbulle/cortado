/*
* Mini Java Test-case
* Id:5
* Author: Pedro de Carvalho Gomes <pedrodcg@csc.kth.se>
*
* This case exploits the fact that main is the only
* possible void method, since it is a reserved word.
* Moreover, it doesn't allow void to be a type.
* Also, the only method in the entry class should be main.
*/

class NoVoid {
   public static void main (String [ ] argv) {
      BadClass myobj;

      myobj = new BadClass();

      myobj.printNumber();

   }

}

class BadClass {

   public void printNumber() {
      int i;
      i=0;
      while ( i < 100) {
         System.out.println(i);
         i = i+1;
      }

      return false;
   }

}
