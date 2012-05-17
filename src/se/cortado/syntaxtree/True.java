package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class True extends Exp {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
