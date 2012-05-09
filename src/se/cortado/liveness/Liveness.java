package se.cortado.liveness;

import java.util.HashMap;
import java.util.HashSet;

import se.cortado.assem.Instr;
import se.cortado.assem.MOVE;
import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempList;

public class Liveness extends InterferenceGraph {

	HashMap<Node, HashSet<Temp>>	mLiveIn		= new HashMap<Node, HashSet<Temp>>();
	HashMap<Node, HashSet<Temp>>	mLiveOut	= new HashMap<Node, HashSet<Temp>>();
	HashMap<Node, HashSet<Temp>>	mLiveMap	= new HashMap<Node, HashSet<Temp>>();

	public Liveness(AssemFlowGraph flow) {

		// iterate until we don't change anything in the maps
		boolean changed = true;
		while (changed) {
			changed = false;

			for (Node node : nodes()) {
				TempList use = flow.uses(node);
				TempList def = flow.defines(node);

				HashSet<Temp> useSet = setFromList(use);
				HashSet<Temp> defSet = setFromList(def);
				HashSet<Temp> outSet = new HashSet<Temp>();

				// calculate live in
				// live in = use of node plus all variables in liveout of node
				// but not in def of node
				outSet.removeAll(defSet);
				useSet.addAll(outSet);
				changed = changed | setLiveIn(node, useSet);

				// calculate live out
				// live out = union of all live-in sets of all successors of
				// node
				for (NodeList snl = node.succ(); snl != null; snl = snl.tail) {
					HashSet<Temp> livein = getLiveIn(snl.head);
					if (livein != null) {
						outSet.addAll(livein);
					}
				}

				changed = changed | setLiveOut(node, outSet);
			}
		}

		// process all nodes using the livein and liveout information
		for (Node node : nodes()) {
			Instr instr = flow.instr(node);

			if (instr instanceof MOVE && ((MOVE)instr).dst != null && ((MOVE)instr).src != null) {
				MOVE move = (MOVE) instr;
				Node dstNode = nodeForTemp(move.dst);
				for (Temp liveOutTemp : getLiveOut(node)) {
					if (liveOutTemp != move.src) {
						Node liveOutNode = nodeForTemp(liveOutTemp);
						
						addEdge(dstNode, liveOutNode);
					}
				}
			} else {
				HashSet<Temp> defSet = setFromList(flow.defines(node));
				for (Temp liveOutTemp : getLiveOut(node)) {
					for (Temp definedTemp : defSet) {
						// add interference edge from definedTemp to liveOutTemp
						Node liveOutNode = nodeForTemp(liveOutTemp);
						Node definedNode = nodeForTemp(definedTemp);

						addEdge(definedNode, liveOutNode);
					}
				}
			}
		}
	}

	public HashSet<Temp> setFromList(TempList tl) {
		HashSet<Temp> set = new HashSet<Temp>();
		for (; tl != null; tl = tl.tail) {
			set.add(tl.head);
		}
		return set;
	}

	public HashSet<Temp> getLiveIn(Node node) {
		HashSet<Temp> set = mLiveIn.get(node);
		return set == null ? null : new HashSet<Temp>(set);
	}

	public HashSet<Temp> getLiveOut(Node node) {
		HashSet<Temp> set = mLiveOut.get(node);
		return set == null ? null : new HashSet<Temp>(set);
	}

	/**
	 * 
	 * @param n
	 * @param set
	 * @return true if the new set is different from the old set. returns false
	 *         if the specified set is identical to the current live in set.
	 */
	public boolean setLiveIn(Node n, HashSet<Temp> set) {
		HashSet<Temp> oldSet = mLiveIn.get(n);
		if (set.equals(oldSet)) {
			return false;
		}

		mLiveIn.put(n, set);

		return true;
	}

	public boolean setLiveOut(Node n, HashSet<Temp> set) {
		HashSet<Temp> oldSet = mLiveOut.get(n);
		if (set.equals(oldSet)) {
			return false;
		}

		mLiveOut.put(n, set);

		return true;
	}

	@Override
	public Node tnode(Temp temp) {
		return null;
	}

	@Override
	public Temp gtemp(Node node) {
		return null;
	}

	@Override
	public MoveList moves() {
		return null;
	}

	// @Override
	// public void show(PrintStream out) {
	// for (NodeList nl = mynodes; nl != null; nl = nl.tail) {
	// out.print("node : " + )
	// }
	//
	// }

}
