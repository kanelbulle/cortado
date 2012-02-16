package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class BooleanType extends Type {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
