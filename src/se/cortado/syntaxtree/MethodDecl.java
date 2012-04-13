package se.cortado.syntaxtree;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.*;

public class MethodDecl {
	public Type type;
	public Identifier identifier;
	public FormalList formalList;
	public VarDeclList varDeclList;
	public StatementList statementList;
	public Exp exp;

	public MethodDecl(Type at, Identifier ai, FormalList afl, VarDeclList avl,
			StatementList asl, Exp ae) {
		type = at;
		identifier = ai;
		formalList = afl;
		varDeclList = avl;
		statementList = asl;
		exp = ae;
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
