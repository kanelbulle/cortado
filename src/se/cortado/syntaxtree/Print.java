package se.cortado.syntaxtree;

import se.cortado.ir.translate.TR_Exp;
import se.cortado.visitors.*;

public class Print extends Statement {
	public Exp e;

	public Print(Exp ae) {
		e = ae;
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
