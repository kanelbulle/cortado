package se.cortado.visitors;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

public class JasminVisitor implements Visitor {

	BufferedWriter	mWriter;
	
	SymbolTable mSymbolTable;
	ClassScope mClassScope;
	MethodScope mMethodScope;
	
	public JasminVisitor(SymbolTable symbolTable) {
		this.mSymbolTable = symbolTable;
	}

	public void write(String message) {
		try {
			mWriter.write(message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeind(String message) {
		write("    " + message);
	}

	@Override
	public void visit(And node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArrayAssign node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArrayLength node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArrayLookup node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Assign node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Block node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BooleanType node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Call node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ClassDecl node) {
		try {
			// create file writer
			mWriter = new BufferedWriter(new FileWriter(node.i.s + ".j"));

			write(".class public " + node.i.s);
			write(".super java/lang/Object");

			// standard initializer
			write(".method public <init>()V");
			writeind("aload_0");
			writeind("invokenonvirtual java/lang/Object/<init>()V");
			writeind("return");
			write(".end method");

			mWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void visit(ClassDeclExtends node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ClassDeclList node) {
		for (int i = 0; i < node.size(); i++) {
			node.elementAt(i).accept(this);
		}
	}

	@Override
	public void visit(ClassDeclSimple node) {
		// TODO Auto-generated method stub

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
	public void visit(False node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Formal node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FormalList node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Identifier node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IdentifierExp node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IdentifierType node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(If node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IntArrayType node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IntegerLiteral node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IntegerType node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(LessThan node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MainClass node) {
		mClassScope = mSymbolTable.get(node.i.s);
		
		node.md.accept(this);
	}

	@Override
	public void visit(MethodDecl node) {
		// TODO Auto-generated method stub

		mMethodScope = mClassScope.getMethodMatching(node);
		
		if (mClassScope.getClassDecl() instanceof MainClass) {
			write(".method public static main([Ljava/lang/String;)V");
		} else {
			//write(".method public " + node.identifier.s +)
		}
		
		
		
		node.exp.accept(this);
		
		write(".end method");
	}

	@Override
	public void visit(MethodDeclList node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Minus node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NewArray node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NewObject node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Not node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Plus node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Print node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Program node) {
		node.mainClass.accept(this);
		node.classDeclList.accept(this);
	}

	@Override
	public void visit(Statement node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StatementList node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(StringArrayType node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(This node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Times node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(True node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Type node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VarDecl node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VarDeclList node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VoidType node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(While node) {
		// TODO Auto-generated method stub

	}

}
