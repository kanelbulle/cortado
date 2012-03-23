
package se.cortado.visitors;
import java.util.List;
import java.util.LinkedList;

import se.cortado.syntaxtree.*;
import se.cortado.ir.frame.Frame;
import se.cortado.ir.translate.*;
import se.cortado.ir.tree.*;
import se.cortado.ir.temp.*;

/** @author Samuel Wejeus */
public class IntermediateVisitor implements Visitor {

	private LinkedList fragments;
	private Frame curFrame;
	private Frame parentFrame;
	private SymbolTable symbolTable;
	
	/* Temporaries used during construction */
	private TR_Exp tmpResult;
	private ClassDecl curClass;
	private MethodDecl curMethod;
	
	//debug
	se.cortado.ir.tree.Print irPrinter;
	
	public IntermediateVisitor(SymbolTable symbolTable) {
		this.fragments = new LinkedList();
		this.parentFrame = parentFrame;
		irPrinter = new se.cortado.ir.tree.Print(System.out);
		this.symbolTable = symbolTable;
	}
	
	//translateExp(Frame f, Env e, ClassInfo c, MethodInfo m, syntaxtree.Exp node)
	private TR_Exp translate(Exp node) {
		node.accept(this);		
		return tmpResult;
    }
	
	//translateExp(Frame f, Env e, ClassInfo c, MethodInfo m, syntaxtree.Exp node)
	private TR_Exp translate(Statement node) {
		node.accept(this);
		return tmpResult;
    }
	
	@Override
	public void visit(MethodDecl node) {
		System.out.println("IR: Method call, building new frame");
		
		/* Determine which variables escape frames */
//		List<Boolean> params = new LinkedList<Boolean>();
//		for (int i = 0; i < node.formalList.size(); ++i) {
//			// No var in minijava ever escapes.
//			params.add(false);
//		}
		
		/* Create new frame and update current pointer */
//		curFrame = parentFrame.newFrame(new Label("ClassName$MethodName"), params);
		
		// TODO: Do something like:
//        for ( List<Formal> aux = node.formals; aux != null; aux = aux.tail, f = f.tail ) {
//            VarInfo v = minfo.formalsTable.get(Symbol.symbol(aux.head.name.s));
//            v.access = f.head;
//        }
        
        // TODO: Handle 'this' pointer
        
		/* Add local variables to frame */
//		node.varDeclList.accept(this);
		
		
		//node.exp.accept(this); // TODO: What is exp here?
		node.statementList.accept(this);
	}

	@Override
	public void visit(MethodDeclList node) {
		System.out.println("IR: Accept MethodDeclList");
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}
	}
	
	/** In MiniJava no variables escape. This is due to: 
		- there is no nesting of classes and methods;
		- it is not possible to take the address of a variable;
		- integers and booleans are passed by value
		- objects, including integer arrays, can be represented as pointers that are also passed by value.
	 */
	@Override
	public void visit(VarDecl node) {
		/* Minijava does not allow variable initalization (by standard) so we're of the hook here */
//		curFrame.allocLocal(false);
	}

	@Override
	public void visit(VarDeclList node) {
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}
	}

	@Override
	public void visit(Exp node) {
		
	}
	
	@Override
	public void visit(Statement node) {
		System.out.println("IR: Accept Statement");
		node.accept(this);
	}

	@Override
	public void visit(StatementList node) {
		System.out.println("IR: Accept StatementList");
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}
	}

	
	@Override
	public void visit(While node) {
		System.out.println("IR: While loop:");
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
		
		/* DEBUG */
		System.out.println("\tBuilt IR:");
		System.out.println("\t");
		irPrinter.prExp(tmpResult.build_EX());
	}
	
	
	/** 
	 * Assignment can be to a TEMP (temporary or register) or MEM (memory).
	 * TEMP use MOVE(TEMP t, e) - Evaluate e and move result to temporary t.
	 * MEM use MOVE(MEM(e1), e2) - Evaluate e1 yielding address a. Evaluate e2 and store wordSize byte result at address a.
	 */	
	@Override
	public void visit(Assign node) {

		IR_Stm r;
		IR_Exp e1 = symbolTable.getAccess(curClass, curMethod, node.i).exp(new TEMP(curFrame.FP()));
		TR_Exp e2 = translate(node.e);		
		
		if (e1 instanceof TEMP) {
			r = new MOVE(e1, e2.build_EX());
		} else {
			Temp z = new Temp();
			r = new MOVE(
					new MEM(
						new BINOP(BINOP.PLUS, new TEMP(z), e1)
					),
					e2.build_EX()
				);
		} 
		
		tmpResult = new TR_Nx(r);
		
		/* DEBUG */
		System.out.println("\tBuilt IR:");
		System.out.println("\t");
		irPrinter.prExp(tmpResult.build_EX());
	}
	
	@Override
	public void visit(IntegerLiteral node) {
		System.out.println("IR: Accept IntegerLiteral");
		tmpResult = new TR_Ex(new CONST(node.i));
	}

	@Override
	public void visit(IntegerType node) {
		System.out.println("IR: Accept IntegerType");
//		IR_Exp e1 = symbolTable.getAccess(curClass, curMethod, node.i).exp(new TEMP(curFrame.FP()));
	}

	@Override
	public void visit(Minus node) {
		TR_Exp e1 = translate(node.e1);
		TR_Exp e2 = translate(node.e2);		
		
		IR_Stm r = new MOVE(new MEM(new TEMP(new Temp())), new BINOP(BINOP.MINUS, e1.build_EX(), e2.build_EX()));
		tmpResult = new TR_Nx(r);
		
		/* DEBUG */
		System.out.println("\tBuilt IR:");
		System.out.println("\t");
		irPrinter.prExp(tmpResult.build_EX());
	}

	@Override
	public void visit(Plus node) {
		System.out.println("IR: Accept Plus");
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

	/* -------------- ITERATE PROGRAM/CLASSES -------------- */
	@Override
	public void visit(Program node) {
		System.out.println("IR: Accept Program");
		node.classDeclList.accept(this);
	}
	
	@Override
	public void visit(ClassDeclList node) {
		System.out.println("IR: Accept ClassDeclList");
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}
	}

	@Override
	public void visit(ClassDeclSimple node) {
//		  Symbol name = Symbol.symbol(node.name.s);
//        ClassInfo info = env.classes.get(name);
//        this.BuildVTable(info);
//        
//        String name = info.name.toString();
//        String[] indexes = new String[info.vtableIndex.size()];
//        
//        for ( int i = 0; i < info.vtableIndex.size(); i++ ) {
//            MethodInfo m = info.methods.get(info.vtableIndex.get(i));
//            indexes[i] = m.decorateName();
//        }
//        
//        VtableFrag frag = new VtableFrag(name, indexes);
//        info.vtable = frag.name;
//        this.addFrag(frag);
		
		System.out.println("IR: Accept ClassDeclSimple: " + node.i.s);
		node.ml.accept(this);
	}

	@Override
	public void visit(ClassDeclExtends node) {
		// TODO Auto-generated method stub
	}
	/* -------------- /ITERATE PROGRAM/CLASSES ------------- */
	
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
	
	/** (Hopefully) Corresponds to IR langugage "simple variable" */
	@Override
	public void visit(Identifier node) {
//		tmpResult = new MEM(new BINOP(BINOP.PLUS, new TEMP(frame.FP), CONST(frame.K)));

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
	public void visit(se.cortado.syntaxtree.Print node) {
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
	public void visit(VoidType node) {
		// TODO Auto-generated method stub

	}


}
