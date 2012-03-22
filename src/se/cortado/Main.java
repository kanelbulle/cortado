package se.cortado;

import java.io.FileReader;

import java_cup.runtime.Symbol;
import se.cortado.visitors.*;
import se.cortado.syntaxtree.Program;


public class Main {

	public static void main(String[] args) throws java.io.FileNotFoundException {
		if (args.length != 1) {
			System.out.println("Usage: java Main input_file");
		}

		String fileName = args[0];
		parser p = new parser(new Scanner(new FileReader(fileName)));
		
		//ASTPrintVisitor printVisitor = new ASTPrintVisitor();
		SymbolTable symbolTable = new SymbolTable();
		ScopeVisitor scopeVisitor = new ScopeVisitor(symbolTable);
		SlowTypeVisitor typeVisitor = new SlowTypeVisitor();

		try {
			Symbol s = p.parse();
			Program prog = (Program) s.value;
			// printVisitor.visit(prog);

			scopeVisitor.visit(prog);
			if (scopeVisitor.errorOccurred)
				return;

			symbolTable.print();

			typeVisitor.setSymbolTable(symbolTable);
			typeVisitor.visit(prog);
			if (typeVisitor.errorOccurred) {
				System.out.println("Type check failed with errors.");
				return;
			}

		} catch (Exception e) {
			System.out.println("NO PARSE FOR YOU!");
			e.printStackTrace();
		}
	}
}
