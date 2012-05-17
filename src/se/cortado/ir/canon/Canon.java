package se.cortado.ir.canon;

import se.cortado.ir.temp.*;
import se.cortado.ir.tree.*;

class MoveCall extends IR_Stm {
	
	private TEMP dst;
	private CALL src;

	public MoveCall(TEMP d, CALL s) {
		dst=d; src=s;
	}

	public IR_ExpList kids() {
		return src.kids();
	}

	public IR_Stm build(IR_ExpList kids) {
		return new MOVE(dst, src.build(kids));
	}
}   

class ExpCall extends IR_Stm {
	CALL call;

	public ExpCall(CALL c) {
		call=c;
	}

	public IR_ExpList kids() {
		return call.kids();
	}

	public IR_Stm build(IR_ExpList kids) {
		return new EXP(call.build(kids));
	}
}   

final class StmExpList {
	IR_Stm stm;
	IR_ExpList exps;

	StmExpList(IR_Stm s, IR_ExpList e) {
		stm=s; exps=e;
	}
}

public class Canon {
	
	/* Local variables used */
	static StmExpList nopNull = new StmExpList(new EXP(new CONST(0)),null);
	
	static boolean isNop(IR_Stm a) {
		return a instanceof EXP && ((EXP) a).exp instanceof CONST;
	}

	static IR_Stm seq(IR_Stm a, IR_Stm b) {
		if (isNop(a)) {
			return b;
		} else if (isNop(b)) {
			return a;
		} else {
			return new SEQ(a,b);
		}
	}

	static boolean commute(IR_Stm a, IR_Exp b) {
		return isNop(a) || b instanceof NAME || b instanceof CONST;
	}

	static IR_Stm do_stm(SEQ s) { 
		return seq(do_stm(s.left), do_stm(s.right));
	}

	static IR_Stm do_stm(MOVE s) { 
		if (s.dst instanceof TEMP && s.src instanceof CALL) {
			return reorder_stm(new MoveCall((TEMP)s.dst, (CALL)s.src));
		} else if (s.dst instanceof ESEQ) {
			return do_stm(new SEQ(((ESEQ)s.dst).stm, new MOVE(((ESEQ)s.dst).exp, s.src)));
		} else {
			return reorder_stm(s);
		}
	}

	static IR_Stm do_stm(EXP s) { 
		if (s.exp instanceof CALL) {
			return reorder_stm(new ExpCall((CALL)s.exp));
		} else {
			return reorder_stm(s);
		}
	}

	static IR_Stm do_stm(IR_Stm s) {
		if (s instanceof SEQ) {
			return do_stm((SEQ) s);
		} else if (s instanceof MOVE) {
			return do_stm((MOVE) s);
		} else if (s instanceof EXP) {
			return do_stm((EXP) s);
		} else {
			return reorder_stm(s);
		}
	}

	static ESEQ do_exp(ESEQ e) {
		IR_Stm stms = do_stm(e.stm);
		ESEQ b = do_exp(e.exp);
		return new ESEQ(seq(stms,b.stm), b.exp);
	}

	static ESEQ do_exp(IR_Exp e) {
		if (e instanceof ESEQ) {
			return do_exp((ESEQ)e);
		} else {
			return reorder_exp(e);
		}
	}

	static IR_Stm reorder_stm(IR_Stm s) {
		StmExpList x = reorder(s.kids());
		return seq(x.stm, s.build(x.exps));
	}
	
	static ESEQ reorder_exp(IR_Exp e) {
		StmExpList x = reorder(e.kids());
		return new ESEQ(x.stm, e.build(x.exps));
	}

	static StmExpList reorder(IR_ExpList exps) {
		if (exps == null) {
			return nopNull;
		} else {
			IR_Exp a = exps.head;
			
			if (a instanceof CALL) {
				Temp t = new Temp();
				IR_Exp e = new ESEQ(new MOVE(new TEMP(t), a), new TEMP(t));
			
				return reorder(new IR_ExpList(e, exps.tail));
			} else {
				ESEQ aa = do_exp(a);
				StmExpList bb = reorder(exps.tail);
				if (commute(bb.stm, aa.exp)) {
					return new StmExpList(seq(aa.stm,bb.stm), new IR_ExpList(aa.exp,bb.exps));
				} else {
					Temp t = new Temp();
					IR_ExpList expList = new IR_ExpList(new TEMP(t), bb.exps);
					IR_Stm s = seq(aa.stm, seq(new MOVE(new TEMP(t),aa.exp), bb.stm));
					
					return new StmExpList(s, expList);
				}
			}
		}
	}

	static IR_StmList linear(SEQ s, IR_StmList l) {
		return linear(s.left,linear(s.right,l));
	}
	
	static IR_StmList linear(IR_Stm s, IR_StmList l) {
		if (s instanceof SEQ) return linear((SEQ)s, l);
		else return new IR_StmList(s,l);
	}

	static public IR_StmList linearize(IR_Stm s) {
		return linear(do_stm(s), null);
	}
}
