package se.cortado.syntaxtree;
import se.cortado.visitors.*;

public class While extends Statement {
	public Exp e;
	public Statement s;

	public While(Exp ae, Statement as) {
		e=ae; s=as; 
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}

