package se.cortado.syntax;

import se.cortado.syntax.constructs.*;

public interface Visitor {
	
	public int visit(Plus node);
	public int visit(Minus node);
	
	public int visit(Multiply node);
	public int visit(LParen node);
	public int visit(RParen node);
	public int visit(LBracket node);
	public int visit(RBracket node);
	public int visit(LCurly node);
	public int visit(RCurly node);
	public int visit(And node);
	public int visit(Less node);
	

	public int visit(If node);
	public int visit(Else node);
	public int visit(While node);
	public int visit(SysOUT node);
	
	//MULTIPLY, LPAREN, RPAREN, LBRACKET, RBRACKET, LCURLY, RCURLY, AND, LESS;
}