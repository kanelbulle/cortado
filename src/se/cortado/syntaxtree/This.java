package se.cortado.syntaxtree;
import se.cortado.visitors.*;

public class This extends Exp {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
