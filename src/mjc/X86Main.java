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
import se.cortado.ir.common.CanonicalizedFragment;
import se.cortado.ir.common.NativeFragment;
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
		
		// TODO: Test input + parameters for correctness
		String inputFile = args[0];
		
		// TODO: should be set depending on commandline parameter
		Architecture.setArchitecture("X86");
		
		try {
			Program program = parseSourceFile(inputFile, false);
			SymbolTable symbolTable = scopeCheckProgram(program, false);
			typeCheckProgram(program, symbolTable);
			SimpleFragment fragments = translateToIntermediateCode(program, symbolTable, false, false);
			CanonicalizedFragment canonicalizedFragments = canonicalizeFragments(fragments, false, false);
			NativeFragment nativeCodeFragments = generateNativeCode(canonicalizedFragments, false);
			NativeFragment graphStuffShit = doGraphStuff(nativeCodeFragments, true);
//			List<NativeFragment> revFragments = doRegisterAllocation(graphStuffShit, true);
//			finilizeAssembly(revFragments, inputFile);
			
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
	
	/* Adds assembly headers and outputs *.s assembly source file */
	public static void finilizeAssembly(List<NativeFragment> revFragments, String fileName) throws IOException {
		System.out.print("BROTTA' OUTPUTZ IT ALL, SLICK AZZEMBLY... ");
	
		// assume file ends in .java
		fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		fileName = fileName.substring(0, fileName.length() - 5) + ".s";
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName));
		
		fileWriter.write(".data\n\n");
		fileWriter.write(".text\n\n");
		fileWriter.write(".globl _main\n\n");

		for (NativeFragment proc : revFragments) {
			for (Instr instr : proc.proc.body) {
				fileWriter.write(instr.format(proc.regalloc));
			}
		}
		
		fileWriter.close();
		System.out.println("done!");
	}
	
	public static List<NativeFragment> doRegisterAllocation(NativeFragment fragments, boolean print) {
		System.out.print("ALLOCIN' SOME REGS FOR YO TEMPS DAWG... ");

		List<NativeFragment> revFragments = new ArrayList<NativeFragment>();
		while (fragments != null) {
			RegAlloc regalloc = new RegAlloc(fragments.frame, fragments.proc.body, fragments.liveness);
			
			if (print) {
				for (Instr instr : fragments.proc.body) {
					System.out.print(instr.format(regalloc));
				}
			}
			
			fragments.regalloc = regalloc;
			revFragments.add(fragments);
			fragments = (NativeFragment) fragments.next;
		}
		Collections.reverse(revFragments);
		System.out.println("done!");
		
		return revFragments;
	}
	
	public static NativeFragment doGraphStuff(NativeFragment fragments, boolean print) {
		System.out.print("BUILDIN' SOME GRAPHS AND NIZZLE... ");
		NativeFragment firstFrag = fragments;
		
		while (fragments != null) {
			AssemFlowGraph afg = new AssemFlowGraph(fragments.proc.body);
			
			if (print) {
				afg.show(System.out);
				System.out.println("\n\n");
			}
			
			Liveness liveness = new Liveness(afg, fragments.proc.body);
			if (print) {
				System.out.println("Interference graph: ");
				liveness.show(System.out);
			}
			
			fragments.liveness = liveness;
			fragments = (NativeFragment) fragments.next;
		}
		
		fragments = firstFrag;
		System.out.println("done!");
		return fragments;
	}
	
	public static NativeFragment generateNativeCode(CanonicalizedFragment fragments, boolean print) {
		System.out.print("DOING DA CODEGEN DUDE...");
		NativeFragment firstFragment = null;
		NativeFragment currentFragment = null;
		
		while (fragments != null) {
			Codegen codegen = new Codegen(fragments.frame);
			
			List<Instr> instr = codegen.codegen(fragments.body);
			instr = fragments.frame.procEntryExit2(instr);
			Proc proc = fragments.frame.procEntryExit3(instr);
		
			NativeFragment frag = new NativeFragment(instr, fragments.frame, proc);
			if (firstFragment == null) {
				firstFragment = frag;
				currentFragment = firstFragment;
			} else {
				currentFragment.next = frag;
				currentFragment = (NativeFragment) currentFragment.next;
			}
			
			// printstuff
			if (print) {
				System.out.println(proc.toString(new DefaultMap()));
			}
			
			fragments = (CanonicalizedFragment) fragments.next;
		}
		
		System.out.println("done!");
		
		return firstFragment;
	}
	
	public static CanonicalizedFragment canonicalizeFragments(SimpleFragment fragments, boolean printProgress, boolean printResult) {
		Canonicalizer canon = new Canonicalizer(printProgress);
		System.out.print("DOING DA CANONICALIZATION DUDE... ");
		CanonicalizedFragment result = canon.canonicalize(fragments);
		System.out.println("done!");
		
		if (printResult) {
			Print printer = new Print(System.out);
			CanonicalizedFragment currentFragment = result;
			
			while (currentFragment != null) {
				System.out.println("FRAME STRUCTURE: " + currentFragment.frame);
				System.out.println("CANONICALIZED IR CODE FOR FRAGMENT:");
				printer.prStm(currentFragment.body.head);
				currentFragment = (CanonicalizedFragment) currentFragment.next;
				System.out.println();
			}
		}
		
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
				System.out.println("FRAME STRUCTURE: " + currentFragment.frame);
				System.out.println("IR CODE FOR FRAGMENT:");
				printer.prStm(currentFragment.body);
				currentFragment = (SimpleFragment) currentFragment.next;
				System.out.println();
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
