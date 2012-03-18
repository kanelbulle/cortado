package se.cortado.syntaxtree;

import se.cortado.syntax.visitor.TypeVisitor;
import se.cortado.syntax.visitor.Visitor;

public abstract class Exp {
	public abstract void accept(Visitor v);
	public abstract Type accept(TypeVisitor v);
}
