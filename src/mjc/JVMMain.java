package mjc;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java_cup.runtime.Symbol;
import se.cortado.Scanner;
import se.cortado.parser;
import se.cortado.syntaxtree.Program;
import se.cortado.visitors.ASTPrintVisitor;
import se.cortado.visitors.JasminVisitor;
import se.cortado.visitors.ScopeVisitor;
import se.cortado.visitors.SlowTypeVisitor;
import se.cortado.visitors.SymbolTable;

public class JVMMain {

	public static void main(String[] args) throws FileNotFoundException {
		String fileName = args[0];
		parser p = new parser(new Scanner(new FileReader(fileName)));

		ASTPrintVisitor printVisitor = new ASTPrintVisitor();
		SymbolTable symbolTable = new SymbolTable();
		ScopeVisitor scopeVisitor = new ScopeVisitor(symbolTable);
		SlowTypeVisitor typeVisitor = new SlowTypeVisitor();

		try {
			System.out.println("==================== PARSING... NINJA STYLE! ====================");
			Symbol s = p.parse();
			Program prog = (Program) s.value;
			printVisitor.visit(prog);

			System.out.println("==================== BADASS SCOPE CHECKING! ====================");
			scopeVisitor.visit(prog);
			if (scopeVisitor.errorOccurred) {
				throw new Exception("Scope check failed with errors.");
			}

			System.out.println("==================== GOT EPIC SYMBOL TABLE ====================");
			symbolTable.print();

			System.out.println("==================== TYPE CHECKING FO SHIZZLE ====================");
			typeVisitor.setSymbolTable(symbolTable);
			typeVisitor.visit(prog);
			if (typeVisitor.errorOccurred) {
				throw new Exception("Type check failed with errors.");
			}
			
			JasminVisitor jv = new JasminVisitor(symbolTable);
			prog.accept(jv);
			
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		
		// clean exit
		System.exit(0);
	}

}
