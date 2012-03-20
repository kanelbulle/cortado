package se.cortado.syntaxtree;

import se.cortado.syntax.visitor.TypeVisitor;
import se.cortado.syntax.visitor.Visitor;

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
