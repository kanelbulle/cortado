package se.cortado.syntaxtree;
import se.cortado.visitors.*;

public class VarDecl {
	public Type type;
	public Identifier identifier;

	public VarDecl(Type at, Identifier ai) {
		type = at;
		identifier = ai;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
