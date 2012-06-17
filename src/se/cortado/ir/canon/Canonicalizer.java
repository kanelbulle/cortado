package se.cortado.ir.canon;

import se.cortado.ir.common.SimpleFragment;
import se.cortado.ir.tree.IR_StmList;
import se.cortado.ir.tree.Print;

public class Canonicalizer {
	
	boolean	debugPrint;

	public Canonicalizer(boolean print) {
		this.debugPrint = print;
	}

	public SimpleFragment canonicalize(SimpleFragment fragments) {
		SimpleFragment first = fragments;

		while (fragments != null) {

			IR_StmList list = Canon.linearize(fragments.body);
			BasicBlocks basicBlocks = new BasicBlocks(list);
			TraceSchedule traceSchedule = new TraceSchedule(basicBlocks);
			
			list = traceSchedule.stms;
//			fragments.canonicalized = list;
			
			/* Finally remove all trivial jumps from canonicalized statement list */
			RemoveTrivialJumps tjRemover = new RemoveTrivialJumps(list);
//			fragments.canonicalized = list = tjRemover.stms();
			
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

		return first;
	}
}
