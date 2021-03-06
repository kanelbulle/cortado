package se.cortado.visitors;

import se.cortado.syntaxtree.*;

public interface Visitor {
	
//	public void visit(SyntaxTreePrinter node); //FIXEME: should this be here?
	
	/* Language constructs */
	public void visit(And node);
	public void visit(ArrayAssign node);
	public void visit(ArrayLength node);
	public void visit(ArrayLookup node);
	public void visit(Assign node);
	public void visit(Block node);
	public void visit(BooleanType node);
	public void visit(Call node);
	public void visit(ClassDecl node);
	public void visit(ClassDeclExtends node);
	public void visit(ClassDeclList node);
	public void visit(ClassDeclSimple node);
	public void visit(Exp node);
	public void visit(ExpList node);
	public void visit(False node);
	public void visit(Formal node);
	public void visit(FormalList node);
	public void visit(Identifier node);
	public void visit(IdentifierExp node);
	public void visit(IdentifierType node);
	public void visit(If node);
	public void visit(IntArrayType node);
	public void visit(IntegerLiteral node);
	public void visit(IntegerType node);
	public void visit(LessThan node);
	public void visit(MainClass node);
	public void visit(MethodDecl node);
	public void visit(MethodDeclList node);
	public void visit(Minus node);
	public void visit(NewArray node);
	public void visit(NewObject node);
	public void visit(Not node);
	public void visit(Plus node);
	public void visit(Print node);
	public void visit(Program node);
	public void visit(Statement node);
	public void visit(StatementList node);
	public void visit(StringArrayType node);
	public void visit(This node);
	public void visit(Times node);
	public void visit(True node);
	public void visit(Type node);
	public void visit(VarDecl node);
	public void visit(VarDeclList node);
	public void visit(VoidType node);
	public void visit(While node);
	
}