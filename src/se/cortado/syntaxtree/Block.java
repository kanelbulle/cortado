package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public class Block extends Statement {
	public StatementList sl;

	public Block(StatementList asl) {
		sl=asl;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}

