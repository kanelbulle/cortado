package se.cortado;

import java.io.*;

public class Main {
	
	public static void main(String[] args) throws java.io.FileNotFoundException {
		if (args.length != 1) {
			System.out.println("Usage: java Main input_file");
		}
		
		String fileName = args[0];
		parser p = new parser(new Scanner(new FileReader(fileName)));
		
		try  {
			System.out.println("" + p.debug_parse());
		} catch (Exception e) {
			System.out.println("NO PARSE FOR YOU!");
			e.printStackTrace();
		}
	}
}
