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
		BigInteger minInt = new BigInteger(Integer.toString(Integer.MIN_VALUE));
		if (bigInt.compareTo(minInt) < 0) {
			throw new Exception("Integer underflow!");
		}
		if (bigInt.compareTo(maxInt) > 0) {
			throw new Exception("Integer overflow!");
		}
		
		i = bigInt.intValue();
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
