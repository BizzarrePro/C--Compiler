package team.weird.texteditor.astnode;

public abstract class Expression {
	private String id = null;
	private int line = 0;
	public Expression(){
		
	}
	public Expression(String id, int line){
		this.id = id;
		this.line = line;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public abstract void print(String tab);
}
