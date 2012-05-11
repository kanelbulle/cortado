package se.cortado.regalloc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempList;
import se.cortado.ir.temp.TempMap;
import se.cortado.liveness.Node;
import se.cortado.liveness.NodeList;
import se.cortado.x86_64.frame.Hardware;

public class Color implements TempMap {

	public class Edge {
		Node	a;
		Node	b;

		public Edge(Node a, Node b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Edge) {
				Edge e = (Edge) obj;
				return e.a.equals(a) && e.b.equals(b);
			}

			return false;
		}

		@Override
		public int hashCode() {
			return 31 * a.hashCode() + b.hashCode();
		}
	}

	TempMap						mInitial;
	InterferenceGraph			mInterferenceGraph;
	List<Temp>					mColors				= new ArrayList<Temp>();
	final int					K;

	HashMap<Temp, Temp>			mColorMap			= new HashMap<Temp, Temp>();

	Set<Node>					mSimplifyWorklist	= new HashSet<Node>();
	Set<Temp>					mColoredNodes		= new HashSet<Temp>();
	Stack<Temp>					mSelectStack		= new Stack<Temp>();

	HashMap<Temp, List<Temp>>	mAdjList			= new HashMap<Temp, List<Temp>>();
	HashMap<Node, Integer>		mDegree				= new HashMap<Node, Integer>();

	public Color(InterferenceGraph ig, TempMap initial, TempList registers) {
		mInterferenceGraph = ig;
		mInitial = initial;

		// calc K and setup mColors and mColorMap
		int c = 0;
		for (TempList tl = registers; tl != null; tl = tl.tail) {
			c++;
			// initialize the precolored nodes
			if (mInitial.tempMap(tl.head) != null) {
				mColorMap.put(tl.head, tl.head);
				System.out.println("adding precolored " + tl.head);
			}

			mColors.add(tl.head);
		}
		K = c;

		// create empty adjacency lists
		for (Node node : ig.nodes()) {
			mAdjList.put(ig.gtemp(node), new ArrayList<Temp>());
		}

		// build edges
		build();

		// setup simplify worklist
		makeWorklist();

		// end of setup, start working
		while (!mSimplifyWorklist.isEmpty()) {
			simplify();
		}

		// simplification complete, assign colors
		assignColors();
	}

	private void addEdge(Node u, Node v) {
		if (u != v) {
			Temp ut = mInterferenceGraph.gtemp(u);
			Temp vt = mInterferenceGraph.gtemp(v);
			if (mInitial.tempMap(ut) == null) {
				// ut not precolored
				mAdjList.get(ut).add(vt);
				incrementDegree(u);
			}
			if (mInitial.tempMap(vt) == null) {
				// vt not precolored
				mAdjList.get(vt).add(ut);
				incrementDegree(v);
			}
		}
	}

	private void makeWorklist() {
		for (Node n : mInterferenceGraph.nodes()) {
			Temp t = mInterferenceGraph.gtemp(n);
			if (!precolored(t)) {
				if (degree(n) >= K) {
					throw new Error("Temp " + t + " interferes with more than K nodes");
				}

				mSimplifyWorklist.add(n);
			}
		}
	}

	private void build() {
		for (Node node : mInterferenceGraph.nodes()) {
			for (NodeList nl = node.adj(); nl != null; nl = nl.tail) {
				addEdge(node, nl.head);
			}
		}
	}

	private void assignColors() {
		while (!mSelectStack.isEmpty()) {
			Temp tempNode = mSelectStack.pop();

			HashSet<Temp> okColors = new HashSet<Temp>(mColors);

			for (Temp w : mAdjList.get(tempNode)) {
				if (mColoredNodes.contains(w)) {
					Temp color = mColorMap.get(w);
					okColors.remove(color);
				}
				if (precolored(w)) {
					okColors.remove(w);
				}
			}

			if (okColors.size() == 0) {
				throw new Error("Could not complete register allocation due to spilling!");
			}

			mColoredNodes.add(tempNode);
			Temp color = okColors.iterator().next();
			System.out.println("Assigning color " + color + " to " + tempNode);
			mColorMap.put(tempNode, color);
		}
	}

	private void simplify() {
		Node node = mSimplifyWorklist.iterator().next();
		mSimplifyWorklist.remove(node);

		mSelectStack.push(mInterferenceGraph.gtemp(node));

		for (NodeList nl = node.adj(); nl != null; nl = nl.tail) {
			decrementDegree(nl.head);
		}
	}

	private void decrementDegree(Node node) {
		Integer d = mDegree.get(node);
		int degree = d == null ? 0 : d;
		mDegree.put(node, degree - 1);
	}

	private void incrementDegree(Node node) {
		Integer d = mDegree.get(node);
		int degree = d == null ? 0 : d;
		mDegree.put(node, degree + 1);
	}

	private int degree(Node node) {
		Integer d = mDegree.get(node);
		return d == null ? 0 : d;
	}

	private boolean precolored(Temp t) {
		return mInitial.tempMap(t) != null;
	}

	public TempList spills() {
		return null;
	}

	@Override
	public String tempMap(Temp t) {
		String s = mInitial.tempMap(t);
		if (s == null) {
			Temp temp = mColorMap.get(t);
			
			s = Hardware.tempName(temp);
		}
		return s;
	}

}
