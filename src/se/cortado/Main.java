package se.cortado;

import java.io.File;

public class Main {
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java Main input_file");
		}
		
		String fileName = args[0];
		parser p = new parser(new File(fileName));
		
		try  {
			System.out.println("" + p.debug_parse());
		} catch (Exception e) {
			System.out.println("NO PARSE FOR YOU!");
			e.printStackTrace();
		}
	}
}
