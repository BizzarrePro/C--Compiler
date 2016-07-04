package team.weird.texteditor.semantic;

import java.util.HashMap;

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
	public boolean checkKeyState(String key){
		return !funcTable.containsKey(key);
	}
}
