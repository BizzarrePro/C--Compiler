package team.weird.texteditor.semantic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

import team.weird.texteditor.lexer.Token;
import team.weird.texteditor.lexer.Word;

public class AbstractSyntaxTree {
	private Node root = null;
	private HashSet<String> terminatingSet;
	private static int depth = 1;
	public AbstractSyntaxTree(Node entrance,
			HashSet<String> terminatingSet) {
		this.root = entrance;
		this.terminatingSet = terminatingSet;
//		displayTreeNode(root);
	}
	
	public void displayTreeNode(Node root){
		if(root.isLeaf()&&((SyntaxLeafNode)root).getToken() != null)
			System.out.println(((SyntaxLeafNode)root).getToken().getWord()+" "+depth);
		else if(!root.isLeaf())
			System.out.println(((SyntaxTreeNode)root).getSymbol()+" "+depth);
		if(root.isLeaf())
			return;
		Iterator<Node> displayIter = ((SyntaxTreeNode)root).getChildList().iterator();
		while(displayIter.hasNext()){
			Node temp = displayIter.next();	
			displayTreeNode(temp);
		}
		depth--;
	}
	public void postOrderTraverse(SyntaxTreeNode root) {
		
	}
//	public void postOrderTraverse(SyntaxTreeNode root) {
//		if (root.getChildList().size() == 0) {
//			if (terminatingSet.contains(root.getSymbol()))
//				visitNode(root);
//			else
//				;
//			return;
//		}
//		int index = root.getChildList().size() - 1;
//		ListIterator<SyntaxTreeNode> iter = root.getChildList().listIterator(
//				index);
//		while (iter.hasPrevious())
//			postOrderTraverse(iter.previous());
//	}
//
//	public void visitNode(SyntaxTreeNode node){
//		if(typeSet.contains(node.getSymbol()))
//			type = node.getSymbol();
//		else if(node.getSymbol().equals("ID")){
//			
//		}

}
