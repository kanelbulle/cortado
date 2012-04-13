package se.cortado.syntaxtree;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.*;

public class ClassDeclSimple extends ClassDecl {

	public VarDeclList vl;
	public MethodDeclList ml;

	public ClassDeclSimple(Identifier ai, VarDeclList avl, MethodDeclList aml) {
		i = ai;
		vl = avl;
		ml = aml;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}
	
	public Translate accept(TranslateVisitor v) {
		return v.visit(this);
	}
}
