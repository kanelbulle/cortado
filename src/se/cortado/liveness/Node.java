package se.cortado.liveness;

public class Node {

	Graph		mygraph;
	String		name;
	NodeList	succs;
	NodeList	preds;

	public Node(Graph g, String name) {
		mygraph = g;
		this.name = name;
	}

	public NodeList succ() {
		return succs;
	}

	public NodeList pred() {
		return preds;
	}

	public NodeList cat(NodeList a, NodeList b) {
		if (a == null) {
			return b;
		} else {
			return new NodeList(a.head, cat(a.tail, b));
		}
	}

	public NodeList adj() {
		return cat(succ(), pred());
	}

	public int len(NodeList l) {
		int i = 0;
		for (NodeList p = l; p != null; p = p.tail)
			i++;
		return i;
	}

	public int inDegree() {
		return len(pred());
	}

	public int outDegree() {
		return len(succ());
	}

	public int degree() {
		return inDegree() + outDegree();
	}

	public boolean goesTo(Node n) {
		return Graph.inList(n, succ());
	}

	public boolean comesFrom(Node n) {
		return Graph.inList(n, pred());
	}

	public boolean adj(Node n) {
		return goesTo(n) || comesFrom(n);
	}

	public String toString() {
		return name;
	}

}
