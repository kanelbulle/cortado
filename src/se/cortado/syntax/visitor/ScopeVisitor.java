/** Author: Samuel Wejeus */ 

package se.cortado.syntax.visitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

import se.cortado.syntaxtree.*;

public class ScopeVisitor implements Visitor {

	private HashMap<String, LinkedList<VarDecl>> varScope;
	private HashMap<String, LinkedList<MethodDecl>> methodScope;
	
	public ScopeVisitor() {
		varScope = new HashMap<String, LinkedList<VarDecl>>();
		methodScope = new HashMap<String, LinkedList<MethodDecl>>();
	}
	
	/* ---------------- NEW SCOPE CONSTRUCTION/DESTRUCION ---------------- */

	@Override
	public void visit(Block node) {
		
	}
	
	@Override
	public void visit(MainClass node) {
		
	}
	
	@Override
	public void visit(ClassDeclList node) {
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
		HashSet<String> curVarScope = new HashSet<String>();
		HashSet<String> curMethodScope = new HashSet<String>();
	
		/* ----------- First pass, set up table for scope testing ----------- */
		System.out.println("-- Entering class <" + node.i.s + "> --");
		
		/* Add variables */
		for (int i = 0; i < vl.size(); ++i) {
			addToScope(vl.elementAt(i), curVarScope);
		}
		
		/* Add methods */
		for (int i = 0; i < ml.size(); ++i) {
			addToScope(ml.elementAt(i), curMethodScope);
		}
		
		/* ----------- Second pass, actually test the type semantics in type scope ----------- */
		for (int i = 0; i < vl.size(); ++i) {
			// TODO
			// vl.accept(this);
		}
		
		/* Do some pretty printing */
		// TODO
		
		/* ----------- Go out of scope, remove vars/methods from current scope ----------- */
		System.out.println("-- Leaving <" + node.i.s + " --");
		
		/* Destroy scope variables */
		for (String variable : curVarScope) {
			System.out.println("\tRemoving variable: <" + variable + ">");
			varScope.get(variable).removeFirst();
		}
		
		/* Destroy scope methods */
		for (String method : curMethodScope) {
			System.out.println("\tRemoving method: <" + method + ">");
			methodScope.get(method).removeFirst();
		}
	}
	
	private void addToScope(MethodDecl method, HashSet<String> scope) {
		if (scope.contains(method.identifier.s)) {
			// TODO: throw error?
			System.out.println("ERROR: redeclaration of method <" + method.identifier.s + ">");
		} else {
			System.out.println("\tAdding method: <" + method.identifier.s + ">");
			scope.add(method.identifier.s);
		}
		
		if ( ! methodScope.containsKey(method.identifier.s)) {
			methodScope.put(method.identifier.s, new LinkedList<MethodDecl>());
		}
		
		methodScope.get(method.identifier.s).addFirst(method);
	}

	private void addToScope(VarDecl variable, HashSet<String> scope) {
		if (scope.contains(variable.identifier.s)) {
			// TODO: throw error?
			System.out.println("ERROR: redeclaration of variable <" + variable.identifier.s + ">");
		} else {
			System.out.println("\tAdding variable: <" + variable.identifier.s + ">");
			scope.add(variable.identifier.s);
		}
		
		if ( ! varScope.containsKey(variable.identifier.s)) {
			varScope.put(variable.identifier.s, new LinkedList<VarDecl>());
		}
		
		varScope.get(variable.identifier.s).addFirst(variable);
	}
	
	
	@Override
	public void visit(MethodDecl node) {
		
	}
	
	@Override
	public void visit(Program node) {
//		node.mainClass.accept(this);
		node.classDeclList.accept(this);
	}
	
	@Override
	public void visit(Statement node) {
		
	}
	
	/* ------------------------------------------------------------------- */
	
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
	public void visit(MethodDeclList node) {
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

	@Override
	public void visit(While node) {
		// TODO Auto-generated method stub
		
	}

}
