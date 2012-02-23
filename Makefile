all: cortado

cortado: parser lexer
	javac -d bin/ -classpath "lib/java-cup-11a-runtime.jar" gen/se/cortado/*.java src/se/cortado/*.java src/se/cortado/syntax/visitor/*.java src/se/cortado/syntaxtree/*.java

lexer: res/lexer/minijava.jflex
	rm -f gen/se/cortado/Scanner.java
	jflex -d gen/se/cortado/ res/lexer/minijava.jflex

parser: res/parser/minijava.cup
	rm -f gen/se/cortado/Parser.java
	java -jar lib/java-cup-11a.jar -package se.cortado -destdir gen/se/cortado/ res/parser/minijava.cup
