package se.cortado.ir.canon;

import se.cortado.ir.tree.IR_StmList;

public class StmListList {
	public IR_StmList head;
	public StmListList tail;
	
	public StmListList(IR_StmList h, StmListList t) {
		head=h; tail=t;
	}
}

