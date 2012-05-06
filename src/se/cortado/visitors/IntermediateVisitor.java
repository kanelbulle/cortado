package se.cortado.visitors;

import se.cortado.frame.Access;
import se.cortado.frame.Frame;
import se.cortado.ir.temp.Label;
import se.cortado.ir.temp.Temp;
import se.cortado.ir.translate.ProcFragment;
import se.cortado.ir.translate.TR_Ex;
import se.cortado.ir.translate.TR_Nx;
import se.cortado.ir.translate.TR_RelCx;
import se.cortado.ir.translate.Translate;
import se.cortado.ir.tree.BINOP;
import se.cortado.ir.tree.CALL;
import se.cortado.ir.tree.CJUMP;
import se.cortado.ir.tree.CONST;
import se.cortado.ir.tree.ESEQ;
import se.cortado.ir.tree.EXP;
import se.cortado.ir.tree.IR_Exp;
import se.cortado.ir.tree.IR_ExpList;
import se.cortado.ir.tree.IR_Stm;
import se.cortado.ir.tree.JUMP;
import se.cortado.ir.tree.LABEL;
import se.cortado.ir.tree.MEM;
import se.cortado.ir.tree.MOVE;
import se.cortado.ir.tree.NAME;
import se.cortado.ir.tree.SEQ;
import se.cortado.ir.tree.TEMP;
import se.cortado.syntaxtree.And;
import se.cortado.syntaxtree.ArrayAssign;
import se.cortado.syntaxtree.ArrayLength;
import se.cortado.syntaxtree.ArrayLookup;
import se.cortado.syntaxtree.Assign;
import se.cortado.syntaxtree.Block;
import se.cortado.syntaxtree.Call;
import se.cortado.syntaxtree.ClassDecl;
import se.cortado.syntaxtree.ClassDeclList;
import se.cortado.syntaxtree.ClassDeclSimple;
import se.cortado.syntaxtree.Exp;
import se.cortado.syntaxtree.ExpList;
import se.cortado.syntaxtree.False;
import se.cortado.syntaxtree.Formal;
import se.cortado.syntaxtree.FormalList;
import se.cortado.syntaxtree.Identifier;
import se.cortado.syntaxtree.IdentifierExp;
import se.cortado.syntaxtree.If;
import se.cortado.syntaxtree.IntegerLiteral;
import se.cortado.syntaxtree.LessThan;
import se.cortado.syntaxtree.MainClass;
import se.cortado.syntaxtree.MethodDecl;
import se.cortado.syntaxtree.MethodDeclList;
import se.cortado.syntaxtree.Minus;
import se.cortado.syntaxtree.NewArray;
import se.cortado.syntaxtree.NewObject;
import se.cortado.syntaxtree.Not;
import se.cortado.syntaxtree.Plus;
import se.cortado.syntaxtree.Program;
import se.cortado.syntaxtree.Statement;
import se.cortado.syntaxtree.StatementList;
import se.cortado.syntaxtree.This;
import se.cortado.syntaxtree.Times;
import se.cortado.syntaxtree.True;
import se.cortado.syntaxtree.VoidExp;
import se.cortado.syntaxtree.While;

/** @author Samuel Wejeus */
public class IntermediateVisitor implements TranslateVisitor {
	private SymbolTable			symbolTable;

	/* Stored fragments */
	private ProcFragment		fragments;

	/* Temporaries used during construction */
	private Frame				curFrame;
	private ClassDecl			curClass;
	private MethodDecl			curMethod;

	private int					maxCallParams;

	private final int			wordSize	= MethodScope.getMotherFrame().wordSize();

	// for debug
	se.cortado.ir.tree.Print	irPrinter	= new se.cortado.ir.tree.Print(System.out);	;

