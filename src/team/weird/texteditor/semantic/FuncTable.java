package team.weird.texteditor.semantic;

import java.util.HashMap;

import team.weird.texteditor.semantic.SymbolAttr.Attribute;
import team.weird.texteditor.semantic.SymbolAttr.Type;

public class FuncTable {
	private static final FuncTable INSTANCE = new FuncTable();
	private HashMap<String, SymbolAttr> funcTable = new HashMap<String, SymbolAttr>();
	private FuncTable(){}
	public static FuncTable getInstance(){
		return INSTANCE;
	}
	public void putInFuncTable(String key, SymbolAttr value){
		funcTable.put(key, value);
	}
	/**
	 * If key exist return false, else return true.
	 * @param key:FunctionTable key
	 * @return
	 */
	public boolean checkKeyState(String key){
		return !funcTable.containsKey(key);
	}
	public SymbolAttr getSymbolAttribute(String key){
		return funcTable.get(key);
	}
	public Type getReturnType(String key){
		return funcTable.get(key).getType();
	}
}
