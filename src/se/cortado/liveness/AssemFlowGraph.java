package se.cortado.liveness;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.cortado.assem.Instr;
import se.cortado.assem.LABEL;
import se.cortado.assem.MOVE;
import se.cortado.ir.temp.Label;
import se.cortado.ir.temp.LabelList;
import se.cortado.ir.temp.TempList;

public class AssemFlowGraph extends FlowGraph {

	private Map<Instr, Node>	mNodes			= new HashMap<Instr, Node>();
	private Map<Node, Instr>	mInstructions	= new HashMap<Node, Instr>();
	private Map<Node, TempList>	mDefines		= new HashMap<Node, TempList>();
	private Map<Node, TempList>	mUses			= new HashMap<Node, TempList>();
	private Map<Label, LABEL>	mLabels			= new HashMap<Label, LABEL>();

	private void processInstruction(Instr instr, Instr nextInstr) {
		Node currNode = node(instr);

		mDefines.put(currNode, instr.defines());
		mUses.put(currNode, instr.uses());

		// add forward edge
		if (nextInstr != null) {
			Node nextNode = node(nextInstr);
			addEdge(currNode, nextNode);
		}

		// add edges to possible jump positions
		if (instr.jumps() != null) {
			for (LabelList ll = instr.jumps().labels; ll != null; ll = ll.tail) {
				Label label = ll.head;
				LABEL labelInstr = mLabels.get(label);
				addEdge(currNode, node(labelInstr));
			}
		}
	}

	/*
	 * The constructor AssemFlowGraph takes a list of instructions and returns a
	 * flow graph. In making the flow graph, the jump fields of the instrs are
	 * used in creating control- flow edges, and the use and def information
	 * (obtained from the src and dst fields of the instrs) is attached to the
	 * nodes by means of the use and def methods of the flowgraph.
	 */
	public AssemFlowGraph(List<Instr> program) {
		// build label lookup
		for (Instr instr : program) {
			if (instr instanceof LABEL) {
				LABEL l = (LABEL) instr;
				mLabels.put(l.label, l);
			}
		}
		
		// build the graph
		for (int i = 0; i < program.size() - 1; i++) {
			Instr i1 = program.get(i);
			Instr i2 = program.get(i + 1);
			
			processInstruction(i1, i2);
		}
	}

	public Node node(Instr instr) {
		Node node = mNodes.get(instr);
		if (node == null) {
			node = this.newNode();
			mInstructions.put(node, instr);
		}

		return node;
	}

	public Instr instr(Node node) {
		return mInstructions.get(node);
	}

	@Override
	public TempList defines(Node node) {
		return mDefines.get(node);
	}

	@Override
	public TempList uses(Node node) {
		return mUses.get(node);
	}

	/* Move specific optimizations is not implemented */
	@Override
	public boolean isMove(Node node) {
		return mInstructions.get(node) instanceof MOVE;
	}

}
