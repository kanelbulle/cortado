package se.cortado.syntaxtree;

import java.util.Vector;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.TranslateVisitor;
import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;

public class FormalList extends Node {
   private Vector<Formal> list;

   public FormalList() {
      list = new Vector<Formal>();
   }

   public boolean contains(String identifier) {
	   for (Formal e : list) {
		   if (e.i.s.equals(identifier)) {
			   return true;
		   }
	   }
	   return false;
   }
   
   public FormalList(Formal n) {
      list = new Vector<Formal>();
      addElement(n);
   }

   public void addElement(Formal n) {
      list.addElement(n);
   }

   public Formal elementAt(int i)  { 
      return (Formal)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
   
   public void accept(Visitor v) {
      v.visit(this);
   }
   
   public Type accept(TypeVisitor v) {
	   return v.visit(this);
   }
   
   public Translate accept(TranslateVisitor v) {
		return v.visit(this);
	}
}
