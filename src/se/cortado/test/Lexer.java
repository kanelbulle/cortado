package se.cortado.test;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;

import se.cortado.sym;
import se.cortado.Scanner;

/**
 * Test cases for token scanner
 * @author Samuel Wejéus
 */
public class Lexer {

	private Scanner mScanner;
	private PipedInputStream mIn;
	PrintWriter mOut;
	
	public Lexer() {
		System.out.print("Setting up I/O streams and creating new scanner... ");
		
		mIn = new PipedInputStream();
		
		PipedOutputStream out = null;
		try {
			out = new PipedOutputStream(mIn);
		} catch (IOException e) {
			e.printStackTrace();
		}

		mOut = new PrintWriter(out);
		mScanner = new Scanner(mIn);
		
		System.out.println("done.");
	}
	
	@Test
	public void testOperators() {

		int[] expected = { sym.LPAREN, sym.RPAREN, sym.LBRACKET, sym.RBRACKET, sym.LCURLY, sym.RCURLY, sym.AND, sym.LESS, sym.PLUS, sym.MINUS, sym.MULTIPLY };
		String[] actual = { "(", ")", "[", "]", "{", "}", "&&", "<",  "+", "-", "*"};
		
		for (int i = 0; i < actual.length; ++i) {
			try {
				mOut.println(actual[i]);
				mOut.flush();
				
				Symbol s = mScanner.next_token();
				assertEquals("Failed lexing operator: \"" + actual[i] + "\"", expected[i], s.sym);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}

}
