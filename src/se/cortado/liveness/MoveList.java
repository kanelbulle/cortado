package se.cortado.liveness;

public class MoveList {
	public Node src, dst;
	public MoveList tail;
	
	public MoveList(Node s, Node d, MoveList t) {
		src = s;
		dst = d;
		tail = t;
	}
}

