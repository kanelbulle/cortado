package se.cortado.syntaxtree;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.TranslateVisitor;
import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;

public abstract class Exp {
	public abstract void accept(Visitor v);
	public abstract Type accept(TypeVisitor v);
	public abstract Translate accept(TranslateVisitor v);
}
