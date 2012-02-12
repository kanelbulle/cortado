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
	public Symbol token( int tokenType ) {
		System.err.println("Obtain token \"" + yytext() + "\"" );
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

"class" 					{ System.out.println("<class>"); return token(Sym.PLUS); }
"public static void main"	{ yybegin(MAIN); System.out.println("<main>"); return token(Sym.PLUS); }
	<MAIN> "String"			{ yybegin(YYINITIAL); System.out.println("<main: String>"); return token(Sym.PLUS); }
"public" 					{ System.out.println("<public>"); return token(Sym.PLUS); }


/* ----- Comments (skipped) ----- */
{COMMENT} 					{ System.out.println("<comment>\t -- " + yytext()); }


/* ----- Statements ----- */
"if" 						{ System.out.println("<if>"); return token(Sym.PLUS); }
"else" 						{ System.out.println("<else>"); return token(Sym.PLUS); }
"while" 					{ System.out.println("<while>"); return token(Sym.PLUS); }
"System.out.println" 		{ System.out.println("<println>"); return token(Sym.PLUS); }


/* ----- Expressions ----- */
"length"					{ System.out.println("<length>"); return token(Sym.PLUS); }
//int_lit //TODO: what is int_lit?
"true"						{ System.out.println("<true>"); return token(Sym.PLUS); }
"false"						{ System.out.println("<false>"); return token(Sym.PLUS); }
"this"						{ System.out.println("<this>"); return token(Sym.PLUS); }
//new int [Exp]				{ System.out.println("<>"); return token(Sym.PLUS); }
// new id()					{ System.out.println("<>"); return token(Sym.PLUS); }
"!"							{ System.out.println("<bang>"); return token(Sym.PLUS); }


/* ----- Datatypes: int, int[], boolean, "identifier" ----- */
"int" 							{ System.out.println("<int>"); return token(Sym.PLUS); }
int\[([0-9]+ | [a-zA-Z0_9]+)\] 	{ System.out.println("<int[X]>\t -- X = " + yytext()); return token(Sym.PLUS); }
"boolean" 						{ System.out.println("<boolean>"); return token(Sym.PLUS); }
[a-zA-Z]([0-9a-zA-Z] | _)* 		{ System.out.println("<identifier>\t -- " + yytext()); return token(Sym.PLUS); }


/* Operators */
"(" 		{ System.out.println("<operator>\t -- " + yytext()); return token(Sym.LPAREN); }
")" 		{ System.out.println("<operator>\t -- " + yytext()); return token(Sym.RPAREN); }
"[" 		{ System.out.println("<operator>\t -- " + yytext()); return token(Sym.LBRACKET); }
"]" 		{ System.out.println("<operator>\t -- " + yytext()); return token(Sym.RBRACKET); }
"{" 		{ System.out.println("<operator>\t -- " + yytext()); return token(Sym.LCURLY); }
"}" 		{ System.out.println("<operator>\t -- " + yytext()); return token(Sym.RCURLY); }
"&&" 		{ System.out.println("<operator>\t -- " + yytext()); return token(Sym.AND); }
"<" 		{ System.out.println("<operator>\t -- " + yytext()); return token(Sym.LESS); }
"+" 		{ System.out.println("<operator>\t -- " + yytext()); return token(Sym.PLUS); }
"-" 		{ System.out.println("<operator>\t -- " + yytext()); return token(Sym.MINUS); }
"*" 		{ System.out.println("<operator>\t -- " + yytext()); return token(Sym.MULTIPLY); }

/* Integer */
[1-9][0-9]* { System.out.println("<integer>\t -- " + yytext()); return token(Sym.PLUS); }

/* String */
\"([0-9a-zA-Z] | {WS})*\" | \"\" 	{ System.out.println("<string>\t -- " + yytext()); return token(Sym.PLUS); }

/* Whitespace (i.e: newline, tabs space) - Ignored */
{WS} | {NEWLINE} 					{ System.out.println("<WS>"); }

/* Non matched input = invalid input, inform parser exception */
. | {NEWLINE} 						{ System.out.println("<ERROR>\t -- " + yytext()); return token(Sym.error); }

<<EOF>> 							{ System.out.println("<<EOF>>"); return token(Sym.EOF); }

