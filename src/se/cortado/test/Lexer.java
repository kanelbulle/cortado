package se.cortado.test;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;

import se.cortado.Sym;
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
		int[] expected = { Sym.LPAREN, Sym.RPAREN, Sym.LBRACKET, Sym.RBRACKET, Sym.LCURLY, Sym.RCURLY, Sym.AND, Sym.LESS, Sym.PLUS, Sym.MINUS, Sym.MULTIPLY };
		String[] actual = { "(", ")", "[", "]", "{", "}", "&&", "<",  "+", "-", "*"};
		
		for (int i = 0; i < actual.length; ++i) {
			try {
				mOut.println(actual[i]);
				mOut.flush();
				
				Symbol s = mScanner.yylex();
				assertEquals("Failed lexing operator: \"" + actual[i] + "\"", expected[i], s.sym);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}

}
