package se.cortado.syntax.visitor;

import java.util.HashMap;

import se.cortado.syntaxtree.MethodDecl;
import se.cortado.syntaxtree.Type;
import se.cortado.syntaxtree.VarDecl;

public class ClassScope {
	private HashMap<String, String> variables;
	private HashMap<String, MethodScope> methods;
	
	public ClassScope() {
		variables = new HashMap<String, String>();
		methods = new HashMap<String, MethodScope>();
	}
	
	public void addVariable(VarDecl variable, Type type) throws Exception {
		if (variables.containsKey(variable.identifier.s)) {
			throw new Exception("Redeclaration of local class variable \"" + variable.identifier + "\" on line: " + variable.identifier.row);
		} else {
			// TODO: type should be string
			variables.put(variable.identifier.s, type.toString());
		}
	}
	
	public void addMethod(MethodDecl method, MethodScope methodScope) throws Exception {
		if (methods.containsKey(method.identifier.s)) {
			throw new Exception("Redeclaration class method \"" + method.identifier + "\" on line: " + method.identifier.row);
		} else {
			methods.put(method.identifier.s, methodScope);
		}
	}
}
