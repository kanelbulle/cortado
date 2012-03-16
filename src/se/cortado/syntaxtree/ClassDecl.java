package se.cortado.syntaxtree;
import se.cortado.visitors.*;

public abstract class ClassDecl {
	public Identifier i;
	public abstract void accept(Visitor v);
}