	public IntermediateVisitor(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

	public ProcFragment getResult() {
		return fragments;
	}

	/* -------------- ITERATE PROGRAM/CLASSES -------------- */
	@Override
	public Translate visit(Program node) {
		System.out.println("IR: Accept Program");

		node.mainClass.accept(this);
		node.classDeclList.accept(this);

		return null;
	}

	@Override
	public Translate visit(ClassDeclList node) {
		System.out.println("IR: Accept ClassDeclList");
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}

		return null;
	}

	@Override
	public Translate visit(ClassDecl node) {
		// System.out.println("IR: Accept ClassDecl: " + node.i.s);
		// node.accept(this);

		throw new Error(node.getClass().getCanonicalName() + ": Not implemented yet and/or not used");
	}

	@Override
	public Translate visit(MainClass node) {
		System.out.println("IR: Accept MainClass: " + node.i.s);
		curClass = node;

		return node.md.accept(this);
	}

	@Override
	public Translate visit(ClassDeclSimple node) {
		System.out.println("IR: Accept ClassDeclSimple: " + node.i.s);
		curClass = node;

		return node.ml.accept(this);
	}

	@Override
	public Translate visit(Statement node) {
		System.out.println("IR: Accept Statement");

		return node.accept(this);
	}

	@Override
	public Translate visit(StatementList node) {
		System.out.println("IR: Accept StatementList");

		IR_Stm res = null;
		for (int i = 0; i < node.size(); ++i) {
			Translate next = node.elementAt(i).accept(this);

			if (res == null) {
				res = next.getNoValue();
			} else {
				res = new SEQ(res, next.getNoValue());
			}
		}

		// quick-fix: return a no-op if there are no statements
		if (res == null) {
			return new TR_Nx(new EXP(new CONST(0)));
		}

		return new TR_Nx(res);
	}

	@Override
	public Translate visit(Exp node) {
		System.out.println("IR: Accept Exp");

		return node.accept(this);
	}

	@Override
	public Translate visit(ExpList node) {
		System.out.println("IR: Accept ExpList");

		IR_Exp res = null;
		for (int i = 0; i < node.size(); ++i) {
			Translate next = node.elementAt(i).accept(this);

			if (res == null) {
				res = next.getValue();
			} else {
				res = new ESEQ(next.getNoValue(), res);
			}
		}

		return new TR_Ex(res);
	}

	@Override
	public Translate visit(Formal node) {
		System.out.println("IR: Accept Formal");

		throw new Error("FORMAL: Not implemented yet and/or not used");
	}

	@Override
	public Translate visit(FormalList node) {
		System.out.println("IR: Accept FormalList");
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}

