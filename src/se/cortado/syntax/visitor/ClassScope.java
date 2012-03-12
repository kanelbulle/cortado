package se.cortado.syntax.visitor;

import java.util.HashMap;

import se.cortado.syntaxtree.Type;

public class ClassScope {
	private HashMap<String, String> variables;
	private HashMap<String, MethodScope> methods;
	
	public ClassScope() {
		variables = new HashMap<String, String>();
		methods = new HashMap<String, MethodScope>();
	}
	
	public void addVariable(String identifier, Type type) throws Exception {
		if (variables.containsKey(identifier)) {
			throw new Exception("Redeclaration of local class variable");
		} else {
			// TODO: type should be string
			variables.put(identifier, type.toString());
		}
	}
	
	public void addMethod(String identifier, MethodScope methodScope) throws Exception {
		if (methods.containsKey(identifier)) {
			throw new Exception("Redeclaration class method");
		} else {
			methods.put(identifier, methodScope);
		}
	}
}
