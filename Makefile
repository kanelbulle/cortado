all: cortado

cortado: lexer parser
	javac -d bin/ -classpath "lib/java-cup-11a-runtime.jar" gen/se/cortado/*.java src/se/cortado/*.java

lexer: res/lexer/minijava.jflex
	jflex -d gen/se/cortado/ res/lexer/minijava.jflex

parser: res/parser/minijava.cup
	java -jar lib/java-cup-11a.jar -package se.cortado -parser Parser -symbols Sym -destdir gen/se/cortado/ res/parser/minijava.cup
