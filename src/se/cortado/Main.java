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
		IntermediateVisitor irVisitor = new IntermediateVisitor(symbolTable);
		
		try {
			Symbol s = p.parse();
			Program prog = (Program) s.value;
			// printVisitor.visit(prog);

			System.out.println("==================== BADASS SCOPE CHECKING! ====================");
			scopeVisitor.visit(prog);
			if (scopeVisitor.errorOccurred) {
				System.out.println("Scope check failed with errors.");
				return;
			}

			System.out.println("==================== GOT EPIC SYMBOL TABLE ====================");
			symbolTable.print();
			
			System.out.println("==================== TYPE CHECKING FO SHIZZLE ====================");
			typeVisitor.setSymbolTable(symbolTable);
			typeVisitor.visit(prog);
			if (typeVisitor.errorOccurred) {
				System.out.println("Type check failed with errors.");
				return;
			}
			
			System.out.println("==================== DOING DA IR CONVERSION MAN ====================");
			irVisitor.visit(prog);

		} catch (Exception e) {
			System.out.println("NO PARSE FOR YOU!");
			e.printStackTrace();
		}
	}
}
