package se.cortado.syntaxtree;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.*;

public class Program {

	public MainClass mainClass;
	public ClassDeclList classDeclList;

	public Program(MainClass am, ClassDeclList acl) {
		mainClass = am;
		classDeclList = acl;
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
