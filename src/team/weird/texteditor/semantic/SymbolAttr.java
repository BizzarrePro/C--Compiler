package team.weird.texteditor.semantic;

import java.util.ArrayList;
import java.util.List;

public class SymbolAttr {
	public enum Attribute{
		VAR, ARRAY, FUNC
	}
	public enum Type{
		INT, DOUBLE, BOOL, FLOAT, VOID, NULL
	}
	private String identify = null;
	private Type type = null;
	private Attribute attr= null;
	private int arrayLength = 0;
	private List<SymbolAttr> paramsList = new ArrayList<SymbolAttr>();
	//for variable
	public SymbolAttr(String identify, Type type, Attribute attr){
		this.identify = identify;
		this.type = type;
		this.attr = attr;
	}
	//for array
	public SymbolAttr(String identify, Type type, Attribute attr, int arrayLength){
		this.identify = identify;
		this.type = type;
		this.attr = attr;
		this.arrayLength = arrayLength;
	}
	//for function
	public SymbolAttr(String identify, Type type, Attribute attr, List<SymbolAttr> paramsList){
		this.identify = identify;
		this.type = type;
		this.attr = attr;
		this.paramsList = paramsList;
	}
	//for null parameter
	public SymbolAttr(Type type){
		this.type = type;
	}
	public SymbolAttr(Attribute attr, Type type){
		this.attr = attr;
		this.type = type;
	}
	public void setAttribute(Attribute attr){
		this.attr = attr;
	}
	public void setType(Type type){
		this.type = type;
	}
	public void setArrayLength(int arrayLength){
		this.arrayLength = arrayLength;
	}
	public Type getType(){
		return type;
	}
	public Attribute getAttribute(){
		return attr;
	}
	public int getArrayLength(){
		return arrayLength;
	}
	public String getIdentify(){
		return identify;
	}
	public void addParameter(SymbolAttr e){
		assert attr == Attribute.FUNC;
		paramsList.add(e);
	}
	//Return parameter at the specified position in the list
	public SymbolAttr getParameter(int index){
		if(paramsList.isEmpty())
			return null;
		return paramsList.get(index);
	}
	public int getListSize(){
		return paramsList.size();
	}
	public boolean equals(Object o){
		if(o == null)	return false;
		if(!(o instanceof SymbolAttr))	return false;
		SymbolAttr temp = (SymbolAttr)o;
		if(temp.getIdentify() == null || this.identify == null)
			return false;
		if(temp.getIdentify().equals(this.identify))
			return true;
		return false;
	}
	public int hashCode(){
		return identify.hashCode();
	}
}
