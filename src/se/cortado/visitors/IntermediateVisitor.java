
package se.cortado.visitors;
import java.util.LinkedList;

import se.cortado.syntaxtree.*;
import se.cortado.ir.translate.*;
import se.cortado.ir.tree.*;
import se.cortado.ir.temp.*;

/** @author Samuel Wejeus */
public class IntermediateVisitor implements Visitor {

	private LinkedList fragments;
	private TR_Exp tmpResult;
	
	public IntermediateVisitor() {
		fragments = new LinkedList();
	}

	//translateExp(Frame f, Env e, ClassInfo c, MethodInfo m, syntaxtree.Exp node)
	private TR_Exp translate(Exp node) {
		//ExpHandler h = new ExpHandler(f, e, c, m);
		node.accept(this);		
		return tmpResult;
    }
	
	//translateExp(Frame f, Env e, ClassInfo c, MethodInfo m, syntaxtree.Exp node)
	private TR_Exp translate(Statement node) {
		//ExpHandler h = new ExpHandler(f, e, c, m);
		node.accept(this);
		return tmpResult;
    }
	
	@Override
	public void visit(While node) {
		Label testLabel = new Label();
		Label doneLabel = new Label();
		Label body = new Label();
		
		TR_Exp condition = translate(node.e);
		TR_Exp body2 = translate(node.s);
		
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

		tmpResult = new TR_Nx(r);
	}
	
	
	/** 
	 * Assignment can be to a TEMP (temporary or register) or MEM (memory).
	 * TEMP use MOVE(TEMP t, e) - Evaluate e and move result to temporary t.
	 * MEM use MOVE(MEM(e1), e2) - Evaluate e1 yielding address a. Evaluate e2 and store wordSize byte result at address a.
	 */
	@Override
	public void visit(Assign node) {

//		TR_Exp e1 = translate(node.i);
//		TR_Exp e2 = translate(node.e);
//		IR_Stm r;
//		
//		if (e1.build_EX() instanceof TEMP) {
//			r = new MOVE(e1.build_EX(), e2.build_EX());
//		} else {
//			Temp z = new Temp();
//			r = new MOVE(
//					new MEM(
//						new BINOP(BINOP.PLUS, new TEMP(z), e1.build_EX())
//					),
//					e2.build_EX()
//				);
//		} 

	}
	
	@Override
	public void visit(IntegerLiteral node) {
		//		MEM(new BINOP(BINOP.MINUS, new Temp(), node.i));
	}

	@Override
	public void visit(IntegerType node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Exp node) {

	}

	@Override
	public void visit(Minus node) {
		//		node.e1;
		//		node.e2;
		//		MEM(new BINOP(BINOP.MINUS, new Temp(), ))
	}

	@Override
	public void visit(Plus node) {

	}


	@Override
	public void visit(And node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArrayAssign node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArrayLength node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArrayLookup node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Block node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BooleanType node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Call node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ClassDecl node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ClassDeclExtends node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ClassDeclList node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ClassDeclSimple node) {
		// TODO Auto-generated method stub

	}


	@Override
	public void visit(ExpList node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(False node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Formal node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FormalList node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Identifier node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IdentifierExp node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IdentifierType node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(If node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IntArrayType node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(LessThan node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MainClass node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MethodDecl node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MethodDeclList node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NewArray node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NewObject node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Not node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Print node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Program node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Statement node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StatementList node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StringArrayType node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(This node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Times node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(True node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Type node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VarDecl node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VarDeclList node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VoidType node) {
		// TODO Auto-generated method stub

	}





}
