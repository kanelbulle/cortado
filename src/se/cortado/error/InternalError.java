package se.cortado.error;

public class InternalError extends java.lang.Error {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2395741845688336814L;
	private String				msg;

	public InternalError(String s) {
		msg = "Internal compiler error!\n" + s;
	}

	public String toString() {
		return msg;
	}
}
