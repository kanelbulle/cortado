
package se.cortado.visitors;
import java.util.List;
import java.util.LinkedList;

import se.cortado.syntaxtree.*;
import se.cortado.ir.frame.Access;
import se.cortado.ir.frame.Frame;
import se.cortado.ir.translate.*;
import se.cortado.ir.tree.*;
import se.cortado.ir.temp.*;

/** @author Samuel Wejeus */
public class IntermediateVisitor implements TranslateVisitor {

	private LinkedList<TR_Exp> fragments = new LinkedList<TR_Exp>();
	private SymbolTable symbolTable;
	
	/* Temporaries used during construction */
	private Frame curFrame;
	private ClassDecl curClass;
	private MethodDecl curMethod;
	
	// for debug
	se.cortado.ir.tree.Print irPrinter = new se.cortado.ir.tree.Print(System.out);;
	
	public IntermediateVisitor(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}
	
	
	/* -------------- ITERATE PROGRAM/CLASSES -------------- */
	@Override
	public TR_Exp visit(Program node) {
		System.out.println("IR: Accept Program");
		return node.classDeclList.accept(this);
	}
	
	@Override
	public TR_Exp visit(ClassDeclList node) {
		System.out.println("IR: Accept ClassDeclList");
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}
		
		throw new Error("Not implemented yet!");
	}

	@Override
	public TR_Exp visit(ClassDecl node) {
//		System.out.println("IR: Accept ClassDecl: " + node.i.s);
//		node.accept(this);
		return null;
	}
	
	@Override
	public TR_Exp visit(MainClass node) {
		System.out.println("IR: Accept MainClass: " + node.i.s);
		curClass = node;
		return node.md.accept(this);
	}
	
	@Override
	public TR_Exp visit(ClassDeclSimple node) {		
		System.out.println("IR: Accept ClassDeclSimple: " + node.i.s);
		curClass = node;
		return node.ml.accept(this);
	}
	
	@Override
	public TR_Exp visit(Statement node) {
		System.out.println("IR: Accept Statement");
		return node.accept(this);
	}

	@Override
	public TR_Exp visit(StatementList node) {
		System.out.println("IR: Accept StatementList");
		
		IR_Stm res = null;
		for (int i = 0; i < node.size(); ++i) {
			TR_Exp next = node.elementAt(i).accept(this);
			
			if (res == null) {
				res = next.build_NX();
			} else {
				res = new SEQ(next.build_NX(), res);
			}
		}
		
		return new TR_Nx(res);
	}
	
	@Override
	public TR_Exp visit(Exp node) {
		System.out.println("IR: Accept Exp");
		return node.accept(this);
	}
	
	@Override
	public TR_Exp visit(ExpList node) {
		System.out.println("IR: Accept ExpList");
		
		IR_Exp res = null;
		for (int i = 0; i < node.size(); ++i) {
			TR_Exp next = node.elementAt(i).accept(this);
			
			if (res == null) {
				res = next.build_EX();
			} else {
				res = new ESEQ(next.build_NX(), res);
			}
		}
		
		return new TR_Ex(res);
	}
	
	@Override
	public TR_Exp visit(Formal node) {
		System.out.println("IR: Accept Formal");
		throw new Error("FORMAL: Not implemented yet and/or not used");
	}

	@Override
	public TR_Exp visit(FormalList node) {
		System.out.println("IR: Accept FormalList");
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}
		throw new Error("FORMAL LIST: Not implemented yet and/or not used");
	}
	
	// TODO: What is exp in node here?
	@Override
	public TR_Exp visit(MethodDecl node) {
		System.out.println("IR: Method call: " + node.identifier.s);
		
		curMethod = node;
		curFrame = symbolTable.getFrame(curClass, curMethod);
		
        // TODO: Handle 'this' pointer?
		// TODO: gen prolog?
		// TODO: gen epilog?
		
		TR_Exp fragment = node.statementList.accept(this);
		
		// Debug out
		irPrinter.prStm(fragment.build_NX());
		
		return fragment;
	}

	@Override
	public TR_Exp visit(MethodDeclList node) {
		System.out.println("IR: Accept MethodDeclList");
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}

		// TODO: Add method fragment to list of fragments
		throw new Error("End of MethodDeclList, fragments not implemented yet.");
	}
	/* -------------- /ITERATE PROGRAM/CLASSES ------------- */

	
	@Override
	public TR_Exp visit(While node) {
		System.out.println("IR: While");
		
		Label testLabel = new Label();
		Label doneLabel = new Label();
		Label body = new Label();
		
		TR_Exp condition = node.e.accept(this);
		TR_Exp body2 = node.s.accept(this);
		
		
		IR_Stm r = new SEQ(
					new LABEL(testLabel), 
						new SEQ(condition.build_CX(body, doneLabel),
							new SEQ(
								new LABEL(body),
									new SEQ(body2.build_NX(),
										new SEQ(new JUMP(testLabel), new LABEL(doneLabel))
									)
							)
						)
					);

		return new TR_Nx(r);
	}
	
	/** 
	 * Assignment can be to a TEMP (temporary or register) or MEM (memory).
	 * TEMP use MOVE(TEMP t, e) - Evaluate e and move result to temporary t.
	 * MEM use MOVE(MEM(e1), e2) - Evaluate e1 yielding address a. Evaluate e2 and store wordSize byte result at address a.
	 */	
	@Override
	public TR_Exp visit(Assign node) {
		System.out.println("IR: Accept Assign");
		
		IR_Stm res;
		
		IR_Exp e1 = symbolTable.getAccess(curClass, curMethod, node.i.s).exp(new TEMP(curFrame.FP()));
		TR_Exp e2 = node.e.accept(this);
		
		if (e1 instanceof TEMP) {
			res = new MOVE(e1, e2.build_EX());
		} else {
			Temp z = new Temp();
			res = new MOVE(
					new MEM(
						new BINOP(BINOP.PLUS, new TEMP(z), e1)
					),
					e2.build_EX()
				);
		} 
		
		return new TR_Nx(res);
	}
	
	@Override
	public TR_Exp visit(IntegerLiteral node) {
		System.out.println("IR: Accept IntegerLiteral");
		return new TR_Ex(new CONST(node.i));
	}

	@Override
	public TR_Exp visit(Minus node) {
		System.out.println("IR: Accept Minus");
		
		TR_Exp e1 = node.e1.accept(this);
		TR_Exp e2 = node.e2.accept(this);		
		
		IR_Exp r = new BINOP(BINOP.MINUS, e1.build_EX(), e2.build_EX());
		return new TR_Ex(r);
	}

	@Override
	public TR_Exp visit(Plus node) {
		System.out.println("IR: Accept Plus");
		
		TR_Exp e1 = node.e1.accept(this);
		TR_Exp e2 = node.e2.accept(this);		
		
		IR_Exp r = new BINOP(BINOP.PLUS, e1.build_EX(), e2.build_EX());
		return new TR_Ex(r);
	}

	@Override
	public TR_Exp visit(Times node) {
		System.out.println("IR: Accept Times");
		TR_Exp e1 = node.e1.accept(this);
		TR_Exp e2 = node.e2.accept(this);		
		
		IR_Exp r = new BINOP(BINOP.MUL, e1.build_EX(), e2.build_EX());
		return new TR_Ex(r);
	}
	
	@Override
	public TR_Exp visit(ArrayAssign node) {
		System.out.println("IR: Accept ArrayAssign");
		throw new Error("Not implemented yet!");
	}

	@Override
	public TR_Exp visit(ArrayLength node) {
		System.out.println("IR: Accept ArrayLength");
		throw new Error("Not implemented yet!");
	}

	@Override
	public TR_Exp visit(ArrayLookup node) {
		System.out.println("IR: Accept ArrayLookup");
		throw new Error("Not implemented yet!");
	}

	@Override
	public TR_Exp visit(Block node) {
		System.out.println("IR: Accept Block");
		return node.sl.accept(this);
	}

	@Override
	public TR_Exp visit(False node) {
		System.out.println("IR: Accept False");
		return new TR_Ex(new CONST(0));
	}
	
	@Override
	public TR_Exp visit(Identifier node) {
		System.out.println("IR: Accept Identifier");
		IR_Exp res = symbolTable.getAccess(curClass, curMethod, node.s).exp(new TEMP(curFrame.FP()));
		return new TR_Ex(res);
	}

	// TODO: There is a BIG chance this is wrong.. IdentifierExp could be a function? Then we cant look it up in symbol table.
	@Override
	public TR_Exp visit(IdentifierExp node) {
		System.out.println("IR: Accept IdentifierExp");
		IR_Exp res = symbolTable.getAccess(curClass, curMethod, node.s).exp(new TEMP(curFrame.FP()));
		return new TR_Ex(res);
	}

	@Override
	public TR_Exp visit(If node) {
		System.out.println("IR: Accept If");
		
		Label T = new Label();
		Label F = new Label();
		Label join = new Label();
		
		TR_Exp condition = node.e.accept(this);
		TR_Exp thenClause = node.s1.accept(this);
		TR_Exp elseClause = node.s2.accept(this);
		
		// If statement is singleton (no else clause)
		if (elseClause == null) {
			IR_Stm res = new SEQ(condition.build_CX(T, join), 
								new SEQ(new LABEL(T),
									new SEQ(thenClause.build_NX(), new LABEL(join))
								)
							);
			return new TR_Nx(res);
		} 
		// If statement is on the form "if c then e1 else e2"
		else {
			IR_Stm res = new SEQ(condition.build_CX(T, F),
							new SEQ(new LABEL(T),
								new SEQ(thenClause.build_NX(), new SEQ(new JUMP(join),
							new SEQ(new LABEL(F),
								new SEQ(elseClause.build_NX(), 
							new LABEL(join)))))));
			return new TR_Nx(res);
		}
	}

	@Override
	public TR_Exp visit(LessThan node) {
		System.out.println("IR: Accept LessThan");

		TR_Exp e1 = node.e1.accept(this);
		TR_Exp e2 = node.e2.accept(this);

		return new TR_RelCx(CJUMP.LT, e1, e2);
	}

	@Override
	public TR_Exp visit(True node) {
		System.out.println("IR: Accept True");
		return new TR_Ex(new CONST(1));
	}
	
	@Override
	public TR_Exp visit(And node) {
		System.out.println("IR: Accept And");
		throw new Error("Not implemented yet!");
	}
	
	@Override
	public TR_Exp visit(Call node) {
		System.out.println("IR: Accept Call");

		//node.
		//IR_Exp res = new CALL(new NAME(new Label(node.c)), null);

		//return new TR_Ex(res);
		//CALL(NAME lc$m,[p,e1,e2,...,en])

//		tmpResult = new TR_Ex(new CALL(new NAME(new LABEL(node.c)), node.el));
		throw new Error("Not implemented yet!");
	}
	
	@Override
	public TR_Exp visit(NewArray node) {
		System.out.println("IR: Accept NewArray");
		throw new Error("Not implemented yet!");
	}

	@Override
	public TR_Exp visit(NewObject node) {
		System.out.println("IR: Accept NewObject");
		throw new Error("Not implemented yet!");
	}

	@Override
	public TR_Exp visit(Not node) {
		System.out.println("IR: Accept Not");
		
		// If node is boolean -> XOR with all 1 else subtract from 0
		// TODO: What does the Java semantics say about !object ?
		
		TR_Exp value = node.accept(this);
		throw new Error("Not implemented yet!");
	}

	@Override
	public TR_Exp visit(se.cortado.syntaxtree.Print node) {
		System.out.println("IR: Accept Print");
		throw new Error("Not implemented yet!");
	}

	@Override
	public TR_Exp visit(This node) {
		System.out.println("IR: Accept This");
		throw new Error("Not implemented yet!");
	}

	@Override
	public TR_Exp visit(VoidExp node) {
		System.out.println("IR: Accept VoidExp");
		throw new Error("Not implemented yet!");
	}


}
