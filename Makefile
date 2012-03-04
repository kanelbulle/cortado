all: cortado

run:
	javac -d bin/ -classpath "lib/java-cup-11a.jar" gen/se/cortado/*.java src/se/cortado/*.java src/se/cortado/syntax/visitor/*.java src/se/cortado/syntaxtree/*.java
	java -cp bin/:lib/java-cup-11a.jar se.cortado.Main test/basic.java
	
cortado: parser lexer
	javac -d bin/ -classpath "lib/java-cup-11a.jar" gen/se/cortado/*.java src/se/cortado/*.java src/se/cortado/syntax/visitor/*.java src/se/cortado/syntaxtree/*.java

lexer: res/lexer/minijava.jflex
	rm -f gen/se/cortado/Scanner.java
	jflex -d gen/se/cortado/ res/lexer/minijava.jflex

parser: res/parser/minijava.cup
	rm -f gen/se/cortado/parser.java
	rm -f gen/se/cortado/sym.java
	java -jar lib/java-cup-11a.jar -package se.cortado -destdir gen/se/cortado/ -expect 0 res/parser/minijava.cup
