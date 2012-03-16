/** ASTPrintVisitor - prints the abstract syntax tree in a constructor
 * style with parenthesized comma-separated lists. This visitor can
 * help check for the correctness of a MiniJava abstract syntax tree.
 * The implementation is based on that of PrettyPrintVisitor, and the
 * author was tempted to call it UglyPrintVisitor.
 *
 * Author: Todd Neller, Gettysburg College 1/29/04 */ 

package se.cortado.visitors;

import se.cortado.syntaxtree.*;

public class ASTPrintVisitor implements Visitor {
	int level = 0;
	
	private String indent() {
		String s = "";
		for (int i = 0; i < level; i++) {
			s += "\t";
		}
		return s;
	}

	// MainClass m;
	// ClassDeclList cl;
	public void visit(Program n) {
		System.out.println(indent() + "Program(");
		level++;
		
		n.mainClass.accept(this);
		System.out.println(", ");
		
		n.classDeclList.accept(this);
		
		level--;
		System.out.println("\n" + indent() + ")");
	}

	// Identifier i1,i2;
	// Statement s;
	public void visit(MainClass n) {
		System.out.println(indent() + "MainClass(");
		level++;
		
		System.out.print(indent());
		n.i.accept(this);
		
		System.out.println(", ");
		
		n.md.accept(this);
		
		level--;
		System.out.print("\n" + indent() + ")");
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public void visit(ClassDeclSimple n) {
		System.out.println(indent() + "ClassDeclSimple(");
		level++;
		
		System.out.print(indent());
		n.i.accept(this);
		
		if (n.vl.size() != 0) {
			System.out.println(", ");
			n.vl.accept(this);
		}
		
		if (n.ml.size() != 0) {
			System.out.println(", ");
			n.ml.accept(this);
		}
		
		level--;
		System.out.print("\n" + indent() + ")");
	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public void visit(ClassDeclExtends n) {
		System.out.print("ClassDeclExtends(");
		n.i.accept(this);
		System.out.print(", ");
		n.j.accept(this);
		System.out.print(", (");
		for ( int i = 0; i < n.vl.size(); i++ ) {
			n.vl.elementAt(i).accept(this);
			if ( i+1 < n.vl.size() ) 
				System.out.print(", ");
		}
		for ( int i = 0; i < n.ml.size(); i++ ) {
			System.out.println();
			if ( i+1 < n.ml.size() ) 
				System.out.println(indent() + ", ");
		}
		System.out.println();
		System.out.println(indent() + "))");
	}

	// Type t;
	// Identifier i;
	public void visit(VarDecl n) {
		System.out.print("VarDecl(");
		n.type.accept(this);
		System.out.print(", ");
		n.identifier.accept(this);
		System.out.print(")");
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public void visit(MethodDecl n) {
		System.out.println(indent() + "MethodDecl(");
		level++;
		
		System.out.print(indent());
		n.type.accept(this);
		System.out.println(", ");
		
		System.out.print(indent());
		n.identifier.accept(this);
		System.out.println(", ");
		
		if (n.formalList.size() != 0) {
			n.formalList.accept(this);
			System.out.println(", ");
		}
		
		if (n.varDeclList.size() != 0) {
			n.varDeclList.accept(this);
			System.out.println(", ");
		}
		
		if (n.statementList.size() != 0) {
			n.statementList.accept(this);
			System.out.println(", ");
		}
		
		System.out.print(indent());
		n.exp.accept(this);

		level--;
		System.out.print("\n" + indent() + ")");
	}

	// Type t;
	// Identifier i;
	public void visit(Formal n) {
		System.out.print("Formal(");
		n.t.accept(this);
		System.out.print(" ");
		n.i.accept(this);
		System.out.print(")");
	}

	public void visit(IntArrayType n) {
		System.out.print("IntArrayType()");
	}

	public void visit(BooleanType n) {
		System.out.print("BooleanType()");
	}

	public void visit(IntegerType n) {
		System.out.print("IntegerType()");
	}
	
	public void visit(VoidType n) {
		System.out.print("VoidType()");
	}

	// String s;
	public void visit(IdentifierType n) {
		System.out.print("IdentifierType(" + n.s + ")");
	}

	// StatementList sl;
	public void visit(Block n) {
		System.out.println(indent() + "Block(");
		level++;
		n.sl.accept(this);
		level--;
		System.out.print("\n" + indent() + ")");
	}

	// Exp e;
	// Statement s1,s2;
	public void visit(If n) {
		System.out.println("If(");
		level++;
		
		System.out.print(indent());
		n.e.accept(this);
		System.out.println(",");
		n.s1.accept(this);
		System.out.println(",");
		n.s2.accept(this);
		
		level--;
		System.out.print("\n" + indent() + ")");
	}

	// Exp e;
	// Statement s;
	public void visit(While n) {
		System.out.print("While(");
		n.e.accept(this);
		System.out.println(indent() + ",");
		n.s.accept(this);
		System.out.print(")");
	}

	// Exp e;
	public void visit(Print n) {
		System.out.print("Print(");
		n.e.accept(this);
		System.out.print(")");
	}

	// Identifier i;
	// Exp e;
	public void visit(Assign n) {
		System.out.print("Assign(");
		n.i.accept(this);
		System.out.print(", ");
		n.e.accept(this);
		System.out.print(")");
	}

	// Identifier i;
	// Exp e1,e2;
	public void visit(ArrayAssign n) {
		System.out.print("ArrayAssign(");
		n.i.accept(this);
		System.out.print(", ");
		n.e1.accept(this);
		System.out.print(", ");
		n.e2.accept(this);
		System.out.print(")");
	}

	// Exp e1,e2;
	public void visit(And n) {
		System.out.print("And(");
		n.e1.accept(this);
		System.out.print(", ");
		n.e2.accept(this);
		System.out.print(")");
	}

	// Exp e1,e2;
	public void visit(LessThan n) {
		System.out.print("LessThan(");
		n.e1.accept(this);
		System.out.print(", ");
		n.e2.accept(this);
		System.out.print(")");
	}

	// Exp e1,e2;
	public void visit(Plus n) {
		System.out.print("Plus(");
		n.e1.accept(this);
		System.out.print(", ");
		n.e2.accept(this);
		System.out.print(")");
	}

	// Exp e1,e2;
	public void visit(Minus n) {
		System.out.print("Minus(");
		n.e1.accept(this);
		System.out.print(", ");
		n.e2.accept(this);
		System.out.print(")");
	}

	// Exp e1,e2;
	public void visit(Times n) {
		System.out.print("Times(");
		n.e1.accept(this);
		System.out.print(", ");
		n.e2.accept(this);
		System.out.print(")");
	}

	// Exp e1,e2;
	public void visit(ArrayLookup n) {
		System.out.print("ArrayLookup(");
		n.e1.accept(this);
		System.out.print(", ");
		n.e2.accept(this);
		System.out.print(")");
	}

	// Exp e;
	public void visit(ArrayLength n) {
		System.out.print("ArrayLength(");
		n.e.accept(this);
		System.out.print(")");
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	public void visit(Call n) {
		System.out.print("Call(");
		n.e.accept(this);
		System.out.print(", ");
		n.i.accept(this);
		System.out.print(", (");
		for ( int i = 0; i < n.el.size(); i++ ) {
			n.el.elementAt(i).accept(this);
			if ( i+1 < n.el.size() ) { System.out.print(", "); }
		}
		System.out.print("))");
	}

	// int i;
	public void visit(IntegerLiteral n) {
		System.out.print("IntegerLiteral(" + n.i + ")");
	}

	public void visit(True n) {
		System.out.print("True()");
	}

	public void visit(False n) {
		System.out.print("False()");
	}

	// String s;
	public void visit(IdentifierExp n) {
		System.out.print("IdentifierExp(" + n.s + ")");
	}

	public void visit(This n) {
		System.out.print("This()");
	}

	// Exp e;
	public void visit(NewArray n) {
		System.out.print("NewArray(");
		n.e.accept(this);
		System.out.print(")");
	}

	// Identifier i;
	public void visit(NewObject n) {
		System.out.print("NewObject(");
		System.out.print(n.i.s);
		System.out.print(")");
	}

	// Exp e;
	public void visit(Not n) {
		System.out.print("Not(");
		n.e.accept(this);
		System.out.print(")");
	}
	
	// String s;
	public void visit(Identifier n) {
		System.out.print("Identifier(" + n.s + ")");
	}

	@Override
	public void visit(ClassDecl node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ClassDeclList node) {	
		System.out.println(indent() + "ClassDeclList(");
		level++;

		for ( int i = 0; i < node.size(); i++ ) {
			node.elementAt(i).accept(this);
			System.out.println(", ");
		}
		
		level--;
		System.out.print(indent() + ")");
	}

	@Override
	public void visit(Exp node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ExpList node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FormalList node) {
		System.out.println(indent() + "FormalList(");
		level++;
		
		for (int i = 0; i < node.size(); i++) {
			System.out.print(indent());
			node.elementAt(i).accept(this);
			System.out.println(",");
		}
		
		level--;
		System.out.print(indent() + ")");
	}

	@Override
	public void visit(MethodDeclList node) {	
		System.out.println(indent() + "MethodDeclList(");
		level++;
		
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
			System.out.println(",");
		}
		
		level--;
		System.out.print(indent() + ")");
	}

	@Override
	public void visit(Statement node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StatementList node) {
		System.out.println(indent() + "StatementList(");
		level++;
		
		for (int i = 0; i < node.size(); i++) {
			System.out.print(indent());
			node.elementAt(i).accept(this);
			System.out.println(",");
		}
		
		level--;
		System.out.print(indent() + ")");
	}
	
	@Override
	public void visit(StringArrayType node) {
		System.out.print("StringArrayType");
	}

	@Override
	public void visit(Type node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VarDeclList node) {		
		System.out.println(indent() + "VarDeclList(");
		level++;
		
		for (int i = 0; i < node.size(); i++) {
			System.out.print(indent());
			node.elementAt(i).accept(this);
			System.out.println(",");
		}
		
		level--;
		System.out.print(indent() + ")");
		
	}
}
