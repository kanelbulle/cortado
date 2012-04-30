package se.cortado.x86_64;

import java.util.ArrayList;
import java.util.List;

import se.cortado.assem.Instr;
import se.cortado.assem.OPER;
import se.cortado.ir.temp.Label;
import se.cortado.ir.temp.LabelList;
import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempList;
import se.cortado.ir.tree.BINOP;
import se.cortado.ir.tree.CALL;
import se.cortado.ir.tree.CJUMP;
import se.cortado.ir.tree.CONST;
import se.cortado.ir.tree.EXP;
import se.cortado.ir.tree.IR_Exp;
import se.cortado.ir.tree.IR_ExpList;
import se.cortado.ir.tree.IR_Stm;
import se.cortado.ir.tree.JUMP;
import se.cortado.ir.tree.LABEL;
import se.cortado.ir.tree.MEM;
import se.cortado.ir.tree.MOVE;
import se.cortado.ir.tree.NAME;
import se.cortado.ir.tree.TEMP;

public class Codegen {
	private List<Instr>	ilist;

	public Codegen() {
		ilist = new ArrayList<Instr>();
	}

	public List<Instr> codegen(IR_Stm s) {
		munchStm(s);

		return ilist;
	}

	private void emit(Instr inst) {
		ilist.add(inst);
	}

	private TempList L(Temp h) {
		return new TempList(h, null);
	}

	private TempList L(Temp h, TempList t) {
		return new TempList(h, t);
	}

	private void munchStm(IR_Stm s) {
		if (s instanceof MOVE) {
			MOVE m = (MOVE) s;
			munchMOVE(m.dst, m.src);
		} else if (s instanceof EXP) {
			EXP e = (EXP) s;
			munchEXP(e.exp);
		} else if (s instanceof JUMP) {
			JUMP j = (JUMP) s;
			munchJUMP(j.exp, j.targets);
		} else if (s instanceof CJUMP) {
			CJUMP cj = (CJUMP) s;
			munchCJUMP(cj.relop, cj.left, cj.right, cj.iftrue, cj.iffalse);
		} else if (s instanceof LABEL) {
			LABEL l = (LABEL) s;
			munchLABEL(l.label);
		}
	}

	/*
	 * munchMOVE
	 */
	private void munchMOVE(IR_Exp dst, IR_Exp src) {
		if (dst instanceof MEM && src instanceof CONST) {
			// covers 6 nodes
			MEM memDst = (MEM) dst;
			CONST constSrc = (CONST) src;
			if (memDst.exp instanceof BINOP) {
				BINOP b = (BINOP) memDst.exp;
				if (b.left instanceof TEMP && b.right instanceof CONST) {
					TEMP t = (TEMP) b.left;
					CONST c = (CONST) b.right;

					if (b.binop == BINOP.PLUS) {
						String str = String.format("movl $%d, %d(`d0)", constSrc.value, c.value);
						emit(new OPER(str, L(t.temp, null), null));
					} else if (b.binop == BINOP.MINUS) {
						String str = String.format("movl $%d, -%d(`d0)", constSrc.value, c.value);
						emit(new OPER(str, L(t.temp, null), null));
					}
				}
			}
		} else if (dst instanceof TEMP && src instanceof CONST) {
			// covers 3 nodes
			TEMP t = (TEMP) dst;
			CONST c = (CONST) src;
			emit(new se.cortado.assem.MOVE("movl $" + c.value + "`d0", t.temp, null));
		} else {
			// covers 3 nodes
			Temp dstTemp = munchExp(dst);
			Temp srcTemp = munchExp(src);
			emit(new OPER("movl d0, s0", L(dstTemp, null), L(srcTemp, null)));
		}
	}

	/*
	 * munchEXP
	 */
	private void munchEXP(IR_Exp exp) {
		munchExp(exp);
	}

	/*
	 * munchJUMP
	 */
	private void munchJUMP(IR_Exp exp, LabelList ll) {
		if (exp instanceof NAME) {
			// covers 2 nodes
			NAME n = (NAME) exp;
			emit(new OPER("jmp " + n.label.toString(), null, null));
		} else {
			// covers 2 nodes
			Temp t = munchExp(exp);
			emit(new OPER("jmp `s0", null, L(t)));
		}
	}

	/*
	 * munchCJUMP
	 */
	private void munchCJUMP(int relop, IR_Exp left, IR_Exp right, Label iftrue, Label iffalse) {

	}

	/*
	 * munchLABEL
	 */
	private void munchLABEL(Label label) {

	}

	private Temp munchExp(IR_Exp e) {
		if (e instanceof CALL) {
			CALL c = (CALL) e;
			return munchCALL(c.func, c.args);
		} else if (e instanceof TEMP) {
			TEMP t = (TEMP) e;
			return munchTEMP(t.temp);
		} else if (e instanceof CONST) {
			CONST c = (CONST) e;
			return munchCONST(c.value);
		} else if (e instanceof BINOP) {
			BINOP b = (BINOP) e;
			return munchBINOP(b.binop, b.left, b.right);
		} else if (e instanceof MEM) {
			MEM m = (MEM) e;
			return munchMEM(m.exp);
		} else if (e instanceof NAME) {
			NAME n = (NAME) e;
			return munchNAME(n.label);
		}

		throw new Error("Found no match in munchExp for e: " + e);
	}

	/*
	 * munchCALL
	 */
	private Temp munchCALL(IR_Exp func, IR_ExpList args) {
		return null;
	}

	/*
	 * munchTEMP
	 */
	private Temp munchTEMP(Temp temp) {
		return temp;
	}

	/*
	 * munchCONST
	 */
	private Temp munchCONST(int value) {
		// move the value in const to a temp
		Temp t = new Temp();
		emit(new se.cortado.assem.MOVE("movq $" + value + ", `d0", t, null));
		return t;
	}

	/*
	 * munchBINOP
	 */
	private Temp munchBINOP(int binop, IR_Exp left, IR_Exp right) {
		if (left instanceof TEMP && right instanceof CONST) {
			Temp t = munchExp(left);
			CONST c = (CONST) right;

		}

		return null;
	}

	/*
	 * munchMEM
	 */
	private Temp munchMEM(IR_Exp exp) {
		// this assumes that the cases where MEM is left child of a MOVE has
		// already been munched => this MEM is definitely a FETCH
		Temp resTemp = null;
		if (exp instanceof BINOP && (((BINOP) exp).binop == BINOP.PLUS || ((BINOP) exp).binop == BINOP.MINUS) && ((BINOP) exp).left instanceof TEMP
				&& ((BINOP) exp).right instanceof CONST) {
			// covers 4 nodes
			BINOP b = (BINOP) exp;
			TEMP t = (TEMP) b.left;
			CONST c = (CONST) b.right;
			String assem = String.format("movl %s%d(`s0), `d0", (b.binop == BINOP.PLUS ? "" : "-"), c.value);

			resTemp = new Temp();
			emit(new OPER(assem, L(resTemp), L(t.temp)));
		} else if (exp instanceof TEMP) {
			// covers 2 nodes
			TEMP srcTemp = (TEMP) exp;
			resTemp = new Temp();
			emit(new se.cortado.assem.MOVE("movl `s0, `d0", resTemp, srcTemp.temp));
		} else {
			// covers 2 nodes
			Temp t = munchExp(exp);
			emit(new se.cortado.assem.MOVE("movl `(s0), `d0", resTemp, t));
		}
		
		return resTemp;
	}

	/*
	 * munchNAME
	 */
	private Temp munchNAME(Label label) {
		
		return null;
	}

}
