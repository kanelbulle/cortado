package se.cortado.syntax;

public abstract class Node {
	public abstract int accept(Visitor v);
}
