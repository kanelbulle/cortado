/* USERCODE */

// nothing yet...

%% /* OPTIONS */

/* -------- CLASS OPTIONS -------- */
/* Name of the class that jFlex will produce */
%class Scanner
/* Tell jFlex to treat all input as unicode */
%unicode
/* Make generated class implement interfaces */
//%interface "interface 1"[, "interface 2", ..]
/* Make generated class extend class (only one class permitted) */
//%extends "classname"
/* Defines visibility */
// %public | %final | %abstract | %apiprivate
/* The code enclosed in %init{ and %init} is copied verbatim into the constructor of the generated class. Here, member variables declared in the %{...%} directive can be initialised. */
//%init{...%init}
/* Enables line numbering */
%line
/* Enables column numbering */
%col

/* -------- SCANNER OPTIONS -------- */
/* The type of object that yylex() will return */
%type Integer
/* The exceptions listed inside %yylexthrow{ ... %yylexthrow} will be declared in the throws clause of the scanning method. */
%yylexthrow "exception1" [, "exception2", ...]
/* Creates a main function in the generated class, outputs line/col numbers (if enabled) */
%debug


LineTerminator = \r|\n|\r\n
//InputCharacter = [^\r\n]
//WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
//Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

//TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
//EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}
//DocumentationComment = "/**" {CommentContent} "*"+ "/"
//CommentContent       = ( [^*] | \*+ [^/*] )*

%% /* LEXICAL RULES */

<YYINITIAL> "ping" {LineTerminator}     { System.out.println("<PONG!>"); return null; }

.* {LineTerminator} | {LineTerminator}  { System.out.println("<garbage>"); return null; }

