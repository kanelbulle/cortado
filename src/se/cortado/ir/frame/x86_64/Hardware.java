package se.cortado.ir.frame.x86_64;

import se.cortado.ir.temp.Temp;

@SuppressWarnings("unused")
public class Hardware {
	public static final int wordSize = 8;

	private static final Temp rax = new Temp();
	private static final Temp rbc = new Temp();
	private static final Temp rcx = new Temp();
	private static final Temp rdx = new Temp();

	private static final Temp rsp = new Temp();
	private static final Temp rbp = new Temp();
	private static final Temp rsi = new Temp();
	private static final Temp rdi = new Temp();

	private static final Temp r8 = new Temp();
	private static final Temp r9 = new Temp();
	private static final Temp r10 = new Temp();
	private static final Temp r11 = new Temp();

	private static final Temp r12 = new Temp();
	private static final Temp r13 = new Temp();
	private static final Temp r14 = new Temp();
	private static final Temp r15 = new Temp();

	private static final Temp[] argRegs = { rdi, rsi, rdx, rcx, r8, r9 };

	private static final Temp[] retRegs = { rax, rdx };

	public static final Temp SP = rsp;
	public static final Temp FP = rbp;
	public static final Temp RV = retRegs[0];

	public static Temp getArgReg(int index) {
		return argRegs[index];
	}

	public static Temp getRetReg(int index) {
		return retRegs[index];
	}
}
