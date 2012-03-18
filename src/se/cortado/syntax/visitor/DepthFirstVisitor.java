package se.cortado.syntax.visitor;

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

/**
 * Visits the syntrax tree in depth first fashion. Takes another visitor as
 * input and calls that visitors visit methods when it encounters elements.
 * 
 * @author Emil Arfvidsson
 * 
 */
public class DepthFirstVisitor implements Visitor {
	Visitor v;

	public DepthFirstVisitor(Visitor v) {
		this.v = v;
	}

	public void start(Program node) {
		v.visit(node);

		this.visit(node);
	}

	@Override
	public void visit(And node) {
		node.e1.accept(this);
		node.e2.accept(this);

	}

	@Override
	public void visit(ArrayAssign node) {
		node.i.accept(this);
		node.e1.accept(this);
		node.e2.accept(this);

	}

	@Override
	public void visit(ArrayLength node) {
		node.e.accept(this);

	}

	@Override
	public void visit(ArrayLookup node) {
		node.e1.accept(this);
		node.e2.accept(this);

	}

	@Override
	public void visit(Assign node) {
		node.i.accept(this);
		node.e.accept(this);

	}

	@Override
	public void visit(Block node) {
		node.sl.accept(this);

	}

	@Override
	public void visit(BooleanType node) {

	}

	@Override
	public void visit(Call node) {
		node.i.accept(this);
		node.e.accept(this);
		node.el.accept(this);

	}

	@Override
	public void visit(ClassDecl node) {
		node.i.accept(this);

	}

	@Override
	public void visit(ClassDeclExtends node) {
		// TODO

	}

	@Override
	public void visit(ClassDeclList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}

	}

	@Override
	public void visit(ClassDeclSimple node) {
		node.i.accept(this);
		node.ml.accept(this);
		node.vl.accept(this);

	}

	@Override
	public void visit(Exp node) {

	}

	@Override
	public void visit(ExpList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}

	}

	@Override
	public void visit(False node) {

	}

	@Override
	public void visit(Formal node) {
		node.i.accept(this);
		node.t.accept(this);

	}

	@Override
	public void visit(FormalList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}

	}

	@Override
	public void visit(Identifier node) {

	}

	@Override
	public void visit(IdentifierExp node) {

	}

	@Override
	public void visit(IdentifierType node) {

	}

	@Override
	public void visit(If node) {
		node.e.accept(this);
		node.s1.accept(this);
		node.s2.accept(this);

	}

	@Override
	public void visit(IntArrayType node) {

	}

	@Override
	public void visit(IntegerLiteral node) {

	}

	@Override
	public void visit(IntegerType node) {

	}

	@Override
	public void visit(LessThan node) {
		node.e1.accept(this);
		node.e2.accept(this);

	}

	@Override
	public void visit(MainClass node) {
		node.i1.accept(this);
		node.i2.accept(this);
		node.md.accept(this);

	}

	@Override
	public void visit(MethodDecl node) {
		node.identifier.accept(this);
		node.type.accept(this);
		node.formalList.accept(this);
		node.varDeclList.accept(this);
		node.statementList.accept(this);
		node.exp.accept(this);

	}

	@Override
	public void visit(MethodDeclList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}

	}

	@Override
	public void visit(Minus node) {
		node.e1.accept(this);
		node.e2.accept(this);

	}

	@Override
	public void visit(NewArray node) {
		node.e.accept(this);

	}

	@Override
	public void visit(NewObject node) {
		node.i.accept(this);

	}

	@Override
	public void visit(Not node) {
		node.e.accept(this);

	}

	@Override
	public void visit(Plus node) {
		node.e1.accept(this);
		node.e2.accept(this);

	}

	@Override
	public void visit(Print node) {
		node.e.accept(this);

	}

	@Override
	public void visit(Program node) {
		node.mainClass.accept(this);
		node.classDeclList.accept(this);

	}

	@Override
	public void visit(Statement node) {

	}

	@Override
	public void visit(StatementList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}

	}

	@Override
	public void visit(StringArrayType node) {

	}

	@Override
	public void visit(This node) {

	}

	@Override
	public void visit(Times node) {
		node.e1.accept(this);
		node.e2.accept(this);

	}

	@Override
	public void visit(True node) {

	}

	@Override
	public void visit(Type node) {

	}

	@Override
	public void visit(VarDecl node) {
		node.identifier.accept(this);
		node.type.accept(this);

	}

	@Override
	public void visit(VarDeclList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}

	}

	@Override
	public void visit(VoidType node) {

	}

	@Override
	public void visit(While node) {
		node.e.accept(this);
		node.s.accept(this);

	}

}
