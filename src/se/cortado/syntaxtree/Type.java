package se.cortado.syntaxtree;
import se.cortado.syntax.visitor.*;

public abstract class Type {
	public  boolean equals(Type tp)
	{
		return getClass().equals(tp.getClass());
	}

	public abstract void accept(Visitor v);
}
