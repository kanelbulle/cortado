package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class NewArray extends Exp {
	public Exp e;

	public NewArray(Exp ae) {
		e=ae; 
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
