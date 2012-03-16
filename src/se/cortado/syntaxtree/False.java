package se.cortado.syntaxtree;
import se.cortado.visitors.*;

public class False extends Exp {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
