package se.cortado.syntaxtree;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.*;

public class Times extends Exp {
	public Exp e1, e2;

	public Times(Exp ae1, Exp ae2) {
		e1 = ae1;
		e2 = ae2;
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
