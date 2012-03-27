package se.cortado.syntaxtree;

import se.cortado.ir.translate.TR_Exp;
import se.cortado.visitors.*;

public abstract class Statement {
	public abstract void accept(Visitor v);
	public abstract Type accept(TypeVisitor v);
	public abstract TR_Exp accept(TranslateVisitor v);
}
