package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class Identifier {
	public String s;
	public int row;

	public Identifier(String as, int row) { 
		s=as;
		this.row = row;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public String toString(){
		return s;
	}
}
