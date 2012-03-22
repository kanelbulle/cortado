package se.cortado.syntaxtree;

import se.cortado.visitors.*;

public class MainClass extends ClassDecl {
	public MethodDecl md;

	public MainClass(Identifier ai, MethodDecl md) {
		i = ai;
		this.md = md;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}

}
