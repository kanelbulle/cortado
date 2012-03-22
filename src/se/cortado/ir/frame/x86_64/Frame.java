package se.cortado.ir.frame.x86_64;

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

	@Override
	public se.cortado.ir.frame.Frame newFrame(Label name, List<Boolean> formals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Record newRecord(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Label name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Access> formals() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Access allocLocal(boolean escape) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Access accessOutgoing(int index) {
		// TODO Auto-generated method stub
		return null;
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
	public Temp RV() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temp FP() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int wordSize() {
		// TODO Auto-generated method stub
		return 0;
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
