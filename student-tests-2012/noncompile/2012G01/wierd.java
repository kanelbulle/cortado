// EXT:!ISC
// EXT:!ICG
// EXT:!JVM
// EXT:!SPARC
// EXT:!MIPS
// EXT:!ARM
// EXT:!X86
// EXT:!SPILL
// EXT:!LONG
// EXT:!IWE
// EXT:!NBD
// EXT:!ABC
// EXT:!CLE
// EXT:!CGT
// EXT:!CGE
// EXT:!CEQ
// EXT:!CNE
// EXT:!BDJ
// EXT:!INT32

class Main {
	public static void main(String[] args) {

	}
}

class Sub {
	// Double this. should not be allowed
	public int selfKill(int i) {
		return this.this.selfKill(1);
	}

	// && on non-boolean should not be allowed
	public int selfKill1() {
		return selfKill1() && selfKill1();
	}

	// Empty statements should not be allowed
	public int selfKill2(int i) {
		;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		return 1;
	}
}
