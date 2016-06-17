package team.weird.texteditor.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;

import team.weird.texteditor.lexer.Token;
import team.weird.texteditor.semantic.Node;
import team.weird.texteditor.semantic.SyntaxLeafNode;
import team.weird.texteditor.semantic.SyntaxTreeNode;

public class PredictiveAnalytics extends PredictAnalyticalTable {
	private Stack<Node> stack = new Stack<Node>();
	public HashMap<String, Symbol> UntermSymbolMap;
	public Set<String> TermSymbolSet;
	private String entrance;

	public PredictiveAnalytics(HashMap<String, Symbol> UntermSymbolMap,
			HashSet<String> TermSymbolSet, String entrance)
			throws OverlappedSyntaxException {
		super(UntermSymbolMap, TermSymbolSet);
		this.UntermSymbolMap = UntermSymbolMap;
		this.TermSymbolSet = TermSymbolSet;
		this.entrance = entrance;
	}

	public Node PredictAndAnalyze(Token[] token)
			throws SyntacticErrorException {
		int index = 0;
		int leafNodeIndex = 0;
		Node root = new SyntaxTreeNode(entrance);
		stack.push(root);
		while (!stack.isEmpty() && index < token.length) {
//		 For debugging
//		 System.out.println("---Stack Start---");
//			 for(Node a : stack)
//				 System.out.println(a.getSymbol());
			String peek = stack.peek().getSymbol();
			if (peek.equals(token[index].toString())) {
//				System.out.println(token[index]);
				stack.pop();
				index++;
			} else if (TermSymbolSet.contains(peek))
				throw new SyntacticErrorException(
						"You have an error in your C- syntax; \r\n"
								+ "check the manual that corresponds to your C- version for the right"
								+ "syntax to use near '"
								+ token[index].toString() + "' at line "
								+ token[index].getLineNum());
			else if (UntermSymbolMap.get(peek).predictiveMap.get(token[index]
					.toString()) == null)
				throw new SyntacticErrorException(
						"You have an error in your C- syntax; \r\n"
								+ "check the manual that corresponds to your C- version for the right"
								+ "syntax to use near '"
								+ token[index].toString() + "' at line "
								+ token[index].getLineNum());
			else if (UntermSymbolMap.get(peek).predictiveMap
					.get(token[index].toString()).get(0).equals("empty")){
				((SyntaxTreeNode)stack.pop()).addNewNode(new SyntaxLeafNode());
			}
			else {
				Node peekElement = stack.pop();
				LinkedList<String> production = UntermSymbolMap.get(peek).predictiveMap
						.get(token[index].toString());
				ListIterator<String> productionIter = production
						.listIterator(production.size());
				while (productionIter.hasPrevious()) {
					String temp = productionIter.previous();
					//System.out.println(temp);
					Node childNode;
					if(UntermSymbolMap.containsKey(temp))
						childNode = new SyntaxTreeNode(temp);
					else
						childNode = new SyntaxLeafNode(token[leafNodeIndex++], temp);
					((SyntaxTreeNode) peekElement).addNewNode(childNode);
					stack.push(childNode);
				}
			}
		}
		System.out.println("Ok");
		return root;
	}
}
