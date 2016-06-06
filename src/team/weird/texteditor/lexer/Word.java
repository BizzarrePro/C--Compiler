package team.weird.texteditor.lexer;

public class Word extends Token{
	private String id = null;
	public Word(String id, String type){
		super(type);
		this.id = id;
	}
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
}
