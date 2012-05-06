package se.cortado.liveness;

import java.util.HashMap;
import java.util.Map;

import se.cortado.assem.Instr;
import se.cortado.assem.InstrList;
import se.cortado.ir.temp.TempList;

public class AssemFlowGraph extends FlowGraph {

	
	private Map<Node, Instr> mInstructions = new HashMap<Node, Instr>();
	private Map<Node, TempList> mDef = new HashMap<Node, TempList>();
	private Map<Node, TempList> mUse = new HashMap<Node, TempList>();
	
	/* 
	 * The constructor AssemFlowGraph takes a list of instructions and returns a flow graph. 
	 * In making the flow graph, the jump fields of the instrs are used in creating control-
	 * flow edges, and the use and def information (obtained from the src and dst fields of 
	 * the instrs) is attached to the nodes by means of the use and def methods of the flowgraph.
	 */
	public AssemFlowGraph(InstrList program) {
		
		/* WARNING! Not tested due to i dont know where to get input from =) */
		
		/* Init, set up first node */
		Instr start = program.head;
		Node prevNode = this.newNode();
		mInstructions.put(prevNode, start);
		mDef.put(prevNode, new TempList(null, null));
		mUse.put(prevNode, new TempList(null, null));
		
		/* Travers rest of program */
		Instr next;
		while((next = program.tail.head) != null) {
			Node curNode = this.newNode();
			
			mInstructions.put(curNode, next);
			mDef.put(curNode, next.def());
			mUse.put(curNode, next.use());
			
			this.addEdge(prevNode, curNode);
			
			prevNode = curNode;
		}
		
		this.show(System.out);
	}
	
	public Instr instr(Node node) {
		return mInstructions.get(node);
	}

	@Override
	public TempList def(Node node) {
		return mDef.get(node);
	}

	@Override
	public TempList use(Node node) {
		return mUse.get(node);
	}

	/* Move Spceific optimizations is not implemented */
	@Override
	public boolean isMove(Node node) {
		return false;
	}

}
