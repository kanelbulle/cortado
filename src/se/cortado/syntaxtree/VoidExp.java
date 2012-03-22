package se.cortado.syntaxtree;

import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;

public class VoidExp extends Exp {

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}

}
