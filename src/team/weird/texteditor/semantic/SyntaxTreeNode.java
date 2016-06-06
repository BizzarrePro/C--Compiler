package team.weird.texteditor.semantic;

import java.util.ArrayList;
import java.util.LinkedList;

import team.weird.texteditor.lexer.Token;

public class SyntaxTreeNode extends Token{
	private String symbol;
	private LinkedList<Token> childNode = new LinkedList<Token>();
	public SyntaxTreeNode(String symbol){
		super(symbol);
		this.symbol = symbol;
	}
	public String getSymbol(){
		return symbol;
	}
	public void addNewNode(Token node){
		childNode.add(node);
	}
	public LinkedList<Token> getChildList(){
		return childNode;
	}
}