		throw new Error(node.getClass().getCanonicalName() + ": Not implemented yet and/or not used");
	}

	@Override
	public Translate visit(MethodDecl node) {
		System.out.println("IR: Method call: " + node.identifier.s);

		curMethod = node;
		curFrame = symbolTable.getFrame(curClass, curMethod);

		// get method body
		Translate body = node.statementList.accept(this);

		// move return value to register
		Translate returnTranslation = node.exp.accept(this);
		if (returnTranslation == null) {
			// no return value, set zero instead
			returnTranslation = new TR_Ex(new CONST(0));
		}

		MOVE returnValue = new MOVE(new TEMP(curFrame.RV()), returnTranslation.getValue());

		IR_Stm bodyStm = curFrame.procEntryExit1(body.getNoValue());
		if (bodyStm == null) {
			// some functions have zero statements, making the body empty
			bodyStm = returnValue;
		} else {
			bodyStm = new SEQ(bodyStm, returnValue);
		}

		// get the label name for this method
		ClassScope cs = symbolTable.get(curClass.i.s);
		MethodScope ms = cs.getMethodMatching(node);
		String labelname;
		if (curClass instanceof MainClass) {
			labelname = "main";
		} else {
			labelname = curClass.i.s + "$" + ms.getLabelName();
		}

		ProcFragment fragment = new ProcFragment(bodyStm, curFrame, labelname);
		curFrame.setMaxCallParams(maxCallParams);
		maxCallParams = 0;

		fragment.next = fragments;
		fragments = fragment;

		// debug print
		// System.out.println(node.identifier.s);
		// irPrinter.prStm(bodyStm);
		// System.out.println();

		return body;
	}

	@Override
	public Translate visit(MethodDeclList node) {
		System.out.println("IR: Accept MethodDeclList");
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}

		return null;
		// throw new Error(node.getClass().getCanonicalName() +
		// ": Not implemented yet and/or not used");
	}

	/* -------------- /ITERATE PROGRAM/CLASSES ------------- */

	@Override
	public Translate visit(While node) {
		System.out.println("IR: While");

		Label testLabel = new Label();
		Label doneLabel = new Label();
		Label body = new Label();

		Translate condition = node.e.accept(this);
		Translate body2 = node.s.accept(this);

		IR_Stm r = new SEQ(new LABEL(testLabel), new SEQ(condition.getConditional(body, doneLabel), new SEQ(new LABEL(body), new SEQ(
				body2.getNoValue(), new SEQ(new JUMP(testLabel), new LABEL(doneLabel))))));

		return new TR_Nx(r);
	}

	/**
	 * Assignment can be to a TEMP (temporary or register) or MEM (memory). TEMP
	 * use MOVE(TEMP t, e) - Evaluate e and move result to temporary t. MEM use
	 * MOVE(MEM(e1), e2) - Evaluate e1 yielding address a. Evaluate e2 and store
	 * wordSize byte result at address a.
	 */
	@Override
	public Translate visit(Assign node) {
		System.out.println("IR: Accept Assign");

		IR_Stm res;

		Access variableAccess = symbolTable.getAccess(curClass, curMethod, node.i.s);
		IR_Exp e1 = variableAccess.exp(new TEMP(curFrame.FP()));
		Translate e2 = node.e.accept(this);

		if (e1 instanceof TEMP) {
			res = new MOVE(e1, e2.getValue());
		} else {
			Temp z = new Temp();
			res = new MOVE(new MEM(new BINOP(BINOP.PLUS, new TEMP(z), e1)), e2.getValue());
		}

		return new TR_Nx(res);
	}

	@Override
	public Translate visit(IntegerLiteral node) {
		System.out.println("IR: Accept IntegerLiteral");

		return new TR_Ex(new CONST(node.i));
	}

	@Override
	public Translate visit(Minus node) {
		System.out.println("IR: Accept Minus");

		Translate e1 = node.e1.accept(this);
		Translate e2 = node.e2.accept(this);

		IR_Exp r = new BINOP(BINOP.MINUS, e1.getValue(), e2.getValue());

		return new TR_Ex(r);
	}

	@Override
	public Translate visit(Plus node) {
		System.out.println("IR: Accept Plus");

		Translate e1 = node.e1.accept(this);
		Translate e2 = node.e2.accept(this);

		IR_Exp r = new BINOP(BINOP.PLUS, e1.getValue(), e2.getValue());

		return new TR_Ex(r);
	}

	@Override
	public Translate visit(Times node) {
		System.out.println("IR: Accept Times");
		Translate e1 = node.e1.accept(this);
		Translate e2 = node.e2.accept(this);

		IR_Exp r = new BINOP(BINOP.MUL, e1.getValue(), e2.getValue());

		return new TR_Ex(r);
	}

	@Override
	public Translate visit(ArrayAssign node) {
		System.out.println("IR: Accept ArrayAssign");

		Translate index = node.e1.accept(this);
		Translate array = node.i.accept(this);
		Translate assignValue = node.e2.accept(this);

		// first 'element' of array is the length
		// so address of element relative to base is 1 + wordSize * index
		IR_Exp offset = new BINOP(BINOP.MUL, index.getValue(), new CONST(wordSize));
		offset = new BINOP(BINOP.PLUS, new CONST(1), offset);
		IR_Exp address = new BINOP(BINOP.PLUS, array.getValue(), offset);

		// store rhs value at address
		MOVE move = new MOVE(new MEM(address), assignValue.getValue());

		return new TR_Nx(move);
	}

	@Override
	public Translate visit(ArrayLength node) {
		System.out.println("IR: Accept ArrayLength");

		Translate array = node.e.accept(this);

		IR_Exp length = new MEM(array.getValue());

		return new TR_Ex(length);
	}

	@Override
	public Translate visit(ArrayLookup node) {
		System.out.println("IR: Accept ArrayLookup");
		Translate array = node.e1.accept(this);
		Translate index = node.e2.accept(this);

		// TODO array bounds check here maybe?

		// first 'element' of array is the length
		// so address of element relative to base is 1 + wordSize * index
		IR_Exp offset = new BINOP(BINOP.MUL, index.getValue(), new CONST(MethodScope.getMotherFrame().wordSize()));
		offset = new BINOP(BINOP.PLUS, new CONST(1), offset);
		IR_Exp address = new BINOP(BINOP.PLUS, array.getValue(), offset);

		return new TR_Ex(new MEM(address));
	}

	@Override
	public Translate visit(Block node) {
		System.out.println("IR: Accept Block");

		return node.sl.accept(this);
	}

	@Override
	public Translate visit(False node) {
		System.out.println("IR: Accept False");

		return new TR_Ex(new CONST(0));
	}

	@Override
	public Translate visit(Identifier node) {
		System.out.println("IR: Accept Identifier");

		/*
		 * If the identifier is a local variable in a method, return the value
		 * of the variable If the identifier is a class field, return the value
		 * (address) of the variable
		 */

		// try to find access among local variables in method
		Access a = symbolTable.getAccess(curClass, curMethod, node.s);
		IR_Exp res = null;
		if (a == null) {
			// no local variable matching, check class fields
			a = symbolTable.getAccess(curClass, node.s);

			if (a == null) {
				throw new Error("Did not find identifier in method or class, something is b0rked");
			}

			Access thisAccess = symbolTable.getAccess(curClass, curMethod, "this");

			res = a.exp(thisAccess.exp(new TEMP(curFrame.FP())));
		} else {
			res = a.exp(new TEMP(curFrame.FP()));
		}

		return new TR_Ex(res);
	}

	// TODO: There is a BIG chance this is wrong.. IdentifierExp could be a
	// function? Then we cant look it up in symbol table.
	@Override
	public Translate visit(IdentifierExp node) {
		System.out.println("IR: Accept IdentifierExp");
		IR_Exp res = symbolTable.getAccess(curClass, curMethod, node.s).exp(new TEMP(curFrame.FP()));

		return new TR_Ex(res);
	}

	@Override
	public Translate visit(If node) {
		System.out.println("IR: Accept If");

		Label T = new Label();
		Label F = new Label();
		Label join = new Label();

		Translate condition = node.e.accept(this);
		Translate thenClause = node.s1.accept(this);
		Translate elseClause = node.s2.accept(this);

		// If statement is singleton (no else clause)
		if (elseClause == null) {
			IR_Stm res = new SEQ(condition.getConditional(T, join), new SEQ(new LABEL(T), new SEQ(thenClause.getNoValue(), new LABEL(join))));

			return new TR_Nx(res);
		}
		// If statement is on the form "if c then e1 else e2"
		else {
			IR_Stm res = new SEQ(condition.getConditional(T, F), new SEQ(new LABEL(T), new SEQ(thenClause.getNoValue(), new SEQ(new JUMP(join),
					new SEQ(new LABEL(F), new SEQ(elseClause.getNoValue(), new LABEL(join)))))));

			return new TR_Nx(res);
		}
	}

	@Override
	public Translate visit(LessThan node) {
		System.out.println("IR: Accept LessThan");

		Translate e1 = node.e1.accept(this);
		Translate e2 = node.e2.accept(this);

		return new TR_RelCx(CJUMP.LT, e1, e2);
	}

	@Override
	public Translate visit(True node) {
		System.out.println("IR: Accept True");

		return new TR_Ex(new CONST(1));
	}

	@Override
	public Translate visit(And node) {
		System.out.println("IR: Accept And");

		Translate e1 = node.e1.accept(this);
		Translate e2 = node.e2.accept(this);

		IR_Exp r = new BINOP(BINOP.AND, e1.getValue(), e2.getValue());

		return new TR_Ex(r);
	}

	@Override
	public Translate visit(Call node) {
		System.out.println("IR: Accept Call");

		Translate exp = node.e.accept(this);

		IR_Exp thisPointer = exp.getValue();
		IR_ExpList paramList = new IR_ExpList(thisPointer);
		for (int i = 0; i < node.el.size(); i++) {
			Exp e = node.el.elementAt(i);
			Translate t = e.accept(this);

			paramList = new IR_ExpList(t.getValue(), paramList);
		}
		
		int numParams = 1 + node.el.size(); 
		if (numParams > maxCallParams) {
			maxCallParams = numParams;
		}
			
		String s = node.cs.getClassDecl().i.s + "$" + node.ms.getLabelName();

		IR_Exp call = new CALL(new NAME(new Label(s)), paramList);

		return new TR_Ex(call);
	}

	@Override
	public Translate visit(NewArray node) {
		System.out.println("IR: Accept NewArray");

		Translate l = node.e.accept(this);
		IR_Exp numElem = new BINOP(BINOP.MUL, l.getValue(), new CONST(wordSize / 4));

		IR_ExpList args = new IR_ExpList(numElem);

		return new TR_Ex(curFrame.externalCall("_minijavalib_initarray", args));
	}

	/*
	 * 1.Generate code for allocating heap space for all the instance variables
	 * (i.e. class variable).
	 * 
	 * 2. Iterate through the memory locations for those variables and
	 * initialize them.
	 */
	@Override
	public Translate visit(NewObject node) {
		System.out.println("IR: Accept NewObject");

		ClassScope c = symbolTable.get(node.i.s);
		int heapSize = (c.getVariables().size() + 1) * curFrame.wordSize();
		IR_ExpList params = new IR_ExpList(new CONST(heapSize));
		IR_Exp allocatedHeap = curFrame.externalCall("_minijavalib_allocate", params);

		/* iterate and set to 0/null */
		IR_Stm it = null;
		for (int i = 0; i < (c.getVariables().size() + 1); ++i) {
			IR_Exp memLocation = new MEM(new BINOP(BINOP.PLUS, allocatedHeap, new CONST(i * curFrame.wordSize())));
			IR_Stm init = new MOVE(memLocation, new CONST(0));

			if (it == null) {
				it = init;
			} else {
				it = new SEQ(init, it);
			}
		}

		IR_Exp e = new ESEQ(it, allocatedHeap);

		return new TR_Ex(e);
	}

	@Override
	public Translate visit(Not node) {
		System.out.println("IR: Accept Not");

		// this assumes that booleans are exactly 0 (false) or exactly 1 (true)
		Translate t = node.e.accept(this);
		BINOP b = new BINOP(BINOP.XOR, new CONST(1), t.getValue());

		return new TR_Ex(b);
	}

	@Override
	public Translate visit(se.cortado.syntaxtree.Print node) {
		System.out.println("IR: Accept Print");

		Translate tr = node.e.accept(this);

		IR_ExpList args = new IR_ExpList(tr.getValue());
		IR_Exp call = curFrame.externalCall("_minijavalib_println", args);

		return new TR_Ex(call);
	}

	@Override
	public Translate visit(This node) {
		System.out.println("IR: Accept This");

		Access thisAccess = symbolTable.getAccess(curClass, curMethod, "this");
		// TODO returns the address of 'this', relative to what? what is the
		// basepointer?
		// this assumes that 'this' is in the heap with 0 as basepointer..?

		return new TR_Ex(thisAccess.exp(new TEMP(curFrame.FP())));
	}

	@Override
	public Translate visit(VoidExp node) {
		System.out.println("IR: Accept VoidExp");

		return new TR_Ex(new CONST(0));
	}

}
