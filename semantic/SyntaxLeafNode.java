package team.weird.texteditor.semantic;

import team.weird.texteditor.lexer.Token;

public class SyntaxLeafNode extends Token{
	private Token token = null;
	public SyntaxLeafNode() { super("empty");}
	public SyntaxLeafNode(Token token){
		super(token.toString());
		this.token = token;
	}
	public Token getToken(){
		return token;
	}
	public void setToken(Token tok){
		this.token = tok;
	}
	public String toString(){
		return token.toString();
	}
}
