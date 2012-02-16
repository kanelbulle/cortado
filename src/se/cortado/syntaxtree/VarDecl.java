package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class VarDecl {
	public Type t;
	public Identifier i;

	public VarDecl(Type at, Identifier ai) {
		t=at; i=ai;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
