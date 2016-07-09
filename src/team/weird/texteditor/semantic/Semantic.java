package team.weird.texteditor.semantic;

public class Semantic {
	private Node root = null;
	private static Semantic INSTANCE = null;
	private Semantic(Node root){
		this.root = root;
	}
	public static Semantic getInstance(Node root){
		if(INSTANCE != null)
			INSTANCE = new Semantic(root);
		return INSTANCE;
	}
	private void program(Node currNode){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		declaration_list(child);
	}
	private void declaration_list(Node currNode){
		Node child0 = ((SyntaxTreeNode)currNode).getChild(0);
		declaration_list_elim(child0);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		declaration(child1);
	}
	private void declaration(Node currNode){
		
	}
	private void declaration_list_elim(Node currNode){
		
	}
}
