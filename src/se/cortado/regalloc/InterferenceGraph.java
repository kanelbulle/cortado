package se.cortado.regalloc;

import se.cortado.ir.temp.Temp;
import se.cortado.liveness.Graph;
import se.cortado.liveness.MoveList;
import se.cortado.liveness.Node;

abstract public class InterferenceGraph extends Graph {
	abstract public Node tnode(Temp temp);

	abstract public Temp gtemp(Node node);

	abstract public MoveList moves();

	public int spillCost(Node node) {
		return 1;
	}
}
