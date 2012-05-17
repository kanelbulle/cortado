package se.cortado.ir.canon;

import se.cortado.ir.translate.ProcFragment;
import se.cortado.ir.tree.IR_StmList;
import se.cortado.ir.tree.Print;

public class Canonicalizer {
	
	boolean	debugPrint;

	public Canonicalizer(boolean print) {
		this.debugPrint = print;
	}

	public ProcFragment canonicalize(ProcFragment fragments) {
		ProcFragment first = fragments;

		while (fragments != null) {
			System.out.println(fragments.labelName);
			IR_StmList list = Canon.linearize(fragments.body);
			BasicBlocks bb = new BasicBlocks(list);
			TraceSchedule ts = new TraceSchedule(bb);
			
			list = ts.stms;
			fragments.canonicalized = list;
			
			/* Finally remove all trivial jumps from canonicalized statement list */
			RemoveTrivialJumps tjRemover = new RemoveTrivialJumps(list);
			fragments.canonicalized = list = tjRemover.stms();
			
			if (debugPrint) {
				Print printer = new Print(System.out);

				while (list != null) {
					printer.prStm(list.head);
					list = list.tail;
				}

				/* Extra newline after each fragment */
				System.out.println();
			}
			
			fragments = (ProcFragment) fragments.next;
		}

		return first;
	}
}
