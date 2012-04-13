package se.cortado.syntaxtree;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.*;

public abstract class Exp {
	public abstract void accept(Visitor v);
	public abstract Type accept(TypeVisitor v);
	public abstract Translate accept(TranslateVisitor v);
}
