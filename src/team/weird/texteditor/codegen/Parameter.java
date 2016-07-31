package team.weird.texteditor.codegen;

import team.weird.texteditor.semantic.Type;

public class Parameter {
	private Type type;
	private String name;
	private boolean isArray;
	private Parameter nextParam;
	public Parameter(){
		
	}
	public Parameter(Type type, String name, boolean isArray){
		this.type = type;
		this.name = name;
		this.isArray = isArray;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isArray() {
		return isArray;
	}
	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}
	public Parameter getNextParam() {
		return nextParam;
	}
	public void setNextParam(Parameter nextParam) {
		this.nextParam = nextParam;
	}
	
}
