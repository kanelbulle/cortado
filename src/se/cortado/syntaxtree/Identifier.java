package se.cortado.syntaxtree;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.TranslateVisitor;
import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;


public class Identifier {
	public String s;
	public int line;

	public Identifier(String as, int row) {
		s = as;
		this.line = row;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public String toString() {
		return s;
	}

	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}
	
	public Translate accept(TranslateVisitor v) {
		return v.visit(this);
	}
}
