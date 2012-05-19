package se.cortado.visitors;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
import se.cortado.syntaxtree.Node;
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
import se.cortado.syntaxtree.VoidExp;
import se.cortado.syntaxtree.VoidType;
import se.cortado.syntaxtree.While;

public class JasminVisitor {

	List<String>	mOutput;

	SymbolTable		mSymbolTable;
	ClassScope		mClassScope;
	MethodScope		mMethodScope;

	int				mLabelCount;

	boolean			mPrint;

	public JasminVisitor(SymbolTable symbolTable, boolean print) {
		this.mSymbolTable = symbolTable;
		this.mPrint = print;
		this.mOutput = new ArrayList<String>();
	}

	private void writeline(int line) {
		// write(".line " + line);
	}

	private void writeToFile(String file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for (String s : mOutput) {
				if (mPrint) {
					System.out.print(s);
				}
				writer.write(s);
			}

			writer.close();

			mOutput.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void write(String message) {
		mOutput.add(message + "\n");
	}

	private int max(int a, int b) {
		return Math.max(a, b);
	}

	private int max(int a, int b, int c) {
		return max(max(a, b), c);
	}

	private int max(int a, int b, int c, int d) {
		return max(max(a, b), max(c, d));
	}

	private int callSpecific(Node node) {
		try {
			Class<? extends JasminVisitor> jClass = this.getClass();
			Method method = jClass.getMethod("visit", node.getClass());
			Integer depth = (Integer) method.invoke(this, node);

			return depth.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	private void writeind(String message) {
		write("    " + message);
	}

	private String newLabel() {
		return "L" + mLabelCount++;
	}

	private String descriptorFromType(Type type) {
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

	private String t(Type type) {
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

	private String store(Type type, String local) {
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

	private String load(Type type, String local) {
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

	public int visit(And node) {

		String trueLabel = newLabel();
		String endLabel = newLabel();

		int d1 = visit(node.e1);
		writeind("dup");
		writeind("ifeq " + endLabel);

		int d2 = visit(node.e2);

		writeind("if_icmpeq " + trueLabel);
		writeind("iconst_0");
		writeind("goto " + endLabel);
		write(trueLabel + ":");
		writeind("iconst_1");
		write(endLabel + ":");

		return Math.max(d1, d2) + 2;
	}

	public int visit(ArrayAssign node) {
		int d1 = visit(node.i);
		int d2 = visit(node.e1);
		int d3 = visit(node.e2);
		writeind("iastore");

		return max(d1, d2, d3, 3);
	}

	public int visit(ArrayLength node) {
		int d = visit(node.e);
		writeind("arraylength");

		return d;
	}

	public int visit(ArrayLookup node) {
		int d1 = visit(node.e1);
		int d2 = visit(node.e2);
		writeind("iaload");

		return max(d1, d2, 2);
	}

	public int visit(Assign node) {
		String local = mMethodScope.getLocal(node.i.s);
		if (local == null) {
			// field assign
			String dis = mMethodScope.getLocal("this");
			writeind("aload " + dis);
			int d = visit(node.e);
			String fType = descriptorFromType(mClassScope.getVariableType(node.i.s));
			writeind("putfield '" + mClassScope.getName() + "/" + node.i.s + "' " + fType);

			return 1 + d;
		} else {
			// local variable assign
			Type varType = mMethodScope.getVariableType(node.i);
			int d = visit(node.e);
			writeind(store(varType, local));

			return d;
		}
	}

	public int visit(Block node) {
		return visit(node.sl);
	}

	public int visit(BooleanType node) {
		return 0;
	}

	public int visit(Call node) {
		// push object reference onto stack
		int d1 = visit(node.e);

		// push arguments onto stack
		int d2 = -1;
		for (int i = 0; i < node.el.size(); i++) {
			int d = visit(node.el.elementAt(i));
			d2 = max(d2, d + i + 1);
		}

		String methodDesc = node.i.s + "(";
		for (int i = 0; i < node.ms.getParameterList().size(); i++) {
			Formal f = node.ms.getParameterList().elementAt(i);
			methodDesc += descriptorFromType(f.t);
		}
		methodDesc += ")" + descriptorFromType(node.ms.getReturnType());

		writeind("invokevirtual '" + node.c + "/" + methodDesc + "'");

		return d1 + d2;
	}

	public int visit(ClassDecl node) {
		return callSpecific(node);
	}

	public int visit(ClassDeclExtends node) {
		return 0;
	}

	public int visit(ClassDeclList node) {
		for (int i = 0; i < node.size(); i++) {
			visit(node.elementAt(i));
		}

		return 0;
	}

	public int visit(ClassDeclSimple node) {
		mClassScope = mSymbolTable.get(node.i.s);

		// create file writer
		// mWriter = new BufferedWriter(new FileWriter(node.i.s + ".j"));

		write(".class '" + node.i.s + "'");
		write(".super java/lang/Object\n");

		// declare fields
		for (int i = 0; i < node.vl.size(); i++) {
			VarDecl vd = node.vl.elementAt(i);
			writeind(".field public '" + vd.identifier.s + "' " + descriptorFromType(vd.type));
		}

		// standard initializer
		write(".method public <init>()V");
		writeind("aload_0");
		writeind("invokespecial java/lang/Object/<init>()V");
		writeind("return");
		write(".end method\n");

		for (int i = 0; i < node.ml.size(); i++) {
			visit(node.ml.elementAt(i));
		}

		writeToFile(node.i.s + ".j");

		return 0;
	}

	public int visit(Exp node) {
		return callSpecific(node);
	}

	public int visit(ExpList node) {
		return 0;
	}

	public int visit(False node) {
		writeind("iconst_0");

		return 1;
	}

	public int visit(Formal node) {
		return 0;
	}

	public int visit(FormalList node) {
		return 0;
	}

	public void loadVariable(String var) {
		String local = mMethodScope.getLocal(var);
		if (local == null) {
			// field load
			String dis = mMethodScope.getLocal("this");
			writeind("aload " + dis);
			String fType = descriptorFromType(mClassScope.getVariableType(var));
			writeind("getfield '" + mClassScope.getName() + "/" + var + "' " + fType);
		} else {
			// local variable load
			Type vType = mMethodScope.getVariableType(var);
			if (vType == null) {
				vType = mMethodScope.getFormalType(var);
			}
			writeind(load(vType, local));
		}
	}

	public int visit(Identifier node) {
		writeline(node.line);

		loadVariable(node.s);

		return 1;
	}

	public int visit(IdentifierExp node) {
		writeline(node.line);

		loadVariable(node.s);

		return 1;
	}

	public int visit(IdentifierType node) {
		return 0;
	}

	public int visit(If node) {
		writeline(node.line);

		int d1 = visit(node.e);

		String trueLabel = newLabel();
		String falseLabel = newLabel();
		String endLabel = newLabel();

		writeind("ifeq " + falseLabel);

		write(trueLabel + ":");
		int d2 = visit(node.s1);
		writeind("goto " + endLabel);

		write(falseLabel + ":");
		int d3 = visit(node.s2);
		write(endLabel + ":");

		return max(d1, d2, d3);
	}

	public int visit(IntArrayType node) {
		return 0;
	}

	public int visit(IntegerLiteral node) {
		writeline(node.line);

		if (node.i >= 0 && node.i < 5) {
			writeind("iconst_" + node.i);
		} else {
			writeind("ldc " + node.i);
		}

		return 1;
	}

	public int visit(IntegerType node) {
		return 0;
	}

	public int visit(LessThan node) {
		writeline(node.line);

		int d1 = visit(node.e1);
		int d2 = visit(node.e2);

		String trueLabel = newLabel();
		String endLabel = newLabel();

		writeind("if_icmplt " + trueLabel);
		writeind("iconst_0");
		writeind("goto " + endLabel);
		write(trueLabel + ":");
		writeind("iconst_1");
		write(endLabel + ":");

		return max(d1, d2, 1);
	}

	public int visit(MainClass node) {
		mClassScope = mSymbolTable.get(node.i.s);

		write(".class '" + node.i.s + "'");
		write(".super java/lang/Object");

		// standard initializer
		write(".method public <init>()V");
		writeind("aload_0");
		writeind("invokenonvirtual java/lang/Object/<init>()V");
		writeind("return");
		write(".end method");

		visit(node.md);

		writeToFile(node.i.s + ".j");
		
		return 0;
	}

	public int visit(MethodDecl node) {
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
		int stackDepthIndex = mOutput.size(); 
		writeind(".limit locals " + mMethodScope.getNumLocals());

		int d1 = visit(node.statementList);
		int d2 = visit(node.exp);
		
		// insert stack depth 
		String stackLimit = "    .limit stack " + (2 + max(d1, d2)) + "\n";
		mOutput.add(stackDepthIndex, stackLimit);

		writeind(t(node.type) + "return");
		write(".end method\n");

		return 0;
	}

	public int visit(MethodDeclList node) {
		for (int i = 0; i < node.size(); i++) {
			visit(node.elementAt(i));
		}

		return 0;
	}

	public int visit(Minus node) {
		int d1 = visit(node.e1);
		int d2 = visit(node.e2);
		writeind("isub");

		return max(d1, d2, 2);
	}

	public int visit(NewArray node) {
		writeline(node.line);

		int d = visit(node.e);
		writeind("newarray int");

		return max(d, 1);
	}

	public int visit(NewObject node) {
		writeline(node.line);

		writeind("new '" + node.i.s + "'");
		writeind("dup");
		writeind("invokespecial '" + node.i.s + "/<init>()V'");

		return 2;
	}

	public int visit(Not node) {
		writeline(node.line);

		int d = visit(node.e);
		writeind("iconst_1");
		writeind("ixor");

		return max(d, 2);
	}

	public int visit(Plus node) {
		writeline(node.line);

		int d1 = visit(node.e1);
		int d2 = visit(node.e2);
		writeind("iadd");

		return max(d1, d2, 2);
	}

	public int visit(Print node) {
		writeind("getstatic java/lang/System/out Ljava/io/PrintStream;");
		int d = visit(node.e);
		writeind("invokevirtual java/io/PrintStream/println(I)V");

		return max(d, 2);
	}

	public int visit(Program node) {
		visit(node.mainClass);
		visit(node.classDeclList);

		return 0;
	}

	public int visit(Statement node) {
		return callSpecific(node);
	}

	public int visit(StatementList node) {
		int d = -1;
		for (int i = 0; i < node.size(); i++) {
			int d1 = visit(node.elementAt(i));
			d = max(d, d1);
		}

		return d;
	}

	public int visit(StringArrayType node) {
		return 0;
	}

	public int visit(This node) {
		writeind("aload_0");

		return 1;
	}

	public int visit(Times node) {
		int d1 = visit(node.e1);
		int d2 = visit(node.e2);
		writeind("imul");

		return max(d1, d2, 2);
	}

	public int visit(True node) {
		writeind("iconst_1");

		return 1;
	}

	public int visit(Type node) {
		return 0;
	}

	public int visit(VarDecl node) {
		return 0;
	}

	public int visit(VarDeclList node) {
		return 0;
	}

	public int visit(VoidType node) {
		return 0;
	}

	public int visit(VoidExp node) {
		return 0;
	}

	public int visit(While node) {
		writeline(node.line);

		String startLabel = newLabel();
		String endLabel = newLabel();

		write(startLabel + ":");
		int d1 = visit(node.e);
		writeind("ifeq " + endLabel);

		int d2 = visit(node.s);
		writeind("goto " + startLabel);

		write(endLabel + ":");

		return max(d1, d2);
	}

}
