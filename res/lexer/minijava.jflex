/* ---------------- USERCODE (copied verbatim to generated class) ---------------- */

package se.cortado;

import java.io.*;
import se.cortado.sym;
import java_cup.runtime.*;

%% 

/* ---------------- OPTIONS ---------------- */

/* Name of the class that jFlex will produce */
%class Scanner

/* Tell jFlex to treat all input as unicode */
%unicode

/* Enables line numbering (the current line number can be accessed via the variable yyline) */
%line

/* Switches column counting on (current column is accessed via yycolumn) */
%column

/* Creates a main function in the generated class, outputs line/col numbers (if enabled) */
//%debug
//%standalone

/* ---------------- CLASS OPTIONS ---------------- */
/* Make generated class implement interfaces */
//%interface "interface 1"[, "interface 2", ..]

/* Make generated class extend class (only one class permitted) */
//%extends "classname"

/* Defines visibility (%public | %final | %abstract | %apiprivate) */
%public 

/* The code enclosed in %init{ and %init} is copied verbatim into the constructor of the generated class. Here, member variables declared in the %{...%} directive can be initialised. */
//%init{...%init}

/* Adds the specified argument to the constructors of the generated scanner. Note: conflicts with %standalone and %debug */
//%ctorarg "type" "ident"


/* ---------------- SCANNING ---------------- */

%{
	public Symbol token(int tokenType) {
		if (tokenType == sym.error) {
			System.out.println("sym.error");
		}
	
		System.err.println("<token>\t -- \"" + yytext() + "\"" );
	
		System.out.println("yyline:" + yyline);
		System.out.println("yychar:" + yycolumn);
		System.out.println("yytext:" + yytext());

		return new Symbol(tokenType, yyline, yycolumn, yytext());
	}
%}

/* The type of object that yylex() will return. The default end of file value under this setting is null. */
%type Symbol

%cup

/* The exceptions listed inside %yylexthrow{ ... %yylexthrow} will be declared in the throws clause of the scanning method. */
//%yylexthrow "exception1" [, "exception2", ...]


/* ---------------- DECLARATIONS ---------------- */

/* Defines a set of lexical states, there can be more than one. */
%state MAIN

/* ---------------- DECLARATIONS ---------------- */

/* Defines a set of lexical states, there can be more than one. */
%state MAIN

NEWLINE = \r|\n|\r\n
WS = [ \t\f]
COMMENT   = "/*" [^*] ~"*/" | "/*" "*"+ "/"

%% /* ---------------- LEXICAL RULES ---------------- */

"class" 						{ return token(sym.CLASS); }
"static"						{ return token(sym.STATIC); }
"void"							{ return token(sym.VOID); }
"return"						{ return token(sym.RETURN); }
//"public static void main"		{ yybegin(MAIN); return token(sym.); }
//	<MAIN> "String"				{ yybegin(YYINITIAL); return token(sym.); }
"public" 						{ return token(sym.PUBLIC); }
"new"							{ return token(sym.NEW); }

/* ----- Comments (skipped) ----- */
{COMMENT} 					{ }

/* ----- Statements ----- */
"if" 							{ return token(sym.IF); }
"else" 							{ return token(sym.ELSE); }
"while" 						{ return token(sym.WHILE); }
"System.out.println" 			{ return token(sym.SYSOUT); }

/* ----- Expressions ----- */
"length"						{ return token(sym.LENGTH); }
"true"							{ return token(sym.TRUE); }
"false"							{ return token(sym.FALSE); }
"this"							{ return token(sym.THIS); }
//new int [Exp]					{ return token(sym.); }
// new id()						{ return token(sym.); }
"!"								{ return token(sym.BANG); }

/* ----- Datatypes ----- */
"int" 							{ return token(sym.INT); }
int\[([0-9]+ | [a-zA-Z0_9]+)\] 	{ return token(sym.INT_ARRAY); }
String\[\]						{ return token(sym.STRING_ARRAY); }
"boolean" 						{ return token(sym.BOOLEAN); }
[a-zA-Z]([0-9a-zA-Z] | _)* 		{ return token(sym.IDENTIFIER); }

/* Operators */
"(" 							{ return token(sym.LPAREN); }
")" 							{ return token(sym.RPAREN); }
"[" 							{ return token(sym.LBRACKET); }
"]" 							{ return token(sym.RBRACKET); }
"{" 							{ return token(sym.LCURLY); }
"}" 							{ return token(sym.RCURLY); }
"&&" 							{ return token(sym.AND); }
"<" 							{ return token(sym.LESS); }
"+" 							{ return token(sym.PLUS); }
"-" 							{ return token(sym.MINUS); }
"*" 							{ return token(sym.MULTIPLY); }
"="								{ return token(sym.ASSIGN); }

/* Separators */
";"								{ return token(sym.SEMI); }
","								{ return token(sym.COMMA); }
"."								{ return token(sym.DOT); }

/* Integer */
0|[1-9][0-9]* 					{ return token(sym.INTEGER); }

/* String */
// \"([0-9a-zA-Z] | {WS})*\" | \"\" 	{ return token(sym.STRING); }

/* Whitespace (i.e: newline, tabs space) - Ignored */
{WS} | {NEWLINE} 					{ }

/* Non matched input = invalid input, inform parser exception */
. | {NEWLINE} 						{ return token(sym.error); }
<<EOF>> 							{ System.out.println("reached EOF"); return token(sym.EOF); }

