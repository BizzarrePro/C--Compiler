package team.weird.texteditor.lexer;

public class Token {
	private String word;
	private String type;
	public Token(){}
	public Token(String word, String type){
		this.word = word;
		this.type = type;
	}
	public String getWord(){
		return word;
	}
	public String getType(){
		return type;
	}
	public boolean equals(Object o){
		if(o == null)	return false;
		if(!(o instanceof Token))	return false;
		Token temp = (Token)o;
		if(this == temp)
			return true;
		return false;
	}
	public int hashCode(){
		return word.hashCode();
	}
	public String toString(){
		return "Word: "+word+" TYPE: "+ type;
	}
	
}
