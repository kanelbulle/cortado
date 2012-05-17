package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public abstract class ClassDecl {
	public abstract void accept(Visitor v);
}
