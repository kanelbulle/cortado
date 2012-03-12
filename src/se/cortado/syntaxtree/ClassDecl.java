package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public abstract class ClassDecl {
	public Identifier i;
	public abstract void accept(Visitor v);
}
