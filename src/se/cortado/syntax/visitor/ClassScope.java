package se.cortado.syntax.visitor;

import java.util.HashMap;

import se.cortado.syntaxtree.FormalList;
import se.cortado.syntaxtree.MethodDecl;
import se.cortado.syntaxtree.Type;
import se.cortado.syntaxtree.VarDecl;

public class ClassScope {
	private HashMap<String, Type> variables;
	private HashMap<String, MethodScope> methods;
	
	public ClassScope() {
		variables = new HashMap<String, Type>();
		methods = new HashMap<String, MethodScope>();
	}
	
	public void addVariable(VarDecl variable, Type type) throws Exception {
		if (variables.containsKey(variable.identifier.s)) {
			throw new Exception("Redeclaration of local class variable \"" + variable.identifier + "\" on line: " + variable.identifier.row);
		} else {
			// TODO: type should be string
			variables.put(variable.identifier.s, type);
		}
	}
	
	public void addMethod(MethodDecl method, MethodScope methodScope) throws Exception {
		if (methods.containsKey(method.identifier.s)) {
			FormalList otherParams = methods.get(method.identifier.s).getParameterList();
			/* Check signature */
			if (otherParams.size() == method.formalList.size()) {
				boolean diff = false;
				for (int i = 0; i < method.formalList.size(); ++i) {
//					System.out.println(method.formalList.elementAt(i).t.getClass() + " ### " + otherParams.elementAt(i).t.getClass());
					if ( ! method.formalList.elementAt(i).t.equals(otherParams.elementAt(i).t)) {
						diff = true;
						break;
					}
				}
				
				if (!diff) {
					throw new Exception("Redeclaration class method \"" + method.identifier + "\" on line: " + method.identifier.row);
				}
			}
		} else {
			methods.put(method.identifier.s, methodScope);
		}
	}
	
	public MethodScope getMethod(String identifier) {
		return methods.get(identifier);
	}
	
	public boolean hasVariable(String variableName) {
		return variables.get(variableName) != null;
	}
	
	public boolean hasMethod(String methodName) {
		return methods.get(methodName) != null;
	}
}
