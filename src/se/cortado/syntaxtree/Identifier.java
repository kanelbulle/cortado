package se.cortado.syntaxtree;

import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;


public class Identifier {
	public String s;
	public int row;

	public Identifier(String as, int row) {
		s = as;
		this.row = row;
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
}
