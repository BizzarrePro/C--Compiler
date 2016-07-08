package team.weird.texteditor.semantic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

import team.weird.texteditor.lexer.Token;
import team.weird.texteditor.lexer.Word;
import team.weird.texteditor.parser.EliminationOfLeftRecursion;

public class AbstractSyntaxTree {
	private static AbstractSyntaxTree INSTANCE = null;
	private Node root = null;
	private HashSet<String> terminatingSet = null;
	private static int depth = 1;
	private AbstractSyntaxTree(Node entrance,
			HashSet<String> terminatingSet) {
		this.root = entrance;
		this.terminatingSet = terminatingSet;
//		postOrderTraverse(root, null);
//		displayTreeNode(root);
	}
	public static AbstractSyntaxTree getInstance(Node entrance, HashSet<String> terminatingSet){
		if(INSTANCE == null)
			INSTANCE = new AbstractSyntaxTree(entrance, terminatingSet);
		return INSTANCE;
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
	private void postOrderTraverse(Node node, Node father) {
		if(node instanceof SyntaxLeafNode)
			visitLeafNode((SyntaxLeafNode)node, father);
		else{
			int index = ((SyntaxTreeNode)node).getChildList().size() - 1;
			ListIterator<Node> iter = ((SyntaxTreeNode)node).getChildList().listIterator(index);
			while(iter.hasPrevious())
				postOrderTraverse(iter.previous(), node);
		}
	}
	private void visitLeafNode(SyntaxLeafNode node, Node father) {
		if(node.getToken() instanceof Word){
			
		}	
	}
}
