all: 
	javac -d bin/ -classpath "lib/java-cup-11a-runtime.jar" gen/se/cortado/*.java src/se/cortado/*.java
