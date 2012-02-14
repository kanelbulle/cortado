package se.cortado.syntax;

import se.cortado.syntax.constructs.*;

public class Interpreter implements Visitor {

	@Override
	public int visit(Plus n) {
		return n.e1.accept(this) + n.e2.accept(this);
	}

	@Override
	public int visit(Minus node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(Multiply node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(LParen node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(RParen node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(LBracket node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(RBracket node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(LCurly node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(RCurly node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(And node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(Less node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(If node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(Else node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(While node) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int visit(SysOUT node) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
