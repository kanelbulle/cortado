package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class Call extends Exp {
	public Exp e;
	public String c=null; // class of e

	public Identifier i;
	public ExpList el;

	public Call(Exp ae, Identifier ai, ExpList ael) {
		e=ae; i=ai; el=ael;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}