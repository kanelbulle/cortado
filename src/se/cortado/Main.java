package se.cortado;

import java.io.FileReader;

import java_cup.runtime.Symbol;
import se.cortado.ir.canon.Canonicalizer;
import se.cortado.ir.translate.ProcFragment;
import se.cortado.syntaxtree.Program;
import se.cortado.visitors.ASTPrintVisitor;
import se.cortado.visitors.IntermediateVisitor;
import se.cortado.visitors.ScopeVisitor;
import se.cortado.visitors.SlowTypeVisitor;
import se.cortado.visitors.SymbolTable;

public class Main {

	public static void main(String[] args) throws java.io.FileNotFoundException {
		if (args.length != 1) {
			System.out.println("Usage: java Main input_file");
		}

		String fileName = args[0];
		parser p = new parser(new Scanner(new FileReader(fileName)));

		ASTPrintVisitor printVisitor = new ASTPrintVisitor();
		SymbolTable symbolTable = new SymbolTable();
		ScopeVisitor scopeVisitor = new ScopeVisitor(symbolTable);
		SlowTypeVisitor typeVisitor = new SlowTypeVisitor();
		IntermediateVisitor irVisitor = new IntermediateVisitor(symbolTable);

		try {
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

			System.out.println("==================== DOING DA IR CONVERSION MAN ====================");
			irVisitor.visit(prog);
			ProcFragment fragments = irVisitor.getResult();

			System.out.println("==================== DOING DA CANONICALIZATION DUDE ====================");
			Canonicalizer canon = new Canonicalizer(true);
			fragments = canon.canonicalize(fragments);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
//			e.printStackTrace();
			System.exit(-1);
		}
	}
}
