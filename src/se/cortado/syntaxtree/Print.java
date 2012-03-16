package se.cortado.syntaxtree;
import se.cortado.visitors.*;

public class Print extends Statement {
	public Exp e;

	public Print(Exp ae) {
		e=ae; 
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
