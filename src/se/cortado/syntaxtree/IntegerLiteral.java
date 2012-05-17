package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class IntegerLiteral extends Exp {
	public int i;

	public IntegerLiteral(int ai) {
		i=ai;
	}
	
	public IntegerLiteral(String s) {
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
