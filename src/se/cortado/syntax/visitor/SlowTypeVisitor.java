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
	ArrayList<String> errors = new ArrayList<String>();
	HashMap<String, ClassScope> symbolTable;
	ClassDecl currentClass;
	MethodDecl currentMethod;

	int currentLine;

	public void setSymbolTable(HashMap<String, ClassScope> map) {
		symbolTable = map;
	}

	/**
	 * Checks if this Type can be used as type of a variable.
	 * 
	 * @param type
	 *            The type to check.
	 * @return true if type is usable, false otherwise.
	 */
	private boolean isValidType(Type type) {

		return false;
	}

	private boolean isVariableDeclared(Identifier id) {
		ClassScope cs = symbolTable.get(currentClass.i.s);
		MethodScope ms = cs.getMethod(currentMethod.identifier.s);

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

	@Override
	public Type visit(And node) {
		node.e1.accept(this);
		node.e2.accept(this);
		return null;
	}

	@Override
	public Type visit(ArrayAssign node) {
		node.i.accept(this);
		node.e1.accept(this);
		node.e2.accept(this);
		return null;
	}

	@Override
	public Type visit(ArrayLength node) {
		node.e.accept(this);
		return null;
	}

	@Override
	public Type visit(ArrayLookup node) {
		node.e1.accept(this);
		node.e2.accept(this);
		return null;
	}

	@Override
	public Type visit(Assign node) {
		node.i.accept(this);
		node.e.accept(this);
		return null;
	}

	@Override
	public Type visit(Block node) {
		node.sl.accept(this);
		return null;
	}

	@Override
	public Type visit(BooleanType node) {
		return null;
	}

	@Override
	public Type visit(Call node) {
		node.i.accept(this);
		node.e.accept(this);
		node.el.accept(this);
		return null;
	}

	@Override
	public Type visit(ClassDecl node) {
		node.i.accept(this);
		return null;
	}

	@Override
	public Type visit(ClassDeclExtends node) {
		// TODO
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
		return null;
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
		return null;
	}

	@Override
	public Type visit(IdentifierExp node) {

		return null;
	}

	@Override
	public Type visit(IdentifierType node) {
		return null;
	}

	@Override
	public Type visit(If node) {
		node.e.accept(this);
		node.s1.accept(this);
		node.s2.accept(this);
		return null;
	}

	@Override
	public Type visit(IntArrayType node) {
		return null;
	}

	@Override
	public Type visit(IntegerLiteral node) {
		return null;
	}

	@Override
	public Type visit(IntegerType node) {
		return null;
	}

	@Override
	public Type visit(LessThan node) {
		node.e1.accept(this);
		node.e2.accept(this);
		return null;
	}

	@Override
	public Type visit(MainClass node) {
		node.i2.accept(this);
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
		node.e1.accept(this);
		node.e2.accept(this);
		return null;
	}

	@Override
	public Type visit(NewArray node) {
		node.e.accept(this);
		return null;
	}

	@Override
	public Type visit(NewObject node) {
		node.i.accept(this);
		return null;
	}

	@Override
	public Type visit(Not node) {
		node.e.accept(this);
		return null;
	}

	@Override
	public Type visit(Plus node) {
		node.e1.accept(this);
		node.e2.accept(this);
		return null;
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
		return null;
	}

	@Override
	public Type visit(This node) {
		return null;
	}

	@Override
	public Type visit(Times node) {
		if (!(node.e1.accept(this) instanceof IntegerType)) {
			errors.add("Line  Left side of '*' must be of type integer.");
		}
		if (!(node.e2.accept(this) instanceof IntegerType)) {

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
