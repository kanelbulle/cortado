/** Author: Samuel Wejeus */ 

package se.cortado.syntax.visitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

import se.cortado.syntaxtree.*;

public class ScopeVisitor implements Visitor {
	
	private HashMap<String, ClassDecl> superClassScope;	
	private HashMap<String, LinkedList<VarDecl>> superVarScope;
	private HashMap<String, LinkedList<MethodDecl>> superMethodScope;
	
	private LinkedList<String> errors;
	
	public ScopeVisitor() {
		superClassScope = new HashMap<String, ClassDecl>();
		superVarScope = new HashMap<String, LinkedList<VarDecl>>();
		superMethodScope = new HashMap<String, LinkedList<MethodDecl>>();
		errors = new LinkedList<String>();
	}
	
	@Override
	public void visit(Program node) {
//		node.mainClass.accept(this);
		
		/* First add overview of classes */
		System.out.println("-- Adding class declarations");
		for (int i = 0; i < node.classDeclList.size(); ++i) {
			addToScope(node.classDeclList.elementAt(i), superClassScope);
		}
		
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
	public void visit(MainClass node) {
		
	}
	
	@Override
	public void visit(ClassDeclList node) {
		/* Let each class handle its own scope */
		for ( int i = 0; i < node.size(); i++ ) {
			node.elementAt(i).accept(this);
		}
		
	}
	
	@Override
	public void visit(ClassDeclExtends node) {
		// TODO
	}
	
	// public Identifier i;
	// public VarDeclList vl;  
	// public MethodDeclList ml;
	@Override
	public void visit(ClassDeclSimple node) {
		
		VarDeclList vl = node.vl;
		MethodDeclList ml = node.ml;
		
		HashSet<String> classVarScope = new HashSet<String>();
		HashSet<String> classMethodScope = new HashSet<String>();
	
		/* ----------- First pass, set up table for scope testing ----------- */
		System.out.println("\n-- Entering class <" + node.i.s + ">");
		
		/* Add variables */
		for (int i = 0; i < vl.size(); ++i) {
			addToScope(vl.elementAt(i), classVarScope);
		}
		
		/* Add methods */
		if (ml.size() > 0) {
			for (int i = 0; i < ml.size(); ++i) {
				addToScope(ml.elementAt(i), classMethodScope);
			}
			
			/* For methods we need to traverse the tree further down.
			 * This must be done after all methods been added since
			 * they can call upon each other. */
			ml.accept(this);
		}
		
		/* ----------- Second pass, actually test the type semantics AND type scope ----------- */
		for (int i = 0; i < vl.size(); ++i) {
			// TODO
			// vl.accept(this);
		}
		
		/* Do some pretty printing */
		// TODO
		
		/* ----------- Go out of scope, remove vars/methods from current scope ----------- */
		System.out.println("-- Leaving <" + node.i.s + ">");
		
		/* Destroy scope variables */
		for (String variable : classVarScope) {
			System.out.println("\tRemoving variable: <" + variable + ">");
			superVarScope.get(variable).removeFirst();
		}
		
		/* Destroy scope methods */
		for (String method : classMethodScope) {
			System.out.println("\tRemoving method: <" + method + ">");
			superMethodScope.get(method).removeFirst();
		}
		
	}
	
	@Override
	public void visit(MethodDeclList node) {
		for (int i = 0; i < node.size(); ++i) {
			node.elementAt(i).accept(this);
		}
	}

	// public Type id ( FormalList ) { VarDecl* Stmt* return Exp ; }
	@Override
	public void visit(MethodDecl node) {
		// TODO: Visit Block
		// TODO: Add VarDeclList
		// TODO: Test if any called methods or used vars is in scope
	}
	
	@Override
	public void visit(Block node) {
		
	}
	
	/** HELPER */
	private boolean inScope(ClassDecl method) {
		// TODO
		return false;
	}
	
	/** HELPER */
	private boolean inScope(MethodDecl method) {
		// TODO
		return false;
	}
	
	/** HELPER */
	private boolean inScope(VarDecl method) {
		// TODO
		return false;
	}
	
	/** HELPER */
	private void addToScope(MethodDecl method, HashSet<String> scope) {
		if (scope.contains(method.identifier.s)) {
			errors.add("redeclaration of method <" + method.identifier.s + "> at row: " + method.identifier.row);
		} else {
			System.out.println("\tAdding method: <" + method.identifier.s + ">");
			scope.add(method.identifier.s);
		}
		
		if ( ! superMethodScope.containsKey(method.identifier.s)) {
			superMethodScope.put(method.identifier.s, new LinkedList<MethodDecl>());
		}
		
		superMethodScope.get(method.identifier.s).addFirst(method);
	}
	
	/** HELPER */
	private void addToScope(VarDecl variable, HashSet<String> scope) {
		if (scope.contains(variable.identifier.s)) {
			errors.add("redeclaration of variable <" + variable.identifier.s + "> at row: " + variable.identifier.row);
		} else {
			System.out.println("\tAdding variable: <" + variable.identifier.s + ">");
			scope.add(variable.identifier.s);
		}
		
		if ( ! superVarScope.containsKey(variable.identifier.s)) {
			superVarScope.put(variable.identifier.s, new LinkedList<VarDecl>());
		}
		
		superVarScope.get(variable.identifier.s).addFirst(variable);
	}
	
	/** HELPER */
	private void addToScope(ClassDecl className, HashMap<String, ClassDecl> scope) {
		
		if (scope.containsKey(className.i.s)) {
			errors.add("redeclaration of class <" + className.i.s + "> at row: " + className.i.row);
		} else {
			System.out.println("\tAdding class: <" + className.i.s + ">");
			scope.put(className.i.s, className);
		}
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
	public void visit(VarDeclList node) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visit(VarDecl node) {
		// TODO Auto-generated method stub
		
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
