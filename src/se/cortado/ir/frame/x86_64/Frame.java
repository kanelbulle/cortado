package se.cortado.ir.frame.x86_64;

import java.util.ArrayList;
import java.util.List;

import se.cortado.ir.assem.Instr;
import se.cortado.ir.frame.Access;
import se.cortado.ir.frame.Proc;
import se.cortado.ir.frame.Record;
import se.cortado.ir.temp.Label;
import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempList;
import se.cortado.ir.temp.TempMap;
import se.cortado.ir.tree.IR_Exp;
import se.cortado.ir.tree.IR_ExpList;
import se.cortado.ir.tree.IR_Stm;

public class Frame implements se.cortado.ir.frame.Frame {
	List<Access> formals = new ArrayList<Access>();

	Label name;
	int numFormals;
	int numLocals;

	final int maxInRegArgs = 6;
	int maxOutgoing = 6;

	public Frame() {

	}

	private Frame(Label name, List<Boolean> escapes) {
		this.name = name;

		// allocate the access objects for the formals
		for (int i = 0; i < escapes.size(); i++) {
			Access a;
			// first six formals get InRegs since that is how many registers
			// that are available for parameter passing
			if (i < maxInRegArgs) {
				a = new InReg(Hardware.getArgReg(i));
			} else {
				// first overflowed arg at +16, second at +24, third at +32...
				a = new InFrame(wordSize() * (i - maxInRegArgs) + 2
						* wordSize());
			}

			formals.add(a);
		}
	}

	@Override
	public se.cortado.ir.frame.Frame newFrame(Label name, List<Boolean> formals) {
		return new Frame(name, formals);
	}

	@Override
	public Record newRecord(String name) {
		return new se.cortado.ir.frame.x86_64.Record(name);
	}

	@Override
	public Label name() {
		return name;
	}

	@Override
	public int size() {
		// the size of this frame depends on the number of outgoing parameters
		// and the number of local variables

		// return address + previous rbp value + maxOutgoing + numLocal
		int sz = wordSize() * (1 + 1 + maxOutgoing + numLocals);
		return sz;
	}

	@Override
	public List<Access> formals() {
		return formals;
	}

	@Override
	public Access allocLocal(boolean escape) {
		return new InFrame(-wordSize() * (++numLocals));
	}

	@Override
	public Access accessOutgoing(int index) {
		// convert the zero-based index to one-based
		index++;

		if (index > maxOutgoing) {
			maxOutgoing = index;
		}

		if (index <= maxInRegArgs) {
			return new InReg(Hardware.getArgReg(index - 1));
		} else {
			// offset = -8, -16, -24 ...
			return new InFrame(-wordSize() * (index - maxInRegArgs));
		}
	}

	@Override
	public Temp RV() {
		return Hardware.RV;
	}

	@Override
	public Temp FP() {
		return Hardware.FP;
	}

	@Override
	public int wordSize() {
		return Hardware.wordSize;
	}

	@Override
	public IR_Exp externalCall(String func, IR_ExpList args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IR_Stm procEntryExit1(IR_Stm body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Instr> procEntryExit2(List<Instr> inst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Proc procEntryExit3(List<Instr> body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Instr> codegen(IR_Stm stm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TempMap initial() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TempList registers() {
		// TODO Auto-generated method stub
		return null;
	}

}
