package se.cortado.visitors;

import java.util.HashMap;
import java.util.LinkedList;

import se.cortado.syntaxtree.Formal;
import se.cortado.syntaxtree.FormalList;
import se.cortado.syntaxtree.Type;
import se.cortado.syntaxtree.VarDecl;

/** @author Samuel Wejeus */
public class MethodScope {
	private FormalList parameters;
	private HashMap<String, Type> variables;
	
	public MethodScope() {
		parameters = new FormalList();
		variables = new HashMap<String, Type>();
	}
	
	public void addParameter(Formal param, Type type) throws Exception {
		if (parameters.contains(param.i.s)) {
			throw new Exception("Duplicate parameter \"" + param.i + "\" on line: " + param.i.row);
		} else {
			parameters.addElement(param);
		}
	}
	
	public void addVariable(VarDecl variable, Type type) throws Exception {
		if (parameters.contains(variable.identifier.s)) {
			throw new Exception("Redeclaration of method parameter");
		} else if (variables.containsKey(variable.identifier.s)) {
			throw new Exception("Redeclaration of local variable \"" + variable.identifier + "\" on line: " + variable.identifier.row);
		} else {
			//FIXME
			variables.put(variable.identifier.s, type);
		}
	}
	
	public FormalList getParameterList() {
		return parameters;
	}
}