package se.cortado;

import java.io.*;

import se.cortado.syntax.visitor.ASTPrintVisitor;
import se.cortado.syntaxtree.Program;

import java_cup.runtime.Symbol;

public class Main {
	
	public static void main(String[] args) throws java.io.FileNotFoundException {
		if (args.length != 1) {
			System.out.println("Usage: java Main input_file");
		}
		
		String fileName = args[0];
		parser p = new parser(new Scanner(new FileReader(fileName)));
		ASTPrintVisitor v = new ASTPrintVisitor();
		
		try  {
			Symbol s = p.parse();
			Program prog = (Program) s.value;
			v.visit(prog);
		} catch (Exception e) {
			System.out.println("NO PARSE FOR YOU!");
			e.printStackTrace();
		}
	}
}
