package se.cortado.ir.canon;

import se.cortado.ir.common.CanonicalizedFragment;
import se.cortado.ir.common.SimpleFragment;
import se.cortado.ir.tree.IR_StmList;
import se.cortado.ir.tree.Print;

public class Canonicalizer {
	
	boolean	debugPrint;

	public Canonicalizer(boolean print) {
		this.debugPrint = print;
	}

	/* Traverses each SimpleFragments consisting of raw IR code and converts
	 * into better canonicalized code */
	public CanonicalizedFragment canonicalize(SimpleFragment fragments) {
		CanonicalizedFragment firstCanonFrag = null;
		CanonicalizedFragment currentCanonFrag = null;
		
		while (fragments != null) {

			IR_StmList list = Canon.linearize(fragments.body);
			BasicBlocks basicBlocks = new BasicBlocks(list);
			
			TraceSchedule traceSchedule = new TraceSchedule(basicBlocks);
			list = traceSchedule.stms;
			
			/* Finally remove all trivial jumps from canonicalized statement list */
			RemoveTrivialJumps tjRemover = new RemoveTrivialJumps(list);
			list = tjRemover.stms();
			
			/* And then create the final (now canonicalized) fragment */
			CanonicalizedFragment canonFrag = new CanonicalizedFragment(list, fragments.frame, fragments.labelName);
			if (firstCanonFrag == null) {
				firstCanonFrag = canonFrag; // just keep a reference to the first fragment
				currentCanonFrag = firstCanonFrag;
			} else {
				currentCanonFrag.next = canonFrag;
				currentCanonFrag = (CanonicalizedFragment) currentCanonFrag.next;
			}
			
			if (debugPrint) {
				System.out.println();
				Print printer = new Print(System.out);

				while (list != null) {
					printer.prStm(list.head);
					list = list.tail;
				}

				/* Extra newline after each fragment */
				System.out.println();
			}
			
			fragments = (SimpleFragment) fragments.next;
		}

		return firstCanonFrag;
	}
}
