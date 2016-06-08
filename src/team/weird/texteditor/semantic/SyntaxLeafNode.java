package team.weird.texteditor.semantic;

import team.weird.texteditor.lexer.Token;

public class SyntaxLeafNode extends Node{
	private Token token = null;
	public SyntaxLeafNode() { super("empty");}
	public SyntaxLeafNode(Token token, String symbol){
		super(symbol);
		this.token = token;
	}
	public Token getToken(){
		return token;
	}
	public void setToken(Token tok){
		this.token = tok;
	}
}
