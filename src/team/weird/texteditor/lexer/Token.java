package team.weird.texteditor.lexer;

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
		if(!(o instanceof Token))	return false;
		Token temp = (Token)o;
		if(this.word.equals(temp.word))
			return true;
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
