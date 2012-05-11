package se.cortado.x86_64;

import java.util.ArrayList;
import java.util.List;

import se.cortado.assem.Instr;
import se.cortado.assem.OPER;
import se.cortado.frame.Access;
import se.cortado.frame.Frame;
import se.cortado.ir.temp.DefaultMap;
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
import se.cortado.ir.tree.IR_StmList;
import se.cortado.ir.tree.JUMP;
import se.cortado.ir.tree.LABEL;
import se.cortado.ir.tree.MEM;
import se.cortado.ir.tree.MOVE;
import se.cortado.ir.tree.NAME;
import se.cortado.ir.tree.Print;
import se.cortado.ir.tree.TEMP;
import se.cortado.x86_64.frame.Hardware;

public class Codegen {
	private List<Instr>	ilist;
	private Frame		frame;

	public Codegen(Frame frame) {
		ilist = new ArrayList<Instr>();
		this.frame = frame;
	}

	public List<Instr> codegen(IR_StmList sl) {		
		while (sl.tail != null) {
			munchStm(sl.head);
			sl = sl.tail;
		}

		return ilist;
	}

	private void emit(Instr inst) {
		ilist.add(inst);
		
		inst.format(new DefaultMap());
	}

	private TempList L(Temp h) {
		return new TempList(h, null);
	}

