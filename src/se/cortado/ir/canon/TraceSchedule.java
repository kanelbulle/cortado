package se.cortado.ir.canon;

import se.cortado.ir.temp.*;
import se.cortado.ir.tree.*;

public class TraceSchedule {

	public IR_StmList stms;
	BasicBlocks theBlocks;
	java.util.Dictionary table = new java.util.Hashtable();

	IR_StmList getLast(IR_StmList block) {
		IR_StmList l=block;
		while (l.tail.tail!=null)  l=l.tail;
		return l;
	}

	void trace(IR_StmList l) {
		for(;;) {
			LABEL lab = (LABEL)l.head;
			table.remove(lab.label);
			IR_StmList last = getLast(l);
			IR_Stm s = last.tail.head;
			if (s instanceof JUMP) {
				JUMP j = (JUMP)s;
				IR_StmList target = (IR_StmList)table.get(j.targets.head);
				if (j.targets.tail==null && target!=null) {
					last.tail=target;
					l=target;
				}
				else {
					last.tail.tail=getNext();
					return;
				}
			}
			else if (s instanceof CJUMP) {
				CJUMP j = (CJUMP)s;
				IR_StmList t = (IR_StmList)table.get(j.iftrue);
				IR_StmList f = (IR_StmList)table.get(j.iffalse);
				if (f!=null) {
					last.tail.tail=f; 
					l=f;
				}
				else if (t!=null) {
					last.tail.head=new CJUMP(CJUMP.notRel(j.relop),
							j.left,j.right,
							j.iffalse,j.iftrue);
					last.tail.tail=t;
					l=t;
				}
				else {
					Label ff = new Label();
					last.tail.head=new CJUMP(j.relop,j.left,j.right,
							j.iftrue,ff);
					last.tail.tail=new IR_StmList(new LABEL(ff),
							new IR_StmList(new JUMP(j.iffalse),
									getNext()));
					return;
				}
			}
			else throw new Error("Bad basic block in TraceSchedule");
		}
	}

	IR_StmList getNext() {
		if (theBlocks.blocks==null) 
			return new IR_StmList(new LABEL(theBlocks.done), null);
		else {
			IR_StmList s = theBlocks.blocks.head;
			LABEL lab = (LABEL)s.head;
			if (table.get(lab.label) != null) {
				trace(s);
				return s;
			}
			else {
				theBlocks.blocks = theBlocks.blocks.tail;
				return getNext();
			}
		}
	}

	public TraceSchedule(BasicBlocks b) {
		theBlocks=b;
		for(StmListList l = b.blocks; l!=null; l=l.tail)
			table.put(((LABEL)l.head.head).label, l.head);
		stms=getNext();
		table=null;
	}        
}


