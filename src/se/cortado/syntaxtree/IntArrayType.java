package se.cortado.syntaxtree;

import se.cortado.visitors.*;

public class IntArrayType extends Type {
	public void accept(Visitor v) {
		v.visit(this);
	}

	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}

	@Override
	public String getTypeName() {
		return "int[]";
	}
}
