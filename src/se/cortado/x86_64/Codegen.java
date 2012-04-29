package se.cortado.x86_64;

import java.util.ArrayList;
import java.util.List;

import se.cortado.assem.Instr;
import se.cortado.ir.temp.Label;
import se.cortado.ir.temp.LabelList;
import se.cortado.ir.temp.Temp;
import se.cortado.ir.tree.BINOP;
import se.cortado.ir.tree.CALL;
import se.cortado.ir.tree.CJUMP;
import se.cortado.ir.tree.CONST;
import se.cortado.ir.tree.ESEQ;
import se.cortado.ir.tree.EXP;
import se.cortado.ir.tree.IR_Exp;
import se.cortado.ir.tree.IR_Stm;
import se.cortado.ir.tree.JUMP;
import se.cortado.ir.tree.LABEL;
import se.cortado.ir.tree.MEM;
import se.cortado.ir.tree.MOVE;
import se.cortado.ir.tree.NAME;
import se.cortado.ir.tree.SEQ;
import se.cortado.ir.tree.TEMP;

@SuppressWarnings("unused")
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

	private void munchStm(IR_Stm s) {
		if (s instanceof MOVE) {
			MOVE m = (MOVE) s;
			munchMOVE(m.dst, m.src);
		} else if (s instanceof EXP) {

		} else if (s instanceof JUMP) {

		} else if (s instanceof CJUMP) {

		} else if (s instanceof SEQ) {

		} else if (s instanceof LABEL) {

		}
	}

	private Temp munchExp(IR_Exp e) {
		if (e instanceof CALL) {

		} else if (e instanceof TEMP) {

		} else if (e instanceof CONST) {

		} else if (e instanceof BINOP) {

		} else if (e instanceof MEM) {

		} else if (e instanceof NAME) {

		} else if (e instanceof ESEQ) {

		}

		throw new Error("Found no match in munchExp for e: " + e);
	}

	/*
	 * munchMOVE
	 */
	private void munchMOVE(IR_Exp dst, IR_Exp src) {

	}

	private void munchMOVE(MEM dst, EXP src) {

	}

	private void munchMOVE(MEM dst, BINOP src) {

	}

	/*
	 * munchEXP
	 */
	private void munchEXP(IR_Exp exp) {

	}

	/*
	 * munchJUMP
	 */
	private void munchJUMP(IR_Exp exp, LabelList ll) {

	}

	/*
	 * munchCJUMP
	 */
	private void munchCJUMP(int relop, IR_Exp left, IR_Exp right, Label iftrue, Label iffalse) {

	}

	/*
	 * munchSEQ
	 */
	private void munchSEQ(IR_Exp left, IR_Exp right) {

	}

	/*
	 * munchLABEL
	 */
	private void munchLABEL(Label label) {
		
	}
	
	/*
	 *  munchTEMP
	 */
	private Temp munchTEMP(Temp temp) {
		return null;
	}
	
	/*
	 *  munchCONST
	 */
	private Temp munchCONST(int value) {
		return null;
	}
	
	/*
	 *  munchBINOP
	 */
	private Temp munchBINOP(int binop, IR_Exp left, IR_Exp right) {
		return null;
	}
	
	/*
	 *  munchMEM
	 */
	private Temp munchMEM(IR_Exp exp) {
		return null;
	}

	/*
	 *  munchNAME
	 */
	private Temp munchNAME(Label label) {
		return null;
	}
	
	/*
	 *  munchESEQ
	 */
	private Temp munchESEQ(IR_Stm left, IR_Exp right) {
		return null;
	}
}
