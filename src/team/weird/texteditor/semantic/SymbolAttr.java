package team.weird.texteditor.semantic;

import java.util.ArrayList;
import java.util.List;

public class SymbolAttr {
	public enum Attribute{
		VAR, ARRAY, FUNC
	}
	public enum Type{
		INT, DOUBLE, BOOL, FLOAT, VOID
	}
	private Type type = null;
	private Attribute attr= null;
	private int arrayLength = 0;
	private List<SymbolAttr> paramsList = new ArrayList<SymbolAttr>();
	//for variable
	public SymbolAttr(Type type, Attribute attr){
		this.type = type;
		this.attr = attr;
	}
	//for array
	public SymbolAttr(Type type, Attribute attr, int arrayLength){
		this.type = type;
		this.attr = attr;
		this.arrayLength = arrayLength;
	}
	//for function
	public SymbolAttr(Type type, Attribute attr, List<SymbolAttr> paramsList){
		this.type = type;
		this.attr = attr;
		this.paramsList = paramsList;
	}
	//for null parameter
	public SymbolAttr(Type type){
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
	public void addParameter(SymbolAttr e){
		assert attr == Attribute.FUNC;
		paramsList.add(e);
	}
}
