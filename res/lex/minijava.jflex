/* ---------------- USERCODE (copied verbatim to generated class) ---------------- */

package se.cortado;

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
%standalone

/* ---------------- CLASS OPTIONS ---------------- */
/* Make generated class implement interfaces */
//%interface "interface 1"[, "interface 2", ..]

/* Make generated class extend class (only one class permitted) */
//%extends "classname"

/* Defines visibility */
// %public | %final | %abstract | %apiprivate

/* The code enclosed in %init{ and %init} is copied verbatim into the constructor of the generated class. Here, member variables declared in the %{...%} directive can be initialised. */
//%init{...%init}

/* Adds the specified argument to the constructors of the generated scanner. Note: conflicts with %standalone and %debug */
//%ctorarg "type" "ident"


/* ---------------- SCANNING METHOD ---------------- */

/* The type of object that yylex() will return. The default end of file value under this setting is null. */
//%type Integer

/* The exceptions listed inside %yylexthrow{ ... %yylexthrow} will be declared in the throws clause of the scanning method. */
//%yylexthrow "exception1" [, "exception2", ...]


/* ---------------- DECLARATIONS ---------------- */

/* Defines a set of lexical states, there can be more than one. */
%state MAIN
	
NEWLINE = \r|\n|\r\n
WS = [ \t\f]
COMMENT   = "/*" [^*] ~"*/" | "/*" "*"+ "/"

LPAR = "("
RPAR = ")"
LBRACKET = "["
RBRACKET = "]"
LCURLY = "{"
RCURLY = "}"
AND = "&&"
LESS = "<"
PLUS = "+"
MINUS = "-"
MULTIPLY = "*"

%% /* ---------------- LEXICAL RULES ---------------- */

"class" 					{ System.out.println("<class>"); return 1; }
"public static void main"	{ yybegin(MAIN); System.out.println("<main>"); return 1; }
	<MAIN> "String"			{ yybegin(YYINITIAL); System.out.println("<main: String>"); return 1; }
"public" 					{ System.out.println("<public>"); return 1; }


/* ----- Comments (skipped) ----- */
{COMMENT} 		{ System.out.println("<comment>\t -- " + yytext()); }


/* ----- Statements ----- */
"if" 						{ System.out.println("<if>"); return 1; }
"else" 						{ System.out.println("<else>"); return 1; }
"while" 					{ System.out.println("<while>"); return 1; }
"System.out.println" 		{ System.out.println("<println>"); return 1; }


/* ----- Expressions ----- */
"length"					{ System.out.println("<length>"); return 1; }
//int_lit //TODO: what is int_lit?
"true"						{ System.out.println("<true>"); return 1; }
"false"						{ System.out.println("<false>"); return 1; }
"this"						{ System.out.println("<this>"); return 1; }
//new int [Exp]				{ System.out.println("<>"); return 1; }
// new id()					{ System.out.println("<>"); return 1; }
"!"							{ System.out.println("<bang>"); return 1; }


/* ----- Datatypes: int, int[], boolean, "identifier" ----- */
"int" 							{ System.out.println("<int>"); return 1; }
int\[([0-9]+ | [a-zA-Z0_9]+)\] 	{ System.out.println("<int[X]>\t -- X = " + yytext()); return 1; }
"boolean" 						{ System.out.println("<boolean>"); return 1; }
[a-zA-Z]([0-9a-zA-Z] | _)* 		{ System.out.println("<identifier>\t -- " + yytext()); return 2; }


/* Operators */
{LPAR} | {RPAR} | {LBRACKET} | {RBRACKET} | {LCURLY} | {RCURLY} | {AND} | {LESS} | {PLUS} | {MINUS} | {MULTIPLY} { System.out.println("<operator>\t -- " + yytext()); return 1; }

/* Integer */
[1-9][0-9]* { System.out.println("<integer>\t -- " + yytext()); return 3; }

/* String */
\"([0-9a-zA-Z] | {WS})*\" | \"\" { System.out.println("<string>\t -- " + yytext()); }

/* Whitespace (i.e: newline, tabs space) - Ignored */
{WS} | {NEWLINE} {}

/* Non matched input = invalid input, inform parser by throwing exception */
. | {NEWLINE} { throw new Error("LEX: Illegal character <"+yytext()+">"); }

