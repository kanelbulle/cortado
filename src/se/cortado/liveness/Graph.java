package se.cortado.liveness;

import java.util.LinkedHashSet;
import java.util.Set;

public class Graph {
	LinkedHashSet<Node>	nodeSet	= new LinkedHashSet<Node>();

	public Set<Node> nodes() {
		return nodeSet;
	}

	public Node newNode() {
		Node node = new Node(this);
		nodeSet.add(node);
		return node;
	}

	void check(Node n) {
		if (n.mygraph != this) {
			throw new Error("Graph.addEdge using nodes from the wrong graph");
		}
	}

	static boolean inList(Node a, NodeList l) {
		for (NodeList p = l; p != null; p = p.tail) {
			if (p.head == a)
				return true;
		}

		return false;
	}

	public void addEdge(Node from, Node to) {
		check(from);
		check(to);

		if (from.goesTo(to)) {
			return;
		}

		to.preds = new NodeList(from, to.preds);
		from.succs = new NodeList(to, from.succs);
	}

	NodeList delete(Node a, NodeList l) {
		if (l == null) {
			throw new Error("Graph.rmEdge: edge nonexistent");
		} else if (a == l.head) {
			return l.tail;
		} else {
			return new NodeList(l.head, delete(a, l.tail));
		}
	}

	public void rmEdge(Node from, Node to) {
		to.preds = delete(from, to.preds);
		from.succs = delete(to, from.succs);
	}

	/**
	 * Print a human-readable dump for debugging.
	 */
	public void show(java.io.PrintStream out) {
		for (Node n : nodes()) {
			out.print(n.toString());
			out.print(": ");

			for (NodeList q = n.succ(); q != null; q = q.tail) {
				out.print(q.head.toString());
				out.print(" ");
			}
			out.println();
		}
	}

}