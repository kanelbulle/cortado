package se.cortado.x86_64.frame;

import java.util.ArrayList;
import java.util.List;

import se.cortado.assem.Instr;
import se.cortado.assem.LABEL;
import se.cortado.assem.MOVE;
import se.cortado.assem.OPER;
import se.cortado.frame.Access;
import se.cortado.frame.Proc;
import se.cortado.frame.Record;
import se.cortado.ir.temp.Label;
import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempList;
import se.cortado.ir.temp.TempMap;
import se.cortado.ir.tree.CALL;
import se.cortado.ir.tree.IR_Exp;
import se.cortado.ir.tree.IR_ExpList;
import se.cortado.ir.tree.IR_Stm;
import se.cortado.ir.tree.IR_StmList;
import se.cortado.ir.tree.NAME;
import se.cortado.x86_64.Codegen;

public class Frame implements se.cortado.frame.Frame {
	List<Access>	formals			= new ArrayList<Access>();

	Label			name;
	int				numFormals;
	int				numLocals;

	final int		maxInRegArgs	= 6;
	int				maxOutgoing		= 6;

	int				maxCallParams;

	static TempList	returnSink		= new TempList(Hardware.FP, new TempList(Hardware.RV, new TempList(Hardware.SP, Hardware.calleeSavedList())));

	IR_StmList		moveArguments;

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
				a = new InFrame(wordSize() * (i - maxInRegArgs) + 2 * wordSize());
			}

			formals.add(a);
		}
	}

	@Override
	public se.cortado.frame.Frame newFrame(Label name, List<Boolean> formals) {
		return new Frame(name, formals);
	}

	@Override
	public Record newRecord(String name) {
		return new se.cortado.x86_64.frame.Record(name);
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
		int sz = wordSize() * (1 + 1 + maxOutgoing + numLocals + maxCallParams);

		// stack frame size should be 16 byte aligned
		while (sz % 16 != 0)
			sz += wordSize();

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
			return new InFrame(-size() - wordSize() * (index - maxInRegArgs));
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
		return new CALL(new NAME(new Label(func)), args);
	}

	@Override
	public IR_Stm procEntryExit1(IR_Stm body) {
		return body;
	}

	@Override
	public List<Instr> procEntryExit2(List<Instr> inst) {
		inst.add(new OPER("", null, returnSink));

		return inst;
	}

	@Override
	public Proc procEntryExit3(List<Instr> body) {
		// prologue
		// push all callee saved regs
		List<Instr> prologue = new ArrayList<Instr>();

		// append function name label
		prologue.add(new LABEL(name.toString(), name));

		// push all callee saved regs
		for (Temp t : Hardware.calleeSaves) {
			prologue.add(new OPER("pushq `s0", null, new TempList(t, null)));
		}

		// move frame pointer to stack pointer reg
		prologue.add(new MOVE("movq %`d0, %`s0", Hardware.SP, Hardware.FP));
		// decrement stack pointer
		prologue.add(new OPER("subq $" + size() + ", %`d0", new TempList(Hardware.SP, null), null));

		// epilogue
		List<Instr> epilogue = new ArrayList<Instr>();
		for (int i = Hardware.calleeSaves.length - 1; i >= 0; i--) {
			Temp t = Hardware.calleeSaves[i];
			epilogue.add(new OPER("popq `d0", new TempList(t, null), null));
		}

		// increment stack pointer
		prologue.add(new OPER("addq $" + size() + ", %`d0", new TempList(Hardware.SP, null), null));

		body.addAll(0, prologue);
		body.addAll(epilogue);

		// return
		prologue.add(new OPER("ret", null, null));

		return new Proc("PROCEDURE " + name.toString() + "\n", body, "END " + name.toString() + "\n");
	}

	@Override
	public List<Instr> codegen(IR_StmList stmList) {
		Codegen codegen = new Codegen(this);

		List<Instr> instr = codegen.codegen(stmList);

		return instr;
	}

	@Override
	public TempMap initial() {
		return this;
	}

	@Override
	public TempList registers() {
		return Hardware.registers;
	}

	/**
	 * @return the maxCallParams
	 */
	@Override
	public int getMaxCallParams() {
		return maxCallParams;
	}

	/**
	 * @param maxCallParams
	 *            the maxCallParams to set
	 */
	@Override
	public void setMaxCallParams(int maxCallParams) {
		this.maxCallParams = maxCallParams;
	}

	@Override
	public void setLabel(Label label) {
		this.name = label;
	}

	// returns null for any not precolored temporaries
	@Override
	public String tempMap(Temp t) {
		String name = Hardware.tempName(t);
		
		return name;
	}
}
