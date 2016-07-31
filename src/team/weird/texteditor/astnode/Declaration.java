package team.weird.texteditor.astnode;

import team.weird.texteditor.semantic.Type;

public abstract class Declaration implements PrintASTree{
	protected String id;
	protected Type type;
	protected int line;
	public Declaration(){
		
	}
	public Declaration(String id, Type type, int line){
		this.id = id;
		this.type = type;
		this.line = line;
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
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String toString(){
		return getClass().getSimpleName();
	}
}
