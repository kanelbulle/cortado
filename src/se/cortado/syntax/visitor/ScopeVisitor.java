/** Author: Samuel Wejeus */ 

package se.cortado.syntax.visitor;

import java.util.HashMap;
import java.util.LinkedList;

import se.cortado.syntaxtree.And;
import se.cortado.syntaxtree.ArrayAssign;
import se.cortado.syntaxtree.ArrayLength;
import se.cortado.syntaxtree.ArrayLookup;
import se.cortado.syntaxtree.Assign;
import se.cortado.syntaxtree.Block;
import se.cortado.syntaxtree.BooleanType;
import se.cortado.syntaxtree.Call;
import se.cortado.syntaxtree.ClassDecl;
import se.cortado.syntaxtree.ClassDeclExtends;
import se.cortado.syntaxtree.ClassDeclList;
import se.cortado.syntaxtree.ClassDeclSimple;
import se.cortado.syntaxtree.Exp;
import se.cortado.syntaxtree.ExpList;
import se.cortado.syntaxtree.False;
import se.cortado.syntaxtree.Formal;
import se.cortado.syntaxtree.FormalList;
import se.cortado.syntaxtree.Identifier;
import se.cortado.syntaxtree.IdentifierExp;
import se.cortado.syntaxtree.IdentifierType;
import se.cortado.syntaxtree.If;
import se.cortado.syntaxtree.IntArrayType;
import se.cortado.syntaxtree.IntegerLiteral;
import se.cortado.syntaxtree.IntegerType;
import se.cortado.syntaxtree.LessThan;
import se.cortado.syntaxtree.MainClass;
import se.cortado.syntaxtree.MethodDecl;
import se.cortado.syntaxtree.MethodDeclList;
import se.cortado.syntaxtree.Minus;
import se.cortado.syntaxtree.NewArray;
import se.cortado.syntaxtree.NewObject;
import se.cortado.syntaxtree.Not;
import se.cortado.syntaxtree.Plus;
import se.cortado.syntaxtree.Print;
import se.cortado.syntaxtree.Program;
import se.cortado.syntaxtree.Statement;
import se.cortado.syntaxtree.StatementList;
import se.cortado.syntaxtree.StringArrayType;
import se.cortado.syntaxtree.This;
import se.cortado.syntaxtree.Times;
import se.cortado.syntaxtree.True;
import se.cortado.syntaxtree.Type;
import se.cortado.syntaxtree.VarDecl;
import se.cortado.syntaxtree.VarDeclList;
import se.cortado.syntaxtree.VoidType;
import se.cortado.syntaxtree.While;

public class ScopeVisitor implements Visitor {
	private SymbolTable symbolTable;
	private LinkedList<String> errors;
	
	public boolean errorOccurred = false;
	
	private ClassDecl CUR_CLASS;
	private MethodDecl CUR_METHOD;
	
	public ScopeVisitor(SymbolTable symbolTable) {		
		this.symbolTable = symbolTable;
		errors = new LinkedList<String>();
	}
	
	@Override
	public void visit(Program node) {

		node.mainClass.accept(this);
		node.classDeclList.accept(this);

		/* Last: print ALL errors encounterd during type/scope check */
		if (errors.size() != 0) {
			System.out.println("\n\n=== Detected: " + errors.size() + " problem(s), please fix these and recompile ===");
			for (String error : errors) {
				System.out.println("ERROR: " + error);
			}
			
			errorOccurred = true;
		}
	}
	
	@Override
	public void visit(MainClass node) {
		System.out.println("-- MAIN CLASS: " + node.i.s);
		if (symbolTable.containsKey(node.i.s)) {
			errors.add("Redeclaration of class");
		} else {
			CUR_CLASS = node;
			CUR_METHOD = node.md;
			symbolTable.put(node.i.s, new ClassScope());
			node.md.accept(this);
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
	public void visit(ClassDeclSimple node) {
		if (symbolTable.containsKey(node.i.s)) {
			errors.add("Redeclaration of class \"" + node.i.s + "\" on line: " + node.i.row);
		} else {
			CUR_CLASS = node;
			symbolTable.put(node.i.s, new ClassScope());
			node.vl.accept(this);
			node.ml.accept(this);
		}
	}
	
	@Override
	public void visit(VarDeclList node) {
		System.out.println("-- " + CUR_CLASS.i.s);
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}
	}
	
	@Override
	public void visit(VarDecl node) {
		try {
			System.out.println("\tClass variable: " + node.identifier.s);
			symbolTable.get(CUR_CLASS.i.s).addVariable(node, node.type);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
	}
	
	@Override
	public void visit(MethodDeclList node) {
		for (int i = 0; i < node.size(); ++i) {
			CUR_METHOD = node.elementAt(i);
			CUR_METHOD.accept(this);
		}
	}

	@Override
	public void visit(MethodDecl node) {
		MethodScope ms = new MethodScope(node.type);
		System.out.println("\tMethod: " + node.identifier.s);
		
		/* Add method parameters */
		for (int i = 0; i < node.formalList.size(); ++i) {
			Formal curParameter = node.formalList.elementAt(i);
			try {
				System.out.println("\t\tParameter: " + curParameter.i.s);
				ms.addParameter(curParameter, curParameter.t);
			} catch (Exception e) {
				errors.add(e.getMessage());
			}
		}
		
		/* Add local variables */
		for (int i = 0; i < node.varDeclList.size(); ++i) {
			VarDecl curVar = node.varDeclList.elementAt(i);
			try {
				System.out.println("\t\tLocal variable: " + curVar.identifier.s);
				ms.addVariable(curVar, curVar.type);
			} catch (Exception e) {
				errors.add(e.getMessage());
			}
		}
		
		// ADD STATEMENTS HERE!
		node.statementList.accept(this);
		
		try {
			symbolTable.get(CUR_CLASS.i.s).addMethod(node, ms);
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
	}
	
	
	/* ------------------------------------------------------------------- */
	
	@Override
	public void visit(ClassDeclExtends node) {
		// TODO
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
