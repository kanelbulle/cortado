package se.cortado.syntaxtree;

import se.cortado.visitors.*;

public abstract class Exp {
	public abstract void accept(Visitor v);
	public abstract Type accept(TypeVisitor v);
}
