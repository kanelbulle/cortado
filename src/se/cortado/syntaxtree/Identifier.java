package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class Identifier {
	public String s;

	public Identifier(String as) { 
		s=as;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public String toString(){
		return s;
	}
}
