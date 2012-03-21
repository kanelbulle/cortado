package se.cortado;

import java.io.*;

import se.cortado.syntaxtree.Program;
import se.cortado.visitors.ASTPrintVisitor;
import se.cortado.visitors.IntermediateVisitor;
import se.cortado.visitors.ScopeVisitor;

import java_cup.runtime.Symbol;

public class Main {
	
	public static void main(String[] args) throws java.io.FileNotFoundException {
		if (args.length != 1) {
			System.out.println("Usage: java Main input_file");
		}
		
		String fileName = args[0];
		parser p = new parser(new Scanner(new FileReader(fileName)));
		ASTPrintVisitor v = new ASTPrintVisitor();
		ScopeVisitor sv = new ScopeVisitor();
		IntermediateVisitor iv = new IntermediateVisitor(null);
//		se.cortado.ir.tree.Print pr = new se.cortado.ir.tree.Print(System.out);
		
		try  {
			Symbol s = p.parse();
			Program prog = (Program) s.value;
//			v.visit(prog);
//			sv.visit(prog);
			iv.visit(prog);
		} catch (Exception e) {
			System.out.println("NO PARSE FOR YOU!");
			e.printStackTrace();
		}
	}
}
