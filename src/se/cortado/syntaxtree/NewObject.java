package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class NewObject extends Exp {
	public Identifier i;

	public NewObject(Identifier ai) {
		i=ai;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
