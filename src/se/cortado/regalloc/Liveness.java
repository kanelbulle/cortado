package se.cortado.regalloc;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import se.cortado.assem.Instr;
import se.cortado.assem.MOVE;
import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempList;
import se.cortado.liveness.AssemFlowGraph;
import se.cortado.liveness.Node;
import se.cortado.liveness.NodeList;

public class Liveness extends InterferenceGraph {
	HashSet<MovePair>				mMoveNodes	= new HashSet<MovePair>();

	LinkedHashMap<Node, Temp>		mNodeMap	= new LinkedHashMap<Node, Temp>();
	LinkedHashMap<Temp, Node>		mTempMap	= new LinkedHashMap<Temp, Node>();

	HashMap<Node, HashSet<Temp>>	mLiveIn		= new HashMap<Node, HashSet<Temp>>();
	HashMap<Node, HashSet<Temp>>	mLiveOut	= new HashMap<Node, HashSet<Temp>>();

	public Liveness(AssemFlowGraph flow, List<Instr> instrs) {

		// iterate until we don't change anything in the maps
		boolean changed = true;
		while (changed) {
			changed = false;

			for (Node node : flow.nodes()) {
				TempList use = flow.uses(node);
				TempList def = flow.defines(node);

				HashSet<Temp> useSet = setFromList(use);
				HashSet<Temp> defSet = setFromList(def);
				HashSet<Temp> outSet = getLiveOut(node);

				// calculate live in
				// live in = use of node plus all variables in liveout of node
				// but not in def of node
				HashSet<Temp> outCopy = new HashSet<Temp>(outSet);
				outCopy.removeAll(defSet);
				useSet.addAll(outCopy);
				changed = changed | setLiveIn(node, useSet);

				// calculate live out
				// live out = union of all live-in sets of all successors of
				// node
				HashSet<Temp> newOutSet = new HashSet<Temp>();
				for (NodeList snl = node.succ(); snl != null; snl = snl.tail) {
					HashSet<Temp> livein = getLiveIn(snl.head);
					if (livein != null) {
						newOutSet.addAll(livein);
					}
				}

				changed = changed | setLiveOut(node, newOutSet);
			}
		}
		
		// process all nodes using the livein and liveout information
		for (Node node : flow.nodes()) {
			Instr instr = flow.instr(node);

			if (instr instanceof MOVE) {
				MOVE move = (MOVE) instr;
				Node dstNode = move.dst == null ? null : tnode(move.dst);
				Node srcNode = move.src == null ? null : tnode(move.src);

				MovePair mp = new MovePair(srcNode, dstNode);
				mMoveNodes.add(mp);

				for (Temp liveOutTemp : getLiveOut(node)) {
					if (liveOutTemp != move.src) {
						Node liveOutNode = tnode(liveOutTemp);

						addEdge(dstNode, liveOutNode);
					}
				}
			} else {
				HashSet<Temp> defSet = setFromList(flow.defines(node));
				for (Temp liveOutTemp : getLiveOut(node)) {
					for (Temp definedTemp : defSet) {
						// add interference edge from definedTemp to liveOutTemp
						Node liveOutNode = tnode(liveOutTemp);
						Node definedNode = tnode(definedTemp);

						if (liveOutNode != definedNode) {
							addEdge(definedNode, liveOutNode);
						}
					}
				}
			}
		}
		
//		for (Node node : flow.nodes()) {
//			Instr instr = flow.instr(node);
//			TempList defTl = flow.defines(node);
//			for (; defTl != null; defTl = defTl.tail) {
//				Temp def = defTl.head;
//				for (Temp temp : getLiveOut(node)) {
//					if (!(instr instanceof MOVE) || def != temp) {
//						Node defNode = tnode(def);
//						Node tempNode = tnode(temp);
//						addEdge(defNode, tempNode);
//					}
//				}
//				for (Temp temp : getLiveIn(node)) {
//					if (!(instr instanceof MOVE) || def != temp) {
//						Node defNode = tnode(def);
//						Node tempNode = tnode(temp);
//						addEdge(defNode, tempNode);
//					}
//				}
//			}
//		}
		
		
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
		return set == null ? new HashSet<Temp>() : new HashSet<Temp>(set);
	}

	public HashSet<Temp> getLiveOut(Node node) {
		HashSet<Temp> set = mLiveOut.get(node);
		return set == null ? new HashSet<Temp>() : new HashSet<Temp>(set);
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

	public Node newNode(Temp temp) {
		Node node = super.newNode();

		mNodeMap.put(node, temp);
		mTempMap.put(temp, node);
		node.name = temp.toString();

		return node;
	}

	@Override
	public Node tnode(Temp temp) {
		Node node = mTempMap.get(temp);
		if (node == null) {
			node = newNode(temp);
		}

		return node;
	}

	@Override
	public Temp gtemp(Node node) {
		return mNodeMap.get(node);
	}

	@Override
	public HashSet<MovePair> moves() {
		return mMoveNodes;
	}

	@Override
	public void show(PrintStream out) {
		for (Node node : nodes()) {
			out.println("node : " + node.toString());
		}

		for (Node node : nodes()) {
			for (NodeList nl = node.succ(); nl != null; nl = nl.tail) {
				Node neighbor = nl.head;
				out.println("edge : " + node.toString() + " --> " + neighbor.toString());
			}
		}
	}

}
