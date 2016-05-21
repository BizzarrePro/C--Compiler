package team.weird.texteditor.parser;

import java.util.ArrayList;

public class SyntaxTreeNode{
	private String symbol;
	private ArrayList<SyntaxTreeNode> childNode = new ArrayList<SyntaxTreeNode>();
	public SyntaxTreeNode(String symbol){
		this.symbol = symbol;
	}
	public String getSymbol(){
		return symbol;
	}
	public void addNewNode(SyntaxTreeNode node){
		childNode.add(node);
	}
	public ArrayList<SyntaxTreeNode> getChildList(){
		return childNode;
	}
	public String toString(){
		return "<ROOT>: "+symbol;
	}
}
