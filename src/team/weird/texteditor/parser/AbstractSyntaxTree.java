package team.weird.texteditor.parser;

import java.util.Iterator;

public class AbstractSyntaxTree {
	private SyntaxTreeNode root = null ;
	private static int depth = 1;
	public AbstractSyntaxTree(SyntaxTreeNode entrance){
		this.root = entrance;
		displayTree(root);
	}
	public void displayTree(SyntaxTreeNode root){
		System.out.println(root.toString()+" <depth>: "+ depth);
		depth++;
		Iterator<SyntaxTreeNode> displayIter = root.getChildList().iterator();
		System.out.print("<|");
		while(displayIter.hasNext()){
			System.out.print(displayIter.next().getSymbol()+" ");
		}
		System.out.print("|>");
		System.out.println();	
		Iterator<SyntaxTreeNode> iter = root.getChildList().iterator();
		while(iter.hasNext()){
			displayTree(iter.next());
		}
		depth--;
		
	}
	public void postOrderTraverse(){
		
	}
	
}
