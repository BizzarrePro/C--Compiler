package team.weird.texteditor.semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import team.weird.texteditor.semantic.Type;

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
	/**
	 * If variable exist, return true, else return false.
	 * @param key:Symoltable key 
	 * @return
	 */
	public boolean checkVariableExist(String key){
		for(int i = scopeStk.size()-1; i >= 0; i--)
			if(scopeStk.get(i).containsKey(key))
				return true;
		return false;
	}
	public void destroyOldScope(){
		if(scopeStk.size() > 1)
			scopeStk.pop();
	}
	public boolean checkKeyState(String key){
		return !scopeStk.peek().containsKey(key);
	}
	public HashMap<String, SymbolAttr> getSymbolTable(){
		return scopeStk.peek();
	}
	public SymbolAttr getAttributeofVariable(String key){
		for(int i = scopeStk.size()-1; i >= 0; i--)
			if(scopeStk.get(i).containsKey(key))
				return scopeStk.get(i).get(key);
		return null;
	}
	public void putAllInSymbolTable(ArrayList<String> typeList, ArrayList<SymbolAttr> attrList, int length){
		for(int i = 0; i < length; i++){
			scopeStk.peek().put(typeList.get(i), attrList.get(i));
		}
	}
}
