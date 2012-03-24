package se.cortado.syntaxtree;

import se.cortado.ir.translate.TR_Exp;
import se.cortado.visitors.*;

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
	
	public TR_Exp accept(TranslateVisitor v) {
		return v.visit(this);
	}
}
