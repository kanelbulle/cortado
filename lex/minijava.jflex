/* ---------------- USERCODE ---------------- */

// nothing yet...

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
%state A

//TYPE = T_INTARRAY | "int" | "boolean"
//	T_INTARRAY = "int[([0-9]+ | "[a-zA-Z0_9]+")]"
	
NEWLINE = \r|\n|\r\n
WS = {NEWLINE} | [ \t\f]

LPAR = "("
RPAR = ")"
AND = "&&"
LESS = "<"
PLUS = "+"
MINUS = "-"
MULTIPLY = "*"

//IF = "if"
//ELSE = "else"

//InputCharacter = [^\r\n]

%% /* LEXICAL RULES */

//{WS}* "(test)" {WS}* { System.out.println("<hard> \"" + yytext() + "\""); return 1; }
{LPAR} | {RPAR} | {AND} | {LESS} | {PLUS} | {MINUS} | {MULTIPLY} { System.out.println("<operator> \"" + yytext() + "\""); return 1; }

[a-z]+ { System.out.println("<text>"); return 2; }
[1-9][0-9]* { System.out.println("<integer>"); return 3; }

{WS} { /* ignore whitespace (i.e: newline, tabs space) */ }

. | {NEWLINE} { System.exit(-1); }

