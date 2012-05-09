package se.cortado.regalloc;

import se.cortado.liveness.Node;

public class MovePair {
	public Node source;
	public Node destination;
	
	public MovePair(Node source, Node destination) {
		this.source = source;
		this.destination = destination;
	}
}
