package se.cortado.syntax.visitor;

import java.util.ArrayList;
import java.util.HashMap;

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

public class SlowTypeVisitor implements TypeVisitor {
	private static final String UNDECLARED_ERROR = "Undeclared identifier '%s'";
	private static final String RIGHT_SIDE_ERROR = "Right side of '%s' must be of type %s.";
	private static final String LEFT_SIDE_ERROR = "Left side of '%s' must be of type %s.";
	private static final String EXPRESSION_INSIDE_ERROR = "Expression inside of '%s' must be of type %s.";
	private static final String NO_MATCHING_METHOD = "No method with signature %s found in %s.";
	private static final String EXPECTED_TYPE_CLASS = "Expected expression of type class here.";
	private static final String EXPECTED_TYPE = "Expected expresseion of type %s here.";
	private static final String ASSIGN_TYPE_ERROR = "Left hand side (%s) does not match type of right hand side (%s).";

	ArrayList<String> errors = new ArrayList<String>();
	HashMap<String, ClassScope> symbolTable;
	ClassDecl currentClass;
	MethodDecl currentMethod;
	public boolean errorOccurred;

	int currentLine;

	public void setSymbolTable(HashMap<String, ClassScope> map) {
		symbolTable = map;
	}

	private void addError(String s, String... format) {
		errors.add("Line " + currentLine + ": "
				+ String.format(s, (Object[]) format));
	}

	private Type typeOfIdentifier(Identifier id) {
		ClassScope cs = symbolTable.get(currentClass.i.s);
		MethodScope ms = cs.getMethodMatching(currentMethod);

		// search current methods local variables
		Type t = ms.getVariableType(id);
		if (t != null) {
			return t;
		}

		// search current methods formal list
		t = ms.getFormalType(id);
		if (t != null) {
			return t;
		}

		// search fields in current class
		t = cs.getVariableType(id.s);
		if (t != null) {
			return t;
		}

		// could not find the declaration of id, return null
		return null;
	}

	private boolean isVariableDeclared(Identifier id) {
		ClassScope cs = symbolTable.get(currentClass.i.s);
		MethodScope ms = cs.getMethodMatching(currentMethod);

		/*
		 * If this identifier is declared either as a local variable in the
		 * method, or as a parameter sent into the function, or as a field in
		 * the class which this method belongs, return true. Otherwise return
		 * false.
		 */

		if (ms.hasVariable(id.s)) {
			return true;
		}
		if (ms.hasFormal(id.s)) {
			return true;
		}
		if (cs.hasVariable(id.s)) {
			return true;
		}

		return false;
	}

	private boolean isClassDeclared(Identifier id) {
		return symbolTable.get(id.s) != null;
	}

	@Override
	public Type visit(And node) {
		if (!(node.e1.accept(this) instanceof BooleanType)) {
			addError(LEFT_SIDE_ERROR, "&&", "boolean");
		}
		if (!(node.e2.accept(this) instanceof BooleanType)) {
			addError(RIGHT_SIDE_ERROR, "&&", "boolean");
		}

		return null;
	}

	@Override
	public Type visit(ArrayAssign node) {
		node.i.accept(this);
		
		Type idType = typeOfIdentifier(node.i);
		if (!(idType instanceof IntArrayType)) {
			addError(EXPECTED_TYPE, "IntegerArrayType");
		}
		if (!(node.e1.accept(this) instanceof IntegerType)) {
			addError(EXPRESSION_INSIDE_ERROR, "[]", "IntegerType");
		}
		if (!(node.e2.accept(this) instanceof IntegerType)) {
			addError(RIGHT_SIDE_ERROR, "=", "IntegerType");
		}
		
		return null;
	}

	@Override
	public Type visit(ArrayLength node) {
		if (!(node.e.accept(this) instanceof IntArrayType)) {
			addError(EXPECTED_TYPE, "IntegerArrayType");
		}
		
		return null;
	}

	@Override
	public Type visit(ArrayLookup node) {
		if (!(node.e1.accept(this) instanceof IntArrayType)) {
			addError(EXPECTED_TYPE, "IntegerArrayType");
		}
		if (!(node.e2.accept(this) instanceof IntegerType)) {
			addError(EXPRESSION_INSIDE_ERROR, "[]", "IntegerType");
		}
		
		return new IntegerType();
	}

