package team.weird.texteditor.semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import team.weird.texteditor.semantic.SymbolAttr.Attribute;
import team.weird.texteditor.semantic.Type;

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
	/**
	 * If every parameter of function is match with function 
	 * which was invoked then return true, else return false
	 * @return
	 */
	public boolean parametersMatched(List<Type> typeList, String identify){
		if(funcTable.containsKey(identify)){
			for(int i = 0; i < typeList.size(); i++){
				if(getSymbolAttribute(identify).getParameter(i).getType() != typeList.get(i))
					return false;
			}
			return true;
		}
		return false;
	}
	public SymbolAttr getSymbolAttribute(String key){
		return funcTable.get(key);
	}
	public Type getReturnType(String key){
		return funcTable.get(key).getType();
	}
	public int getFuncNum(){
		return funcTable.size();
	}
}
