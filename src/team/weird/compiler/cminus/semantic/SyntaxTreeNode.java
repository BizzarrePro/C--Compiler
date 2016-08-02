package team.weird.compiler.cminus.semantic;

import java.util.ArrayList;
import java.util.LinkedList;

import team.weird.compiler.cminus.lexer.Token;

public class SyntaxTreeNode extends Node{
	private LinkedList<Node> childNode = new LinkedList<Node>();
	public SyntaxTreeNode(String symbol){
		super(symbol);
	}
	public String getSymbol(){
		return symbol;
	}
	public void addNewNode(Node node){
		childNode.add(node);
	}
	public LinkedList<Node> getChildList(){
		return childNode;
	}
	public int getChildNumber(){
		return childNode.size();
	}
	public Node getChild(int index){
		return childNode.get(index);
	}
}
