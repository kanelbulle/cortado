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

	SymbolTable		mSymbolTable;
	ClassScope		mClassScope;
	MethodScope		mMethodScope;

	int				mLabelCount;

	public JasminVisitor(SymbolTable symbolTable) {
		this.mSymbolTable = symbolTable;
	}

	public void write(String message) {
		try {
			System.out.println(message);
			mWriter.write(message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeind(String message) {
		write("    " + message);
	}
	
	public String newLabel() {
		return "L" + mLabelCount++;
	}

	public String descriptorFromType(Type type) {
		if (type instanceof BooleanType) {
			return "B";
		} else if (type instanceof IntegerType) {
			return "I";
		} else if (type instanceof IntArrayType) {
			return "[I";
		} else if (type instanceof IdentifierType) {
			return "L" + ((IdentifierType) type).s + ";";
		}

		throw new Error("Unknown type: " + type);
	}

	public String t(Type type) {
		if (type instanceof BooleanType) {
			return "i";
		} else if (type instanceof IntegerType) {
			return "i";
		} else if (type instanceof IntArrayType) {
			return "a";
		} else if (type instanceof IdentifierType) {
			return "a";
		} else if (type instanceof VoidType) {
			return "";
		}

		throw new Error("Unknown type: " + type);
	}

	public String store(Type type, String local) {
		if (type instanceof BooleanType) {
			return "istore " + local;
		} else if (type instanceof IntegerType) {
			return "istore " + local;
		} else if (type instanceof IntArrayType) {
			return "astore " + local;
		} else if (type instanceof IdentifierType) {
			return "astore " + local;
		}

		throw new Error("Unknown type: " + type);
	}

	public String load(Type type, String local) {
		if (type instanceof BooleanType) {
			return "iload " + local;
		} else if (type instanceof IntegerType) {
			return "iload " + local;
		} else if (type instanceof IntArrayType) {
			return "aload " + local;
		} else if (type instanceof IdentifierType) {
			return "aload " + local;
		}

		throw new Error("Unknown type: " + type);
	}

	@Override
	public void visit(And node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArrayAssign node) {
		node.i.accept(this);
		node.e1.accept(this);
		node.e2.accept(this);
		writeind("iastore");
	}

	@Override
	public void visit(ArrayLength node) {
		node.e.accept(this);
		writeind("arraylength");
	}

	@Override
	public void visit(ArrayLookup node) {
		node.e1.accept(this);
		node.e2.accept(this);
		writeind("iaload");
	}

	@Override
	public void visit(Assign node) {
		String local = mMethodScope.getLocal(node.i.s);
		if (local == null) {
			// field assign
			String dis = mMethodScope.getLocal("this");
			writeind("aload " + dis);
			node.e.accept(this);
			String fType = descriptorFromType(mClassScope.getVariableType(node.i.s));
			writeind("putfield " + mClassScope.getName() + "/" + node.i.s + " " + fType);
		} else {
			// local variable assign
			Type varType = mMethodScope.getVariableType(node.i);
			node.e.accept(this);
			writeind(store(varType, local));
		}
	}

	@Override
	public void visit(Block node) {
		node.sl.accept(this);
	}

	@Override
	public void visit(BooleanType node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Call node) {
		// push object reference onto stack
		node.e.accept(this);

		// push arguments onto stack
		for (int i = node.el.size() - 1; i >= 0; i--) {
			node.el.elementAt(i).accept(this);
		}

		String methodDesc = node.i.s + "(";
		for (int i = 0; i < node.ms.getParameterList().size(); i++) {
			Formal f = node.ms.getParameterList().elementAt(i);
			methodDesc += descriptorFromType(f.t);
		}
		methodDesc += ")" + descriptorFromType(node.ms.getReturnType());

		writeind("invokevirtual " + node.c + "/" + methodDesc);
	}

	@Override
	public void visit(ClassDecl node) {
		// TODO Auto-generated method stub

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
		mClassScope = mSymbolTable.get(node.i.s);

		try {
			// create file writer
			mWriter = new BufferedWriter(new FileWriter(node.i.s + ".j"));

			write(".class " + node.i.s);
			write(".super java/lang/Object\n");

			// declare fields
			for (int i = 0; i < node.vl.size(); i++) {
				VarDecl vd = node.vl.elementAt(i);
				writeind(".field public " + vd.identifier.s + " " + descriptorFromType(vd.type));
			}

			// standard initializer
			write(".method public <init>()V");
			writeind("aload_0");
			writeind("invokespecial java/lang/Object/<init>()V");
			writeind("return");
			write(".end method\n");

			for (int i = 0; i < node.ml.size(); i++) {
				node.ml.elementAt(i).accept(this);
			}

			mWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		writeind("iconst_0");
	}

	@Override
	public void visit(Formal node) {

	}

	@Override
	public void visit(FormalList node) {

	}

	public void loadVariable(String var) {
		String local = mMethodScope.getLocal(var);
		if (local == null) {
			// field load
			String dis = mMethodScope.getLocal("this");
			writeind("aload " + dis);
			String fType = descriptorFromType(mClassScope.getVariableType(var));
			writeind("getfield " + mClassScope.getName() + "/" + var + " " + fType);
		} else {
			// local variable load
			Type vType = mMethodScope.getVariableType(var);
			if (vType == null) {
				vType = mMethodScope.getFormalType(var);
			}
			writeind(load(vType, local));
		}
	}

	@Override
	public void visit(Identifier node) {
		loadVariable(node.s);
	}

	@Override
	public void visit(IdentifierExp node) {
		loadVariable(node.s);
	}

	@Override
	public void visit(IdentifierType node) {

	}

	@Override
	public void visit(If node) {
		// TODO Auto-generated method stub

		node.e.accept(this);
		
		String trueLabel = newLabel();
		String falseLabel = newLabel();
		String endLabel = newLabel();
		
		writeind("ifeq " + falseLabel);
		
		write(trueLabel + ":");
		node.s1.accept(this);
		writeind("goto " + endLabel);
		
		write(falseLabel + ":");
		node.s2.accept(this);
		write(endLabel + ":");
	}

	@Override
	public void visit(IntArrayType node) {

	}

	@Override
	public void visit(IntegerLiteral node) {
		if (node.i >= 0 && node.i < 5) {
			writeind("iconst_" + node.i);
		} else {
			writeind("ldc " + node.i);
		}
	}

	@Override
	public void visit(IntegerType node) {

	}

	@Override
	public void visit(LessThan node) {
		node.e2.accept(this);
		node.e1.accept(this);
		
		String falseLabel = newLabel();
		String trueLabel = newLabel();
		String endLabel = newLabel();
		
		writeind("if_icmplt " + trueLabel);
		write(falseLabel + ":");
		writeind("iconst_1");
		writeind("goto " + endLabel);
		write(trueLabel + ":");
		writeind("iconst_0");
		write(endLabel + ":");
	}

	@Override
	public void visit(MainClass node) {
		mClassScope = mSymbolTable.get(node.i.s);

		try {
			// create file writer
			mWriter = new BufferedWriter(new FileWriter(node.i.s + ".j"));

			write(".class " + node.i.s);
			write(".super java/lang/Object");

			// standard initializer
			write(".method public <init>()V");
			writeind("aload_0");
			writeind("invokenonvirtual java/lang/Object/<init>()V");
			writeind("return");
			write(".end method");

			node.md.accept(this);

			mWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void visit(MethodDecl node) {
		mMethodScope = mClassScope.getMethodMatching(node);

		boolean main = mClassScope.getClassDecl() instanceof MainClass;

		if (main) {
			write(".method public static main([Ljava/lang/String;)V");
		} else {
			String arglist = "";
			for (int i = 0; i < node.formalList.size(); i++) {
				Formal f = node.formalList.elementAt(i);
				arglist += descriptorFromType(f.t) + "";
			}

			String retType = descriptorFromType(mMethodScope.getReturnType());

			write(".method public " + node.identifier.s + "(" + arglist + ")" + retType);
		}

		// TODO fix stack depth
		writeind(".limit stack 10");
		writeind(".limit locals " + mMethodScope.getNumLocals());

		node.statementList.accept(this);

		node.exp.accept(this);

		writeind(t(node.type) + "return");
		write(".end method\n");
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
		writeind("isub");
	}

	@Override
	public void visit(NewArray node) {
		node.e.accept(this);
		writeind("newarray int");
	}

	@Override
	public void visit(NewObject node) {
		writeind("new " + node.i.s);
		writeind("dup");
		writeind("invokespecial " + node.i.s + "/<init>()V");
	}

	@Override
	public void visit(Not node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Plus node) {
		node.e1.accept(this);
		node.e2.accept(this);
		writeind("iadd");
	}

	@Override
	public void visit(Print node) {
		writeind("getstatic java/lang/System/out Ljava/io/PrintStream;");
		node.e.accept(this);
		writeind("invokevirtual java/io/PrintStream/println(I)V");
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
		writeind("aload_0");
	}

	@Override
	public void visit(Times node) {
		node.e1.accept(this);
		node.e2.accept(this);
		writeind("imul");
	}

	@Override
	public void visit(True node) {
		writeind("iconst_1");
	}

	@Override
	public void visit(Type node) {

	}

	@Override
	public void visit(VarDecl node) {

	}

	@Override
	public void visit(VarDeclList node) {

	}

	@Override
	public void visit(VoidType node) {

	}

	@Override
	public void visit(While node) {
		String startLabel = newLabel();
		String endLabel = newLabel();

		write(startLabel + ":");
		node.e.accept(this);
		writeind("ifeq " + endLabel);
		
		node.s.accept(this);
		writeind("goto " + startLabel);

		write(endLabel + ":");
	}

}
