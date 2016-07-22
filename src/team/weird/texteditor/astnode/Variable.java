package team.weird.texteditor.astnode;

import team.weird.texteditor.semantic.SymbolAttr.Type;

public class Variable {
	private String id;
	private Type type;
	private boolean isArray;
	public Variable(String id, Type type, boolean isArray){
		this.id = id;
		this.type = type;
		this.isArray = isArray;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public boolean isArray() {
		return isArray;
	}
	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}
}
