package team.weird.texteditor.semantic;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {
	private static final SymbolTable INSTANCE = new SymbolTable();
	private Stack<HashMap<String, SymbolAttr>> scopeStk = new Stack<HashMap<String, SymbolAttr>>();
	private SymbolTable(){
		scopeStk.add(new HashMap<String, SymbolAttr>());
	}
	public static SymbolTable getInstance(){
		return INSTANCE;
	}
	public void putInSymbolTable(String key, SymbolAttr value){
		scopeStk.peek().put(key, value);
	}
	public void createNewScope(){
		scopeStk.add(new HashMap<String, SymbolAttr>());
	}
	public void destroyOldScope(){
		if(scopeStk.size() > 1)
			scopeStk.pop();
	}
	public boolean checkKeyState(String key){
		return scopeStk.peek().get(key) == null ? true : false;
	}
	public HashMap<String, SymbolAttr> getSymbolTable(){
		return scopeStk.peek();
	}
}
