package se.cortado.liveness;

import java.util.LinkedHashMap;
import java.util.Set;

import se.cortado.ir.temp.Temp;

public class Graph {
	LinkedHashMap<Node, Temp>	nodeMap	= new LinkedHashMap<Node, Temp>();
	LinkedHashMap<Temp, Node>	tempMap	= new LinkedHashMap<Temp, Node>();

	public Set<Node> nodes() {
		return nodeMap.keySet();
	}

	public Temp nodeTemp(Node node) {
		return nodeMap.get(node);
	}

	public Node nodeForTemp(Temp temp) {
		Node node = tempMap.get(temp);
		if (node == null) {
			return newNode(temp);
		}
		
		return node; 
	}

	public Node newNode() {
		Node node = new Node(this, "" + nodes().size());
		nodeMap.put(node, null);
		return node;
	}

	public Node newNode(Temp temp) {
		Node node = new Node(this, temp.toString());
		nodeMap.put(node, temp);
		tempMap.put(temp, node);
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