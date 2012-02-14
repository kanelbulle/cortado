package se.cortado.syntax.constructs;

import se.cortado.syntax.Node;
import se.cortado.syntax.Visitor;

public class Plus extends Node {
	public Node e1, e2;
	
	public Plus(Node left, Node right) {
		e1 = left;
		e2 = right;
	}
	
	public int accept(Visitor v) {
		return v.visit(this);
	}
}