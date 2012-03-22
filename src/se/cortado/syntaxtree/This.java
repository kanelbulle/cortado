package se.cortado.syntaxtree;

import se.cortado.visitors.*;

public class This extends Exp {
	public void accept(Visitor v) {
		v.visit(this);
	}

	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}
}
