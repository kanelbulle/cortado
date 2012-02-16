
package se.cortado.syntaxtree;

import se.cortado.syntax.visitor.*;

public abstract class Exp {
	public abstract void accept(Visitor v);
}
