package se.cortado.syntax.visitor;

import java.util.HashMap;

import se.cortado.syntaxtree.Type;

public class MethodScope {
	private HashMap<String, String> parameters;
	private HashMap<String, String> variables;

	public MethodScope() {
		parameters = new HashMap<String, String>();
		variables = new HashMap<String, String>();
	}

	public void addParameter(String identifier, Type type) throws Exception {
		if (parameters.containsKey(identifier)) {
			throw new Exception("Duplicate parameter");
		} else {
			// FIXME
			parameters.put(identifier, type.toString());
		}
	}

	public void addVariable(String identifier, Type type) throws Exception {
		if (parameters.containsKey(identifier)) {
			throw new Exception("Redeclaration of method parameter");
		} else if (variables.containsKey(identifier)) {
			throw new Exception("Redeclaration of local variable");
		} else {
			// FIXME
			variables.put(identifier, type.toString());
		}
	}

	public boolean hasFormal(String formalName) {
		return parameters.get(formalName) != null;
	}

	public boolean hasVariable(String variableName) {
		return variables.get(variableName) != null;
	}
}
