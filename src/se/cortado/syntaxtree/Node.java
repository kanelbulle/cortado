package se.cortado.syntaxtree;

public class Node {
	public int line = -1;
	
	public Node setLine(int line) {
		this.line = line;
		return this;
	}
}
