package mjc;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java_cup.runtime.Symbol;
import se.cortado.Scanner;
import se.cortado.parser;
import se.cortado.assem.Instr;
import se.cortado.frame.Proc;
import se.cortado.ir.canon.Canonicalizer;
import se.cortado.ir.common.SimpleFragment;
import se.cortado.ir.temp.DefaultMap;
import se.cortado.ir.tree.IR_StmList;
import se.cortado.ir.tree.Print;
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

public class X86Main {

	public static boolean DEBUG_COMPILER = true;
	
	public static void main(String[] args) {
		
		// Test input + parameters
		String inputFile = args[0];
		
		Architecture.setArchitecture("X86");
		
		try {
			Program program = parseSourceFile(inputFile, false);
			SymbolTable symbolTable = scopeCheckProgram(program, false);
			typeCheckProgram(program, symbolTable);
			SimpleFragment fragments = translateToIntermediateCode(program, symbolTable, false, true);
//			ProcFragment canonicalizedFragments = doCanonicalization(fragments, false);
//			ProcFragment nativeCodeFragments = doCodegen(canonicalizedFragments, false);
//			ProcFragment graphStuffShit = doGraphStuff(nativeCodeFragments, false);
//			List<ProcFragment> revFragments = doRegisterAllocation(graphStuffShit, false);
//			outputAssembly(revFragments);
			
		} catch (Exception e) {
			System.out.println("ERROR!\n");
			
			if (DEBUG_COMPILER) { 
				System.out.println(e.getMessage());
				System.out.println("\nSTACKTRACE");
				for (StackTraceElement stackTraceError : e.getStackTrace()) {
				    System.out.println("\t" + stackTraceError);
				}
			} else {
				System.out.println(e.getMessage());
			}
			
			System.exit(1);
		}
		
		// No exceptions thrown? -> clean exit
		System.exit(0);
	}
	
	public static void outputAssembly(List<SimpleFragment> revFragments) throws IOException {
		System.out.print("BROTTA' OUTPUTZ IT ALL, SLICK AZZEMBLY... ");
	
		// assume file ends in .java
//		fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
//		fileName = fileName.substring(0, fileName.length() - 5) + ".s";
//		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName));

//		BufferedWriter fileWriter = new BufferedWriter(new FileWriter("out.s"));
//		
//		fileWriter.write(".data\n\n");
//		fileWriter.write(".text\n\n");
//		fileWriter.write(".globl _main\n\n");
//
//		for (SimpleFragment proc : revFragments) {
//			for (Instr instr : proc.proc.body) {
//				fileWriter.write(instr.format(proc.regalloc));
//			}
//		}
//		
//		fileWriter.close();
//		System.out.println("done!");
	}
	
	public static List<SimpleFragment> doRegisterAllocation(SimpleFragment fragments, boolean print) {
		System.out.print("ALLOCIN' SOME REGS FOR YO TEMPS DAWG... ");

		List<SimpleFragment> revFragments = new ArrayList<SimpleFragment>();
//		while (fragments != null) {
//			RegAlloc regalloc = new RegAlloc(fragments.frame, fragments.proc.body, fragments.liveness);
//			for (Instr instr : fragments.proc.body) {
//				System.out.print(instr.format(regalloc));
//			}
//			fragments.regalloc = regalloc;
//			
//			revFragments.add(fragments);
//			fragments = (SimpleFragment) fragments.next;
//		}
//		Collections.reverse(revFragments);
//		System.out.println("done!");
		
		return revFragments;
	}
	
	public static SimpleFragment doGraphStuff(SimpleFragment fragments, boolean print) {
		System.out.print("BUILDIN' SOME GRAPHS AND NIZZLE... ");

		while (fragments != null) {
//			AssemFlowGraph afg = new AssemFlowGraph(fragments.proc.body);
//			
//			if (print) {
//				afg.show(System.out);
//				System.out.println("\n\n");
//			}
//			
//			Liveness liveness = new Liveness(afg, fragments.proc.body);
//			if (print) {
//				System.out.println("Interference graph: ");
//				liveness.show(System.out);
//			}
//			
//			fragments.liveness = liveness;
//			fragments = (SimpleFragment) fragments.next;
		}
//		fragments = firstFrag;
		System.out.println("done!");
		return fragments;
	}
	
	public static SimpleFragment doCodegen(SimpleFragment fragments, boolean print) {
		System.out.print("DOING DA CODEGEN DUDE...");
		
		SimpleFragment firstFrag = fragments;
		
		while (fragments != null) {
//			Codegen codegen = new Codegen(fragments.frame);
//			
//			List<Instr> instr = codegen.codegen(fragments.canonicalized);
//			instr = fragments.frame.procEntryExit2(instr);
//			Proc proc = fragments.frame.procEntryExit3(instr);
//			
//			// printstuff
//			if (print) {
//				System.out.println(proc.toString(new DefaultMap()));
//			}
//			
//			fragments.proc = proc;
//			fragments = (SimpleFragment) fragments.next;
		}
		
		fragments = firstFrag;
		System.out.println("done!");
		return fragments;
	}
	
	public static SimpleFragment canonicalizeFragments(SimpleFragment fragments, boolean print) {
		Canonicalizer canon = new Canonicalizer(print);
		System.out.print("DOING DA CANONICALIZATION DUDE... ");
		SimpleFragment result = canon.canonicalize(fragments);
		System.out.println("done!");
		return result;
	}
	
	public static SimpleFragment translateToIntermediateCode(Program program, SymbolTable symbolTable, boolean printProgress, boolean printResult) throws Exception {
		IntermediateVisitor irVisitor = new IntermediateVisitor(symbolTable, printProgress);
		System.out.print("DOING DA IR CONVERSION MAN... ");
		SimpleFragment result = irVisitor.translate(program);
		System.out.println("done!");
		
		if (printResult) {
			Print printer = new Print(System.out);
			SimpleFragment currentFragment = result;
			
			while (currentFragment != null) {
				System.out.println("\nFRAGMENT: " + currentFragment.labelName);
				System.out.println("FRAME STRUCTURE: " + currentFragment.frame);
				printer.prStm(currentFragment.body);
				currentFragment = (SimpleFragment) currentFragment.next;
			}
		}
		
		return result;
	}
	
	public static void typeCheckProgram(Program program, SymbolTable symbolTable) throws Exception {
		SlowTypeVisitor typeVisitor = new SlowTypeVisitor(symbolTable);
		System.out.print("TYPE CHECKING FO SHIZZLE... ");
		typeVisitor.check(program);
		System.out.println("done!");
	}
	
	public static SymbolTable scopeCheckProgram(Program program, boolean print) throws Exception {
		System.out.print("BADASS SCOPE CHECKING... ");
		ScopeVisitor scopeVisitor = new ScopeVisitor(print);
		SymbolTable result = scopeVisitor.check(program);
		System.out.println("done!");
		return result;
	}

	public static Program parseSourceFile(String inputFile, boolean printResult) throws Exception {
		FileReader inputFileReader = new FileReader(inputFile);
		parser p = new parser(new Scanner(inputFileReader));
		
		System.out.print("PARSING, NINJA STYLE!... ");
		Symbol s = p.parse();
		Program prog = (Program) s.value;
		System.out.println("done!");
		
		if (printResult) {
			ASTPrintVisitor printVisitor = new ASTPrintVisitor();
			printVisitor.visit(prog);
		}
		
		return prog;
	}
	
	
}
