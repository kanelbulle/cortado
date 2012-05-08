package se.cortado.liveness;

import java.util.HashMap;
import java.util.HashSet;

import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempList;

public class Liveness extends InterferenceGraph {

	HashMap<Node, HashSet<Temp>>	mLiveIn		= new HashMap<Node, HashSet<Temp>>();
	HashMap<Node, HashSet<Temp>>	mLiveOut	= new HashMap<Node, HashSet<Temp>>();
	HashMap<Node, HashSet<Temp>>	mLiveMap	= new HashMap<Node, HashSet<Temp>>();

	public Liveness(FlowGraph flow) {

		// iterate until we don't change anything in the maps
		boolean changed = true;
		while (changed) {
			changed = false;

			for (NodeList nl = flow.mynodes; nl != null; nl = nl.tail) {
				Node node = nl.head;
				TempList use = flow.uses(nl.head);
				TempList def = flow.defines(nl.head);

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

		this.show(System.out);
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

}