	@Override
	public Type visit(Assign node) {
		Type lhsType = typeOfIdentifier(node.i);
		Type rhsType = node.e.accept(this);

		if (lhsType.getClass() != rhsType.getClass()) {
			addError(ASSIGN_TYPE_ERROR, lhsType.getClass().getSimpleName(),
					rhsType.getClass().getSimpleName());
		}

		return null;
	}

	@Override
	public Type visit(Block node) {
		node.sl.accept(this);
		return null;
	}

	@Override
	public Type visit(BooleanType node) {
		return new BooleanType();
	}

	@Override
	public Type visit(Call node) {
		/*
		 * This method check a few things:
		 * 
		 * 1) The Exp must be of type IdentifierExp
		 * 
		 * 2) A class with that name must have been declared
		 * 
		 * 3) The method must have the correct number of parameters and the
		 * parameters types must match one that has been declared
		 */

		node.i.accept(this);

		// evaluate the Exp to find on what class the call is
		Type classType = node.e.accept(this);
		if (!(classType instanceof IdentifierType)) {
			IdentifierType classId = (IdentifierType) classType;
			ClassScope cs = symbolTable.get(classId.s);

			if (cs == null) {
				// class undeclared
				addError(UNDECLARED_ERROR, classId.s);

				return null;
			}

			// get the types in the ExpList and store in list
			ArrayList<Type> methodTypes = new ArrayList<Type>();
			for (int i = 0; i < node.el.size(); i++) {
				Type paramType = node.el.elementAt(i).accept(this);
				methodTypes.add(paramType);
			}

			// get the method with the specified name and parameters of the
			// specified types
			MethodScope callMethod = cs
					.getMethodMatching(node.i.s, methodTypes);
			if (callMethod == null) {
				// there is no method in the specified class that fits the types
				// print error
				String types = "(";
				for (Type t : methodTypes) {
					types += t.getClass().getSimpleName() + ", ";
				}
				types = types.substring(0, types.length() - 2) + ")";

				addError(NO_MATCHING_METHOD, types, classId.s);

				return null;
			}

			// if we get here, a method that matches was found the type of this
			// expression is then the return type of the method
			return callMethod.getReturnType();
		} else {
			// expression is not of class type
			addError(EXPECTED_TYPE_CLASS);
		}

		return null;
	}

	@Override
	public Type visit(ClassDecl node) {
		node.i.accept(this);
		return null;
	}

	@Override
	public Type visit(ClassDeclExtends node) {
		node.i.accept(this);
		node.j.accept(this);
		node.ml.accept(this);
		node.vl.accept(this);
		return null;
	}

