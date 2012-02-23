package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class MainClass {
	public Identifier i1,i2;
	public MethodDecl md;

	public MainClass(Identifier ai1, MethodDecl md) {
		i1=ai1; this.md=md;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}

