package se.cortado;

import java.io.FileReader;

import java_cup.runtime.Symbol;
import se.cortado.ir.canon.BasicBlocks;
import se.cortado.ir.canon.Canon;
import se.cortado.ir.canon.TraceSchedule;
import se.cortado.ir.translate.ProcFragment;
import se.cortado.ir.tree.IR_StmList;
import se.cortado.ir.tree.Print;
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
			
			ProcFragment fragments = irVisitor.getResult();
			while (fragments != null) {
				System.out.println(fragments.labelName);
				IR_StmList list = Canon.linearize(fragments.body);
				BasicBlocks bb = new BasicBlocks(list);
				TraceSchedule ts = new TraceSchedule(bb);
				list = ts.stms;
				
				Print print = new Print(System.out);
				
				while (list != null) {
					print.prStm(list.head);
					System.out.println();
					list = list.tail;
				}
				
				System.out.println();
				
				fragments = (ProcFragment) fragments.next;
			}

		} catch (Exception e) {
			System.out.println("NO PARSE FOR YOU!");
			e.printStackTrace();
		}
	}
}
