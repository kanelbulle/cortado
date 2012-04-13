package se.cortado.syntaxtree;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.TranslateVisitor;
import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;

public class VarDeclList {
	private java.util.ArrayList<VarDecl> list;

	public VarDeclList() {
		list = new java.util.ArrayList<VarDecl>();
	}

	public VarDeclList(VarDecl n) {
		list = new java.util.ArrayList<VarDecl>();
		addElement(n);
	}

	public void addElement(VarDecl n) {
		list.add(n);
	}

	public VarDecl elementAt(int i) {
		return list.get(i);
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
