package team.weird.texteditor.lexer;

public class Bound extends Token{
	private char ch = ' ';
	public Bound(char ch){
		super();
		this.ch = ch;
	}
	public String toString(){
		return ""+ch;
	}
}
