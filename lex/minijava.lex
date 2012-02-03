/* USERCODE */

// nothing yet...

%% /* OPTIONS */

%class Lexer
%unicode
%line
%type Integer

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

