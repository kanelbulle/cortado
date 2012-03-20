package se.cortado.syntax.visitor;

import java.util.HashMap;

public class SymbolTable {
	private HashMap<String, ClassScope> symbolTable;

	public SymbolTable() {
		symbolTable = new HashMap<String, ClassScope>();
	}
	
	public void put(String key, ClassScope value) {
		symbolTable.put(key, value);
	}
	
	public ClassScope get(String key) {
		return symbolTable.get(key);
	}
	
	public boolean containsKey(String key) {
		return get(key) != null;
	}
	
	public void print() {
		
	}
}
