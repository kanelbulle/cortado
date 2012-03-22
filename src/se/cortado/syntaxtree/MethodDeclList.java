package se.cortado.syntaxtree;

import java.util.Vector;

import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;

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

	public MethodDecl elementAt(int i) {
		return (MethodDecl) list.elementAt(i);
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
}
