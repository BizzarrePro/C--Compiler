package team.weird.compiler.cminus.astnode;

import team.weird.compiler.cminus.semantic.Type;

public class Variable implements PrintASTree{
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
	public String toString(){
		return "["+type+"]"+" '"+id+"'";
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "Variable: " + id );
	}
}
