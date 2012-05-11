package se.cortado.syntaxtree;

import java.math.BigInteger;

import se.cortado.ir.translate.Translate;
import se.cortado.visitors.TranslateVisitor;
import se.cortado.visitors.TypeVisitor;
import se.cortado.visitors.Visitor;

public class IntegerLiteral extends Exp {
	public int i;

	public IntegerLiteral(int ai) {
		i = ai;
	}

	public IntegerLiteral(String s) throws Exception {
		
		BigInteger bigInt = new BigInteger(s);
		BigInteger maxInt = new BigInteger(Integer.toString(Integer.MAX_VALUE));
		if (bigInt.compareTo(maxInt) == 1) {
			throw new Exception("Integer overflow!");
		}
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}
	
	public Translate accept(TranslateVisitor v) {
		return v.visit(this);
	}
}
