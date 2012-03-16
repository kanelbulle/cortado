package se.cortado.syntaxtree;

import se.cortado.visitors.*;

import java.util.Vector;

public class ClassDeclList {
	private Vector<ClassDecl> list;

	public ClassDeclList() {
		list = new Vector<ClassDecl>();
	}
	
	public ClassDeclList(ClassDecl n) {
		list = new Vector<ClassDecl>();
		addElement(n);
	}

	public void addElement(ClassDecl n) {
		list.addElement(n);
	}

	public ClassDecl elementAt(int i)  { 
		return (ClassDecl)list.elementAt(i); 
	}

	public int size() { 
		return list.size(); 
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
