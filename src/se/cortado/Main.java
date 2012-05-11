package se.cortado;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java_cup.runtime.Symbol;
import se.cortado.assem.Instr;
import se.cortado.frame.Proc;
import se.cortado.ir.canon.Canonicalizer;
import se.cortado.ir.temp.DefaultMap;
import se.cortado.ir.translate.ProcFragment;
import se.cortado.liveness.AssemFlowGraph;
import se.cortado.regalloc.Liveness;
import se.cortado.regalloc.RegAlloc;
import se.cortado.syntaxtree.Program;
import se.cortado.visitors.ASTPrintVisitor;
import se.cortado.visitors.IntermediateVisitor;
import se.cortado.visitors.ScopeVisitor;
import se.cortado.visitors.SlowTypeVisitor;
import se.cortado.visitors.SymbolTable;
import se.cortado.x86_64.Codegen;

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

			System.out.println("==================== DOING DA IR CONVERSION MAN ====================");
			irVisitor.visit(prog);
			ProcFragment fragments = irVisitor.getResult();

			System.out.println("==================== DOING DA CANONICALIZATION DUDE ====================");
			Canonicalizer canon = new Canonicalizer(true);
			fragments = canon.canonicalize(fragments);
			
			System.out.println("==================== DOING DA CODEGEN DUDE ====================");
			ProcFragment firstFrag = fragments;
			while (fragments != null) {
				Codegen codegen = new Codegen(fragments.frame);
				List<Instr> instr = codegen.codegen(fragments.canonicalized);
				
				instr = fragments.frame.procEntryExit2(instr);
				
				Proc proc = fragments.frame.procEntryExit3(instr);
				
				System.out.println(proc.toString(new DefaultMap()));
				
				fragments.proc = proc;
				fragments = (ProcFragment) fragments.next;
			}
			fragments = firstFrag;
			
			System.out.println("==================== BUILDIN' SOME GRAPHS AND NIZZLE ====================");
			
			
			while (fragments != null) {
				AssemFlowGraph afg = new AssemFlowGraph(fragments.proc.body);
				
				afg.show(System.out);
				System.out.println("\n\n");
				
				Liveness liveness = new Liveness(afg);
				liveness.show(System.out);

				fragments.liveness = liveness;
				
				fragments = (ProcFragment) fragments.next;
			}
			fragments = firstFrag;
			
			
			System.out.println("==================== ALLOCIN' SOME REGS FOR YO TEMPS DAWG ====================");
			
			List<ProcFragment> revFragments = new ArrayList<ProcFragment>();
			while (fragments != null) {
				RegAlloc regalloc = new RegAlloc(fragments.frame, fragments.proc.body, fragments.liveness);
				for (Instr instr : fragments.proc.body) {
					System.out.print(instr.format(regalloc));
				}
				fragments.regalloc = regalloc;
				
				revFragments.add(fragments);
				fragments = (ProcFragment) fragments.next;
			}
			Collections.reverse(revFragments);
			
			
			// assume file ends in .java
			fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
			fileName = fileName.substring(0, fileName.length() - 5) + ".s";
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName));
			
			for (ProcFragment proc : revFragments) {
				for (Instr instr : proc.proc.body) {
					fileWriter.write(instr.format(proc.regalloc));
				}
			}
			
			fileWriter.close();
			
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
