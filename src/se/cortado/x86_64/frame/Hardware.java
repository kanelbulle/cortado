package se.cortado.x86_64.frame;

import java.util.HashMap;

import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempList;

public class Hardware {
	public static final int			wordSize	= 8;
	
	private static final HashMap<Temp, String> tempMap = new HashMap<Temp, String>();

	private static final Temp		rax			= new Temp();
	private static final Temp		rbx			= new Temp();
	private static final Temp		rcx			= new Temp();
	private static final Temp		rdx			= new Temp();

	private static final Temp		rsp			= new Temp();
	private static final Temp		rbp			= new Temp();
	private static final Temp		rsi			= new Temp();
	private static final Temp		rdi			= new Temp();

	private static final Temp		r8			= new Temp();
	private static final Temp		r9			= new Temp();
	private static final Temp		r10			= new Temp();
	private static final Temp		r11			= new Temp();

	private static final Temp		r12			= new Temp();
	private static final Temp		r13			= new Temp();
	private static final Temp		r14			= new Temp();
	private static final Temp		r15			= new Temp();

	// TODO appels says at p. 199 that 'the four lists of registers must not
	// overlap', but these for lists overlap in the x86 specification (some regs
	// are both an arg reg and a special reg). what does he mean?
	public static final Temp[]		specialRegs	= { rsp, rbp, rax, rdx };

	public static final Temp[]		argRegs		= { rdi, rsi, rdx, rcx, r8, r9 };

	public static final Temp[]		calleeSaves	= { rbp, rbx, rsp, r12, r13, r14, r15 };

	public static final Temp[]		callerSaves	= { rax, rcx, rdx, rsi, rdi, r8, r9, r10, r11 };

	public static final Temp		SP			= rsp;
	public static final Temp		FP			= rbp;
	public static final Temp		RV			= rax;

	public static final TempList	calldefs;
	public static final TempList	registers;

	static {
		TempList tl = null;
		for (Temp t : callerSaves) {
			if (tl == null) {
				tl = new TempList(t, null);
			} else {
				tl = new TempList(t, tl);
			}
		}
		calldefs = tl;

		// i hate this linked list crap everywhere in appels poop code
		registers = new TempList(rax, 
				new TempList(rbx, 
				new TempList(rcx,
				new TempList(rdx,
				new TempList(rsi,
				new TempList(rdi,
				new TempList(r8,
				new TempList(r9,
				new TempList(r10,
				new TempList(r11,
				new TempList(r12,
				new TempList(r13,
				new TempList(r14,
				new TempList(r15, null))))))))))))));
		
		tempMap.put(rax, "rax");
		tempMap.put(rbx, "rbx");
		tempMap.put(rcx, "rcx");
		tempMap.put(rdx, "rdx");
		tempMap.put(rsi, "rsi");
		tempMap.put(rdi, "rdi");
		tempMap.put(r8, "r8");
		tempMap.put(r9, "r9");
		tempMap.put(r10, "r10");
		tempMap.put(r11, "r11");
		tempMap.put(r12, "r12");
		tempMap.put(r13, "r13");
		tempMap.put(r14, "r14");
		tempMap.put(r15, "r15");
	}
	
	public static String tempName(Temp temp) {
		return tempMap.get(temp);
	}

	public static Temp getArgReg(int index) {
		return argRegs[index];
	}

	public static TempList calleeSavedList() {
		TempList tl = new TempList(calleeSaves[0], null);
		for (int i = 1; i < calleeSaves.length; i++) {
			tl = new TempList(calleeSaves[i], tl);
		}
		return tl;
	}
}
