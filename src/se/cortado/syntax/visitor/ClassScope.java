package se.cortado.syntax.visitor;

import java.util.HashMap;

import se.cortado.syntaxtree.FormalList;
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
			FormalList otherParams = methods.get(method.identifier.s).getParameterList();
			// TODO: Check signature
			if (otherParams.size() == method.formalList.size()) {
				boolean diff = false;
				for (int i = 0; i < method.formalList.size(); ++i) {
					if ( ! method.formalList.elementAt(i).t.equals(otherParams.elementAt(i))) {
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
}
