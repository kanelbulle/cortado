package se.cortado.liveness;

import se.cortado.ir.temp.TempList;

/**
 * A control flow graph is a directed graph in which each edge indicates a
 * possible flow of control. Also, each node in the graph defines a set of
 * temporaries; each node uses a set of temporaries; and each node is, or is
 * not, a <strong>move</strong> instruction.
 * 
 * @see AssemFlowGraph
 */

public abstract class FlowGraph extends Graph {
	/**
	 * The set of temporaries defined by this instruction or block
	 */
	public abstract TempList defines(Node node);

	/**
	 * The set of temporaries used by this instruction or block
	 */
	public abstract TempList uses(Node node);

	/**
	 * True if this node represents a <strong>move</strong> instruction, i.e.
	 * one that can be deleted if def=use.
	 */
	public abstract boolean isMove(Node node);

	/**
	 * Print a human-readable dump for debugging.
	 */
	public void show(java.io.PrintStream out) {
		for (Node n : nodes()) {
			out.print(n.toString());
			out.print(": ");
			for (TempList q = defines(n); q != null; q = q.tail) {
				out.print(q.head.toString());
				out.print(" ");
			}
			out.print(isMove(n) ? "<= " : "<- ");
			for (TempList q = uses(n); q != null; q = q.tail) {
				out.print(q.head.toString());
				out.print(" ");
			}
			out.print("; goto ");
			for (NodeList q = n.succ(); q != null; q = q.tail) {
				out.print(q.head.toString());
				out.print(" ");
			}
			out.println();
		}
	}

}
