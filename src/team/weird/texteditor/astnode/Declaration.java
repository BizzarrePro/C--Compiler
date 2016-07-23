package team.weird.texteditor.astnode;

import team.weird.texteditor.semantic.SymbolAttr.Type;

public abstract class Declaration {
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
}
