package se.cortado.syntaxtree;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.TranslateVisitor;
import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;

public class ClassDeclExtends extends ClassDecl {

	public Identifier j;
	public VarDeclList vl;
	public MethodDeclList ml;

	public ClassDeclExtends(Identifier ai, Identifier aj, VarDeclList avl,
			MethodDeclList aml) {
		i = ai;
		j = aj;
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
