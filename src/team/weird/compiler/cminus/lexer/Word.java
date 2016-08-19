package team.weird.compiler.cminus.lexer;

public class Word extends Token{
	private String id = null;
	private String type = null;
	public Word(String id, String type){
		super(type);
		this.id = id;
		this.type = type;
	}
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getType(){
		return type;
	}
	public String toString(){
		return "Identify: [ "+type+" ] Identify: [ "+id+" ] Line: "+super.lineNum;
	}
}
