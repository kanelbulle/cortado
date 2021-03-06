package se.cortado.ir.tree;

import se.cortado.ir.temp.Label;

public class CJUMP extends IR_Stm {
	public int relop;
	public IR_Exp left, right;
	public Label iftrue, iffalse;
	public CJUMP(int rel, IR_Exp l, IR_Exp r, Label t, Label f) {
		relop=rel; left=l; right=r; iftrue=t; iffalse=f;
	}

	public final static int 
	EQ  =0, 
	NE  =1, 
	LT  =2, 
	GT  =3, 
	LE  =4, 
	GE  =5,
	ULT =6, 
	ULE =7, 
	UGT =8, 
	UGE =9;

	public IR_ExpList kids() {
		return new IR_ExpList(left, new IR_ExpList(right, null));
	}
	
	public IR_Stm build(IR_ExpList kids) {
		return new CJUMP(relop, kids.head, kids.tail.head, iftrue, iffalse);
	}
	
	public static int notRel(int relop) {
		switch (relop) {
		case EQ:  return NE;
		case NE:  return EQ;
		case LT:  return GE;
		case GE:  return LT;
		case GT:  return LE;
		case LE:  return GT;
		case ULT: return UGE;
		case UGE: return ULT;
		case UGT: return ULE;
		case ULE: return UGT;
		default: throw new Error("bad relop in CJUMP.notRel");
		}
	}
}

