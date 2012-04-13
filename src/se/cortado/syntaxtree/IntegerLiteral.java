package se.cortado.syntaxtree;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.*;

public class IntegerLiteral extends Exp {
	public int i;

	public IntegerLiteral(int ai) {
		i = ai;
	}

	public IntegerLiteral(String s) {
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
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
