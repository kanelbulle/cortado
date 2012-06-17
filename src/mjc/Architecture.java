package mjc;

import se.cortado.frame.Frame;

/**
 * Helper class to make it easier to target multiple architectures
 * 
 * TODO: Add more architecture dependent objects as needed
 */
public class Architecture {
	
	private static boolean isDefined = false;
	private static Frame frame;
	
	/* Valid: X86 (ARM?) */
	public static void setArchitecture(String arch) {
		if (arch.equals("X86")) {
			frame = new se.cortado.x86_64.frame.Frame();
			
			/* Lastly when everything is defined, set 
			 * flag to indicate everythig is OK */
			isDefined = true;
		}
	}
	
	public static Frame getMotherFrame() throws Exception {
		if (isDefined) {
			return frame;
		} else {
			throw new Exception("Architecture not defined!");
		}
	}
	
	/* public static Hardware getHardware ...? */
}
