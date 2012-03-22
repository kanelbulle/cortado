package se.cortado.syntax.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.cortado.syntaxtree.ClassDecl;
import se.cortado.syntaxtree.Formal;
import se.cortado.syntaxtree.FormalList;
import se.cortado.syntaxtree.MethodDecl;
import se.cortado.syntaxtree.Type;
import se.cortado.syntaxtree.VarDecl;

public class ClassScope {
	private HashMap<String, Type> variables;
	private HashMap<String, List<MethodScope>> methods;
	private HashMap<MethodDecl, MethodScope> methods2;
	private ClassDecl classDecl;

	public ClassScope(ClassDecl classDecl) {
		this.setClassDecl(classDecl);
		
		variables = new HashMap<String, Type>();
		methods = new HashMap<String, List<MethodScope>>();
		methods2 = new HashMap<MethodDecl, MethodScope>();
	}

	public void addVariable(VarDecl variable, Type type) throws Exception {
		if (variables.containsKey(variable.identifier.s)) {
			throw new Exception("Redeclaration of local class variable \""
					+ variable.identifier + "\" on line: "
					+ variable.identifier.row);
		} else {
			variables.put(variable.identifier.s, type);
		}
	}

	/**
	 * Adds the given method with the specified methodScope unless a method with
	 * a matching signature is already present.
	 * 
	 * @param method
	 * @param methodScope
	 * @throws Exception
	 *             if the method is already declared.
	 */
	public void addMethod(MethodDecl method, MethodScope methodScope)
			throws Exception {
		List<MethodScope> scopes = methods.get(method.identifier.s);

		if (scopes != null) {
			for (MethodScope ms : scopes) {
				FormalList otherParams = ms.getParameterList();

				if (otherParams.size() == method.formalList.size()) {
					/* Check if the two methods parameter types are identical */

					for (int i = 0; i < method.formalList.size(); ++i) {
						if (!method.formalList.elementAt(i).t
								.equals(otherParams.elementAt(i).t)) {
							throw new Exception("Redeclaration class method \""
									+ method.identifier + "\" on line: "
									+ method.identifier.row);
						}
					}
				}
			}

			// if we reach this point without an exception thrown, then we can
			// safely add the method scope to the list
			scopes.add(methodScope);
			methods2.put(method, methodScope);
		} else {
			ArrayList<MethodScope> mList = new ArrayList<MethodScope>();
			mList.add(methodScope);
			methods.put(method.identifier.s, mList);
			methods2.put(method, methodScope);
		}
	}

	public List<MethodScope> getMethods(String identifier) {
		return methods.get(identifier);
	}

	public Type getVariableType(String variableName) {
		return variables.get(variableName);
	}
	
	public HashMap<String, Type> getVariables() {
		return variables;
	}

	public boolean hasVariable(String variableName) {
		return variables.get(variableName) != null;
	}

	public boolean hasMethod(String methodName) {
		return methods.get(methodName) != null;
	}
	
	public HashMap<String, List<MethodScope>> getMethods() {
		return methods;
	}

	public MethodScope getMethodMatching(String methodName, List<Type> types) {
		List<MethodScope> scopes = methods.get(methodName);

		// loop through all methodscopes for the given methodname and compare
		// them to the list of types that was given as input. return the method
		// that matches all of the types. return null if no such method is
		// found.
		for (MethodScope ms : scopes) {
			FormalList fl = ms.getParameterList();
			if (fl.size() != types.size()) {
				continue;
			}

			boolean allEqual = true;
			for (int i = 0; i < fl.size(); i++) {
				Formal f = fl.elementAt(i);
				if (f.t.getClass() != types.get(i).getClass()) {
					allEqual = false;
					break;
				}
			}

			if (allEqual) {
				return ms;
			}
		}

		return null;
	}

	public MethodScope getMethodMatching(MethodDecl mdecl) {
		return methods2.get(mdecl);
	}

	public ClassDecl getClassDecl() {
		return classDecl;
	}

	public void setClassDecl(ClassDecl classDecl) {
		this.classDecl = classDecl;
	}

}
