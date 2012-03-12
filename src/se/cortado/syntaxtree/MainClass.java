package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class MainClass extends ClassDecl {
	public Identifier i2;
	public MethodDecl md;

	public MainClass(Identifier ai, MethodDecl md) {
		i=ai; this.md=md;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}

