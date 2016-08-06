package team.weird.compiler.cminus.lexer;

public class Token {
	protected String word;
	protected int lineNum ;
	public Token(){}
	public Token(String word){
		this.word = word;
	}
	public String getWord(){
		return word;
	}
	public boolean equals(Object o){
		if(o == null)	return false;
		if(!(o instanceof Token || o instanceof String))	return false;
		if(o instanceof Token){
			if(this.word.equals(((Token)o).word))
				return true;
		}
		else {
			if(this.word.equals((String)o))
				return true;
		}
		return false;
	}
	public void setLineNum(int lineNum){
		this.lineNum = lineNum;
	}
	public int getLineNum(){
		return lineNum;
	}
	public int hashCode(){
		return word.hashCode();
	}
	public String toString(){
		return word;
	}
	
}
