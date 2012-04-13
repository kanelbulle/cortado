package se.cortado.syntaxtree;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.TranslateVisitor;
import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;

public class Call extends Exp {
	public Exp e;
	public String c = null; // class of e

	public Identifier i;
	public ExpList el;

	public Call(Exp ae, Identifier ai, ExpList ael) {
		e = ae;
		i = ai;
		el = ael;
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
