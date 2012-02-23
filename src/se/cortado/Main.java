package se.cortado;

import java.io.File;

public class Main {
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java Main input_file");
		}
		
		String fileName = args[1];
		Parser p = new Parser(new File(fileName));
		
		try  {
			p.parse();
		} catch (Exception e) {
			System.out.println("NO PARSE FOR YOU!");
			e.printStackTrace();
		}
	}
}
