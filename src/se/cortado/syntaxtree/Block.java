package se.cortado.syntaxtree;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.*;

public class Block extends Statement {
	public StatementList sl;

	public Block(StatementList asl) {
		sl = asl;
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
