package se.cortado.syntaxtree;

import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;

public class BooleanType extends Type {
	public void accept(Visitor v) {
		v.visit(this);
	}

	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}

	@Override
	public String getTypeName() {
		return "boolean";
	}
}
