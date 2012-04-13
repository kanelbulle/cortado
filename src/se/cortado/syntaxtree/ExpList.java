package se.cortado.syntaxtree;

import java.util.Vector;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.*;

public class ExpList {
	private Vector<Exp> list;

	public ExpList() {
		list = new Vector<Exp>();
	}

	public ExpList(Exp n) {
		list = new Vector<Exp>();
		addElement(n);
	}

	public void addElement(Exp n) {
		list.addElement(n);
	}

	public Exp elementAt(int i) {
		return (Exp) list.elementAt(i);
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
