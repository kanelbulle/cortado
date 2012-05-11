/*
* Mini Java Test-case
* Id:10
* Author: Pedro de Carvalho Gomes <pedrodcg@csc.kth.se>
*
* This test-case checks if the Java sub-typing rules were implemented
* properly.
*/

// tege: invalid to use Exp as if Stmt

class SubTyping {

   public static void main ( String [] argv) {
      A myobj;
      int myrand;

      myrand = 6;

      if (( myrand >= 0) && (myrand < 5)) {
         myobj = new A();
      } else
      if (( myrand >= 5) && (myrand < 15)) {
         myobj = new B();
      } else {
         myobj = new C();
      }

      myobj.printValue();


   }

}

class A {
   public boolean printValue() {
      System.out.println(10);
      return true;
   }
}

class B extends A {
   public boolean printValue() {
      System.out.println(20);
      return true;
   }
}

class C extends B {
   public boolean printValue() {
      System.out.println(30);
      return true;
   }
}