	@Override
	public Type visit(ClassDeclList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}
		return null;
	}

	@Override
	public Type visit(ClassDeclSimple node) {
		currentClass = node;

		node.i.accept(this);
		node.ml.accept(this);
		node.vl.accept(this);
		return null;
	}

	@Override
	public Type visit(Exp node) {
		return null;
	}

	@Override
	public Type visit(ExpList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}
		return null;
	}

	@Override
	public Type visit(False node) {
		return new BooleanType();
	}

	@Override
	public Type visit(Formal node) {
		node.i.accept(this);
		node.t.accept(this);
		return null;
	}

	@Override
	public Type visit(FormalList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}
		return null;
	}

	@Override
	public Type visit(Identifier node) {
		currentLine = node.row;

		return null;
	}

	@Override
	public Type visit(IdentifierExp node) {
		Identifier i = new Identifier(node.s, 0);

		// check if identifier is declared
		if (!isVariableDeclared(i)) {
			addError(UNDECLARED_ERROR, i.s);
		} else {
			// it's defined somewhere alright, return its type
			typeOfIdentifier(i);
		}

		return null;
	}

	@Override
	public Type visit(IdentifierType node) {
		return new IdentifierType(node.s);
	}

	@Override
	public Type visit(If node) {
		if (!(node.e.accept(this) instanceof BooleanType)) {
			addError(EXPRESSION_INSIDE_ERROR, "if()", "boolean");
		}
		node.s1.accept(this);
		node.s2.accept(this);
		return null;
	}

	@Override
	public Type visit(IntArrayType node) {
		return new IntArrayType();
	}

	@Override
	public Type visit(IntegerLiteral node) {
		return new IntegerType();
	}

	@Override
	public Type visit(IntegerType node) {
		return new IntegerType();
	}

	@Override
	public Type visit(LessThan node) {
		if (!(node.e1.accept(this) instanceof IntegerType)) {
			addError(LEFT_SIDE_ERROR, "<", "integer");
		}
		if (!(node.e2.accept(this) instanceof IntegerType)) {
			addError(RIGHT_SIDE_ERROR, "<", "integer");
		}

		return new BooleanType();
	}

	@Override
	public Type visit(MainClass node) {
		currentClass = node;
		
		node.i.accept(this);
		node.md.accept(this);
		return null;
	}

	@Override
	public Type visit(MethodDecl node) {
		currentMethod = node;

		node.identifier.accept(this);
		node.type.accept(this);
		node.formalList.accept(this);
		node.varDeclList.accept(this);
		node.statementList.accept(this);
		node.exp.accept(this);
		return null;
	}

	@Override
	public Type visit(MethodDeclList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}
		return null;
	}

	@Override
	public Type visit(Minus node) {
		if (!(node.e1.accept(this) instanceof IntegerType)) {
			addError(LEFT_SIDE_ERROR, "-", "integer");
		}
		if (!(node.e2.accept(this) instanceof IntegerType)) {
			addError(RIGHT_SIDE_ERROR, "-", "integer");
		}

		return new IntegerType();
	}

	@Override
	public Type visit(NewArray node) {
		if (!(node.e.accept(this) instanceof IntegerType)) {
			addError(EXPRESSION_INSIDE_ERROR, "[]", "integer");
		}

		return new IntArrayType();
	}

	@Override
	public Type visit(NewObject node) {
		node.i.accept(this);

		if (!isClassDeclared(node.i)) {
			addError(UNDECLARED_ERROR, node.i.s);
		}

		return new IdentifierType(node.i.s);
	}

	@Override
	public Type visit(Not node) {
		if (!(node.e.accept(this) instanceof BooleanType)) {
			addError(RIGHT_SIDE_ERROR, "!", "boolean");
		}

		return null;
	}

	@Override
	public Type visit(Plus node) {
		if (!(node.e1.accept(this) instanceof IntegerType)) {
			addError(LEFT_SIDE_ERROR, "+", "integer");
		}
		if (!(node.e2.accept(this) instanceof IntegerType)) {
			addError(RIGHT_SIDE_ERROR, "+", "integer");
		}

		return new IntegerType();
	}

	@Override
	public Type visit(Print node) {
		node.e.accept(this);
		return null;
	}

	@Override
	public Type visit(Program node) {
		node.mainClass.accept(this);
		node.classDeclList.accept(this);
		
		if (errors.size() > 0) {
			for (String s : errors) {
				System.out.println(s);
			}
			
			errorOccurred = true;
		}
		
		return null;
	}

	@Override
	public Type visit(Statement node) {
		return null;
	}

	@Override
	public Type visit(StatementList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}

		return null;
	}

	@Override
	public Type visit(StringArrayType node) {
		return new StringArrayType();
	}

	@Override
	public Type visit(This node) {
		return new IdentifierType(currentClass.i.s);
	}

	@Override
	public Type visit(Times node) {
		if (!(node.e1.accept(this) instanceof IntegerType)) {
			addError(LEFT_SIDE_ERROR, "*", "integer");
		}
		if (!(node.e2.accept(this) instanceof IntegerType)) {
			addError(RIGHT_SIDE_ERROR, "*", "integer");
		}

		return new IntegerType();
	}

	@Override
	public Type visit(True node) {
		return new BooleanType();
	}

	@Override
	public Type visit(Type node) {
		return null;
	}

	@Override
	public Type visit(VarDecl node) {
		node.identifier.accept(this);

		// TODO verify identifier

		node.type.accept(this);
		return null;
	}

	@Override
	public Type visit(VarDeclList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}
		return null;
	}

	@Override
	public Type visit(VoidType node) {
		return new VoidType();
	}

	@Override
	public Type visit(While node) {
		node.e.accept(this);
		node.s.accept(this);

		return null;
	}
}
