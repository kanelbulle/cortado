package se.cortado.syntaxtree;

import se.cortado.syntax.visitor.TypeVisitor;
import se.cortado.syntax.visitor.Visitor;

public abstract class Statement {
	public abstract void accept(Visitor v);
	public abstract Type accept(TypeVisitor v);
}