	private TempList L(Temp t1, Temp t2) {
		return new TempList(t1, new TempList(t2, null));
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
						String str = String.format("movl $%d, %d(%%`d0)", constSrc.value, c.value);
						emit(new OPER(str, L(t.temp), null));
					} else if (b.binop == BINOP.MINUS) {
						String str = String.format("movl $%d, -%d(%%`d0)", constSrc.value, c.value);
						emit(new OPER(str, L(t.temp), null));
					}
				}
			}
		} else if (dst instanceof TEMP && src instanceof CALL) {
			// covers 3 nodes
			// function call, put result of call into the TEMP

			TEMP t = (TEMP) dst;
			CALL call = (CALL) src;

			Temp funcAddr = munchExp(call.func);
			TempList argTemps = munchArgs(0, call.args);
			emit(new OPER("callq %`s0", Hardware.calldefs, L(funcAddr, argTemps)));
			// move return value to specified temp
			emit(new se.cortado.assem.MOVE("movl %`d0, %`s0", t.temp, Hardware.RV));
		} else if (dst instanceof TEMP && src instanceof CONST) {
			// covers 3 nodes
			TEMP t = (TEMP) dst;
			CONST c = (CONST) src;
			emit(new se.cortado.assem.MOVE("movl $" + c.value + ", %`d0", t.temp, null));
		} else {
			// covers 3 nodes
			Temp dstTemp = munchExp(dst);
			Temp srcTemp = munchExp(src);
			
			if (srcTemp == null) {
				System.out.println("FASEN: " + src);
			}
			emit(new OPER("movl %`d0, %`s0", L(dstTemp), L(srcTemp)));
		}
	}

	/*
	 * munchEXP
	 */
	private void munchEXP(IR_Exp exp) {
		if (exp instanceof CALL) {
			// procedure call
			CALL call = (CALL) exp;
			Temp c = munchExp(call.func);
			TempList tl = munchArgs(0, call.args);
			emit(new OPER("callq %`s0", Hardware.calldefs, L(c, tl)));
		} else {
			munchExp(exp);
		}
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
			emit(new OPER("jmp %`s0", null, L(t)));
		}
	}

	/*
	 * munchCJUMP
	 */
	private void munchCJUMP(int relop, IR_Exp left, IR_Exp right, Label iftrue, Label iffalse) {
		String instr;
		switch (relop) {
		case CJUMP.EQ:
			instr = "je";
			break;
		case CJUMP.NE:
			instr = "jne";
			break;
		case CJUMP.LT:
			instr = "jl";
			break;
		case CJUMP.GT:
			instr = "jg";
			break;
		case CJUMP.LE:
			instr = "jle";
			break;
		case CJUMP.GE:
			instr = "jge";
			break;
		case CJUMP.ULT:
			instr = "jb";
			break;
		case CJUMP.UGT:
			instr = "ja";
			break;
		case CJUMP.ULE:
			instr = "jbe";
			break;
		case CJUMP.UGE:
			instr = "jae";
			break;
		default:
			throw new Error("Unknown relop in CJUMP: " + relop);
		}

		// CJUMP is always followed by its false label
		// cmpl seems to have the format 'cmpl operand2 operand1'

		LabelList destinations = new LabelList(iftrue, new LabelList(iffalse));
		if (left instanceof MEM && right instanceof CONST && ((MEM) left).exp instanceof BINOP && ((BINOP) ((MEM) left).exp).left instanceof TEMP
				&& ((BINOP) ((MEM) left).exp).right instanceof CONST
				&& (((BINOP) ((MEM) left).exp).binop == BINOP.PLUS
				|| ((BINOP) ((MEM) left).exp).binop == BINOP.MINUS)) {
			// covers 6 nodes
			MEM l = (MEM) left;
			CONST r = (CONST) right;
			BINOP b = (BINOP) l.exp;
			TEMP bl = (TEMP) b.left;
			CONST br = (CONST) b.right;

			String bop = b.binop == BINOP.PLUS ? "" : "-";
			String assem = String.format("cmpl $%d, %s%d(%%`s0)", r.value, bop, br.value);
			emit(new OPER(assem, null, L(bl.temp)));
			assem = instr + " " + iftrue.toString();
			emit(new OPER(assem, null, null, destinations));
		} else if (left instanceof TEMP && right instanceof CONST) {
			// covers 3 nodes
			TEMP l = (TEMP) left;
			CONST c = (CONST) right;

			String assem = String.format("cmpl $%d, %%`s0", c.value);
			emit(new OPER(assem, null, L(l.temp)));
			assem = instr + " " + iftrue.toString();
			emit(new OPER(assem, null, null, destinations));
		} else if (left instanceof TEMP && right instanceof TEMP) {
			// covers 3 nodes
			TEMP l = (TEMP) left;
			TEMP r = (TEMP) right;

			emit(new OPER("cmpl %`s0, %`s1", null, L(l.temp, r.temp)));
			String assem = instr + " " + iftrue.toString();
			emit(new OPER(assem, null, null, destinations));
		} else {
			// covers 1 node (kinda)
			Temp l = munchExp(left);
			Temp r = munchExp(right);

			emit(new OPER("cmpl %`s0, %`s1", null, L(l, r)));
			String assem = instr + " " + iftrue.toString();
			emit(new OPER(assem, null, null, destinations));
		}
	}

	/*
	 * munchLABEL
	 */
	private void munchLABEL(Label label) {
		emit(new se.cortado.assem.LABEL(label + ":", label));
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
		Temp t = new Temp();
		
		Temp funcAddr = munchExp(func);
		TempList argTemps = munchArgs(0, args);
		emit(new OPER("callq %`s0", Hardware.calldefs, L(funcAddr, argTemps)));
		// move return value to specified temp
		emit(new se.cortado.assem.MOVE("movl %`d0, %`s0", t, Hardware.RV));
		
		return t;
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
		emit(new se.cortado.assem.MOVE("movq $" + value + ", %`d0", t, null));
		return t;
	}

	/*
	 * munchBINOP
	 */
	private Temp munchBINOP(int binop, IR_Exp left, IR_Exp right) {
		String instr;
		switch (binop) {
		case BINOP.PLUS:
			instr = "addl";
			break;
		case BINOP.MINUS:
			instr = "subl";
			break;
		case BINOP.MUL:
			instr = "imull";
			break;
		case BINOP.DIV:
			instr = "idivl";
			break;
		case BINOP.AND:
			instr = "andl";
			break;
		case BINOP.OR:
			instr = "orl";
			break;
		case BINOP.LSHIFT:
			instr = "shll";
			break;
		case BINOP.RSHIFT:
			instr = "shrl";
			break;
		case BINOP.ARSHIFT:
			instr = "sarl";
			break;
		case BINOP.XOR:
			instr = "xorl";
			break;
		default:
			throw new Error("Unknown binop: " + binop);
		}

		// FIXME 'idivl' behaves different compared to the other ops

		if (left instanceof TEMP && right instanceof CONST) {
			// covers 3 nodes
			Temp t = munchExp(left);
			CONST c = (CONST) right;

			String assem = String.format("%s $%d, %%`d0", instr, c.value);
			emit(new OPER(assem, L(t), L(t)));
			return t;
		}
		if (left instanceof TEMP && right instanceof TEMP) {
			// covers 3 nodes
			TEMP l = (TEMP) left;
			TEMP r = (TEMP) right;

			String assem = String.format("%s %%`s0, %%`d0", instr);
			emit(new OPER(assem, L(l.temp), L(r.temp)));
			return l.temp;
		} else {
			// covers 1 node
			Temp l = munchExp(left);
			Temp r = munchExp(right);

			String assem = String.format("%s %%`s0, %%`d0", instr);
			emit(new OPER(assem, L(l), L(r)));
			return l;
		}
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
			String assem = String.format("movl %s%d(%%`s0), `d0", (b.binop == BINOP.PLUS ? "" : "-"), c.value);

			resTemp = new Temp();
			emit(new OPER(assem, L(resTemp), L(t.temp)));
		} else if (exp instanceof TEMP) {
			// covers 2 nodes
			TEMP srcTemp = (TEMP) exp;
			resTemp = new Temp();
			emit(new se.cortado.assem.MOVE("movl %`s0, %`d0", resTemp, srcTemp.temp));
		} else {
			// covers 2 nodes
			Temp t = munchExp(exp);
			resTemp = new Temp();
			emit(new se.cortado.assem.MOVE("movl (%`s0), %`d0", resTemp, t));
		}

		return resTemp;
	}

	/*
	 * munchNAME
	 */
	private Temp munchNAME(Label label) {
		Temp t = new Temp();
		emit(new OPER("movl " + label.toString() + ", %`d0", L(t), null));
		return t;
	}

	/*
	 * helpers
	 */

	private TempList munchArgs(int n, IR_ExpList list) {
		// recursive cause its funny
		if (list != null) {
			// could optimize cases where list.head is CONST or TEMP

			// munch the argument to get a temp containing the result
			Temp t = munchExp(list.head);
			// get out argument position, may be a TEMP or stack position
			Access access = frame.accessOutgoing(n);
			IR_Exp outLoc = access.exp(new TEMP(frame.FP()));

			// munchMove contains code for moving stuff into registers, reuse
			munchMOVE(outLoc, new TEMP(t));

			if (outLoc instanceof TEMP) {
				TEMP res = (TEMP) outLoc;
				return new TempList(res.temp, munchArgs(++n, list.tail));
			} else {
				return munchArgs(++n, list.tail);
			}
		} else {
			return null;
		}
	}

}
