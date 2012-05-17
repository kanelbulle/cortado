package se.cortado.ir.canon;

import se.cortado.ir.tree.*;

public class RemoveTrivialJumps
{
	IR_StmList stms;

	public IR_StmList stms() 
	{
		return stms;
	}

	public  RemoveTrivialJumps(IR_StmList s) 
	{
		stms = fix(s);	
	}

	private IR_StmList L(IR_Stm a, IR_StmList s)
	{
		return new IR_StmList(a,s);
	}

	private IR_StmList fix(IR_StmList ss)
	{
		if (ss == null) return null;

		IR_Stm h = ss.head;	
		IR_StmList t = fix(ss.tail);

		if ( t == null || ! (h instanceof JUMP && (t.head instanceof LABEL)))
			return L(h,t);

		JUMP  jmp = (JUMP)  h;

		if (jmp.targets.tail != null)  
			return L(h, t);

		String labl = ((LABEL) t.head).label.toString();
		String labj = jmp.targets.head.toString();

		if (labl == labj) {
			return t;
		}

		return L(h,t);
	}
}


