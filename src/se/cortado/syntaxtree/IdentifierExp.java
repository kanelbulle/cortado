package se.cortado.syntaxtree;
import se.cortado.visitors.*;

public class IdentifierExp extends Exp {
	public String s;
	public IdentifierExp(String as) { 
		s=as;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
