package se.cortado.syntaxtree;

import se.cortado.ir.translate.TR_Exp;
import se.cortado.visitors.*;

public class NewObject extends Exp {
	public Identifier i;

	public NewObject(Identifier ai) {
		i = ai;
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
