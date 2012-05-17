package se.cortado.error;

public class ErrorMsg {
	private java.io.PrintStream	out;

	public ErrorMsg(java.io.PrintStream o) {
		out = o;
	}

	public void complain(String msg) {
		out.println(msg);
	}
}
