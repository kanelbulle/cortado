package se.cortado.syntaxtree;

import java.util.Vector;

import se.cortado.ir.translate.TR_Exp;
import se.cortado.visitors.TranslateVisitor;
import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;

public class StatementList {
	private Vector<Statement> list;

	public StatementList() {
		list = new Vector<Statement>();
	}

	public StatementList(Statement n) {
		list = new Vector<Statement>();
		addElement(n);
	}

	public void addElement(Statement n) {
		list.addElement(n);
	}

	public Statement elementAt(int i) {
		return (Statement) list.elementAt(i);
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
	
	public TR_Exp accept(TranslateVisitor v) {
		return v.visit(this);
	}
}
