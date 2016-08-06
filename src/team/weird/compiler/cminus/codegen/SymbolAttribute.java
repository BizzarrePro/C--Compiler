package team.weird.compiler.cminus.codegen;

import team.weird.compiler.cminus.semantic.Type;

public class SymbolAttribute {
	private String id;
	private Type type;
	private int regNum;
	private boolean isArray;
	private int length = -1;
	public SymbolAttribute(String id, Type type, boolean isArray, int regNum){
		this.id = id;
		this.type = type;
		this.isArray = isArray;
		this.regNum = regNum;
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
	public int getRegNum() {
		return regNum;
	}
	public void setRegNum(int regNum) {
		this.regNum = regNum;
	}
	public boolean isArray() {
		return isArray;
	}
	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
}
