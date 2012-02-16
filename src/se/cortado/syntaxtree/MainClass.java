package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class MainClass {
	public Identifier i1,i2;
	public Statement s;

	public MainClass(Identifier ai1, Identifier ai2, Statement as) {
		i1=ai1; i2=ai2; s=as;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}

