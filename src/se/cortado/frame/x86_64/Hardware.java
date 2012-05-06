package se.cortado.frame.x86_64;

import se.cortado.ir.temp.Temp;
import se.cortado.ir.temp.TempList;

public class Hardware {
	public static final int		wordSize	= 8;

	private static final Temp	rax			= new Temp();
	private static final Temp	rbx			= new Temp();
	private static final Temp	rcx			= new Temp();
	private static final Temp	rdx			= new Temp();

	private static final Temp	rsp			= new Temp();
	private static final Temp	rbp			= new Temp();
	private static final Temp	rsi			= new Temp();
	private static final Temp	rdi			= new Temp();

	private static final Temp	r8			= new Temp();
	private static final Temp	r9			= new Temp();
	private static final Temp	r10			= new Temp();
	private static final Temp	r11			= new Temp();

	private static final Temp	r12			= new Temp();
	private static final Temp	r13			= new Temp();
	private static final Temp	r14			= new Temp();
	private static final Temp	r15			= new Temp();

	// TODO appels says at p. 199 that 'the four lists of registers must not
	// overlap', but these for lists overlap in the x86 specification (some regs
	// are both an arg reg and a special reg). what does he mean?
	public static final Temp[]	specialRegs	= { rsp, rbp, rax, rdx };

	public static final Temp[]	argRegs		= { rdi, rsi, rdx, rcx, r8, r9 };

	public static final Temp[]	calleeSaves	= { rbp, rbx, rsp, r12, r13, r14, r15 };

	public static final Temp[]	callerSaves	= { rax, rcx, rdx, rsi, rdi, r8, r9, r10, r11 };

	public static final Temp	SP			= rsp;
	public static final Temp	FP			= rbp;
	public static final Temp	RV			= rax;

	public static final TempList	calldefs;
	
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
