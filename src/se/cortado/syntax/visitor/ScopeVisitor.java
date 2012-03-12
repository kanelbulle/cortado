/** Author: Samuel Wejeus */ 

package se.cortado.syntax.visitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

import se.cortado.syntaxtree.*;

public class ScopeVisitor implements Visitor {
	
	private HashMap<String, ClassScope> classTable;
	private LinkedList<String> errors;
	
	private ClassDecl CUR_CLASS;
	private MethodDecl CUR_METHOD;
	
	public ScopeVisitor() {		
		classTable = new HashMap<String, ClassScope>();
		errors = new LinkedList<String>();
	}
	
	@Override
	public void visit(Program node) {
		System.out.println("-- Adding class declarations");	
		node.classDeclList.accept(this);

		/* Last: print ALL errors encounterd during type/scope check */
		if (errors.size() != 0) {
			System.out.println("\n\n=== Detected: " + errors.size() + " problem(s), please fix these and recompile ===");
			for (String error : errors) {
				System.out.println("ERROR: " + error);
			}
		}
	}
	
	@Override
	public void visit(ClassDeclList node) {
		/* Let each class handle its own scope */
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}
	}
	
	@Override
	public void visit(ClassDeclExtends node) {
		// TODO
	}
	
	@Override
	public void visit(ClassDeclSimple node) {
		if (classTable.containsKey(node.i.s)) {
			errors.add("Redeclaration of class");
		} else {
			CUR_CLASS = node;
			classTable.put(node.i.s, new ClassScope());
			node.vl.accept(this);
			node.ml.accept(this);
		}
	}
	
	@Override
	public void visit(VarDeclList node) {
		System.out.println("-- Adding variable declarations for class: " + CUR_CLASS.i.s);
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}
	}
	
	@Override
	public void visit(VarDecl node) {
		try {
			System.out.println("\t" + node.identifier.s);
			classTable.get(CUR_CLASS.i.s).addVariable(node, node.type);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
	}
	
	@Override
	public void visit(MethodDeclList node) {
		System.out.println("-- Adding method declarations for class: " + CUR_CLASS.i.s);
		for (int i = 0; i < node.size(); ++i) {
			CUR_METHOD = node.elementAt(i);
			CUR_METHOD.accept(this);
		}
	}

	// public Type id ( FormalList ) { VarDecl* Stmt* return Exp ; }
	@Override
	public void visit(MethodDecl node) {
		MethodScope ms = new MethodScope();
		System.out.println("\tMethod: " + node.identifier.s);
		
		/* Add method parameters */
		System.out.println("-- Adding parameter declarations for class: " + CUR_CLASS.i.s + " and method: " + CUR_METHOD.identifier.s);
		for (int i = 0; i < node.formalList.size(); ++i) {
			Formal curParameter = node.formalList.elementAt(i);
			try {
				System.out.println("\tParameter: " + curParameter.i.s);
				ms.addParameter(curParameter, curParameter.t);
			} catch (Exception e) {
				errors.add(e.getMessage());
			}
		}
		
		/* Add local variables */
		System.out.println("-- Adding local variables for class: " + CUR_CLASS.i.s + " and method: " + CUR_METHOD.identifier.s);
		for (int i = 0; i < node.varDeclList.size(); ++i) {
			VarDecl curVar = node.varDeclList.elementAt(i);
			try {
				System.out.println("\tVariable: " + curVar.identifier.s);
				ms.addVariable(curVar, curVar.type);
			} catch (Exception e) {
				errors.add(e.getMessage());
			}
		}
		
		try {
			classTable.get(CUR_CLASS.i.s).addMethod(node, ms);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
	}
	
	
	@Override
	public void visit(Block node) {
		
	}
	
	@Override
	public void visit(Statement node) {
		
	}

	@Override
	public void visit(StatementList node) {
		// TODO Auto-generated method stub
		
	}
	
	
	/* ------------------------------------------------------------------- */
	
	@Override
	public void visit(MainClass node) {
		
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
	public void visit(Assign node) {
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
	public void visit(Exp node) {
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
	public void visit(IntegerLiteral node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IntegerType node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LessThan node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Minus node) {
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
	public void visit(Plus node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Print node) {
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

	@Override
	public void visit(While node) {
		// TODO Auto-generated method stub
		
	}

}
