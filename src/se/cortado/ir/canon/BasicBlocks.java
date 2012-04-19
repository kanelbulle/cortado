package se.cortado.ir.canon;

import se.cortado.ir.temp.*;
import se.cortado.ir.tree.*;

public class BasicBlocks {
	public StmListList blocks;
	public Label done;

	private StmListList lastBlock;
	private IR_StmList lastStm;

	private void addStm(IR_Stm s) {
		lastStm = lastStm.tail = new IR_StmList(s,null);
	}

	private void doStms(IR_StmList l) {
		if (l==null) 
			doStms(new IR_StmList(new JUMP(done), null));
		else if (l.head instanceof JUMP || l.head instanceof CJUMP) {
			addStm(l.head);
			mkBlocks(l.tail);
		} 
		else if (l.head instanceof LABEL)
			doStms(new IR_StmList(new JUMP(((LABEL)l.head).label), l));
		else {
			addStm(l.head);
			doStms(l.tail);
		}
	}

	void mkBlocks(IR_StmList l) {
		if (l==null) return;
		else if (l.head instanceof LABEL) {
			lastStm = new IR_StmList(l.head,null);
			if (lastBlock==null)
				lastBlock= blocks= new StmListList(lastStm,null);
			else
				lastBlock = lastBlock.tail = new StmListList(lastStm,null);
			doStms(l.tail);
		}
		else mkBlocks(new IR_StmList(new LABEL(new Label()), l));
	}


	public BasicBlocks(IR_StmList stms) {
		done = new Label();
		mkBlocks(stms);
	}
}
