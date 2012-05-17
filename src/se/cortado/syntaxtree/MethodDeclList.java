package se.cortado.syntaxtree;

import se.cortado.syntax.visitor.*;
import java.util.Vector;

public class MethodDeclList {
   private Vector<MethodDecl> list;

   public MethodDeclList() {
      list = new Vector<MethodDecl>();
   }
   
   public MethodDeclList(MethodDecl n) {
      list = new Vector<MethodDecl>();
      addElement(n);
   }

   public void addElement(MethodDecl n) {
      list.addElement(n);
   }

   public MethodDecl elementAt(int i)  { 
      return (MethodDecl)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
   
   public void accept(Visitor v) {
      v.visit(this);
   }
}
