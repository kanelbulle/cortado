/* ---------------- USERCODE (copied verbatim to generated class) ---------------- */

package se.cortado;

import java.io.*;
import se.cortado.Sym;
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
		System.err.println("<token>\t -- \"" + yytext() + "\"" );
		return new Symbol( tokenType, yychar, yychar + yytext().length(), yytext() );
	}
%}

/* The type of object that yylex() will return. The default end of file value under this setting is null. */
%type Symbol

//%cup

/* The exceptions listed inside %yylexthrow{ ... %yylexthrow} will be declared in the throws clause of the scanning method. */
//%yylexthrow "exception1" [, "exception2", ...]


/* ---------------- DECLARATIONS ---------------- */

/* Defines a set of lexical states, there can be more than one. */
%state MAIN
	
NEWLINE = \r|\n|\r\n
WS = [ \t\f]
COMMENT   = "/*" [^*] ~"*/" | "/*" "*"+ "/"

%% /* ---------------- LEXICAL RULES ---------------- */
 
"class" 						{ return token(Sym.CLASS); }
"static"						{ return token(Sym.STATIC); }
"void"							{ return token(Sym.VOID); }
"return"						{ return token(Sym.RETURN); }
//"public static void main"		{ yybegin(MAIN); return token(Sym.); }
//	<MAIN> "String"				{ yybegin(YYINITIAL); return token(Sym.); }
"public" 						{ return token(Sym.PUBLIC); }

/* ----- Comments (skipped) ----- */
{COMMENT} 					{ }

/* ----- Statements ----- */
"if" 							{ return token(Sym.IF); }
"else" 							{ return token(Sym.ELSE); }
"while" 						{ return token(Sym.WHILE); }
"System.out.println" 			{ return token(Sym.SYSOUT); }

/* ----- Expressions ----- */
"length"						{ return token(Sym.LENGTH); }
//int_lit //TODO: what is int_lit?
"true"							{ return token(Sym.TRUE); }
"false"							{ return token(Sym.FALSE); }
"this"							{ return token(Sym.THIS); }
//new int [Exp]					{ return token(Sym.); }
// new id()						{ return token(Sym.); }
"!"								{ return token(Sym.BANG); }

/* ----- Datatypes ----- */
"int" 							{ return token(Sym.INT); }
int\[([0-9]+ | [a-zA-Z0_9]+)\] 	{ return token(Sym.INT_ARRAY); }
"boolean" 						{ return token(Sym.BOOLEAN); }
[a-zA-Z]([0-9a-zA-Z] | _)* 		{ return token(Sym.IDENTIFIER); }

/* Operators */
"(" 							{ return token(Sym.LPAREN); }
")" 							{ return token(Sym.RPAREN); }
"[" 							{ return token(Sym.LBRACKET); }
"]" 							{ return token(Sym.RBRACKET); }
"{" 							{ return token(Sym.LCURLY); }
"}" 							{ return token(Sym.RCURLY); }
"&&" 							{ return token(Sym.AND); }
"<" 							{ return token(Sym.LESS); }
"+" 							{ return token(Sym.PLUS); }
"-" 							{ return token(Sym.MINUS); }
"*" 							{ return token(Sym.MULTIPLY); }
"="								{ return token(Sym.ASSIGN); }
"=="							{ return token(Sym.EQUALS); }

/* Separators */
";"								{ return token(Sym.SEMI); }
","								{ return token(Sym.COMMA); }
"."								{ return token(Sym.DOT); }

/* Integer */
0|[1-9][0-9]* 					{ return token(Sym.NUMBER); }
0|[1-9][0-9]*[lL]				{ return token(Sym.LONG_NUMBER); }

/* String */
\"([0-9a-zA-Z] | {WS})*\" | \"\" 	{ return token(Sym.STRING); }

/* Whitespace (i.e: newline, tabs space) - Ignored */
{WS} | {NEWLINE} 					{ }

/* Non matched input = invalid input, inform parser exception */
. | {NEWLINE} 						{ return token(Sym.error); }
<<EOF>> 							{ return token(Sym.EOF); }

