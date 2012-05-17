package se.cortado.syntaxtree;

import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;

public class IdentifierType extends Type {
	public String s;

	public boolean equals(Type tp) {
		if (!(tp instanceof IdentifierType))
			return false;
		return ((IdentifierType) tp).s.equals(s);
	}

	public IdentifierType(String as) {
		s = as;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}

	@Override
	public String getTypeName() {
		return s;
	}
}
