package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public abstract class Statement {
	public abstract void accept(Visitor v);
}
