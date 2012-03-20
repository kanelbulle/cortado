package se.cortado.syntaxtree;

import se.cortado.syntax.visitor.TypeVisitor;
import se.cortado.syntax.visitor.Visitor;

public abstract class Type {
	@Override
	public boolean equals(Object o) {
		if (o instanceof Type) {
			Type tp = (Type) o;
			return this.getTypeName().equals(tp.getTypeName());
		}

		return false;
	}

	@Override
	public String toString() {
		return getTypeName();
	}

	public abstract void accept(Visitor v);

	public abstract Type accept(TypeVisitor v);

	public abstract String getTypeName();
}
