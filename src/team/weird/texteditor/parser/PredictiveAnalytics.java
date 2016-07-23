package team.weird.texteditor.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import team.weird.texteditor.lexer.Token;
import team.weird.texteditor.lexer.Word;
import team.weird.texteditor.lexer.Number;
import team.weird.texteditor.semantic.ErrorList;
import team.weird.texteditor.semantic.FuncTable;
import team.weird.texteditor.semantic.Node;
import team.weird.texteditor.semantic.SymbolAttr;
import team.weird.texteditor.semantic.SymbolAttr.Attribute;
import team.weird.texteditor.semantic.SyntaxLeafNode;
import team.weird.texteditor.semantic.SyntaxTreeNode;
import team.weird.texteditor.semantic.SymbolTable;

public class PredictiveAnalytics extends PredictAnalyticalTable {
	/**
	 * @param:
	 */
	private Stack<Node> stack = new Stack<Node>();
	private Stack<SyntaxLeafNode> leafNodeStack = new Stack<SyntaxLeafNode>();
	private HashMap<String, Symbol> UntermSymbolMap;
	private ErrorList err = ErrorList.getInstance();
	private Set<String> TermSymbolSet;
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
		Node root = new SyntaxTreeNode(entrance);
		stack.push(root);
		while (!stack.isEmpty() && index < token.length) {
//		 For debugging
//			System.out.println("---Stack Start---");
//			for(Node a : stack)
//				 System.out.println(a.getSymbol());
//			if(token[index] instanceof Word)
//				System.out.println(((Word)token[index]).getId());
//			else 
//				System.out.println(token[index]);
			String peek = stack.peek().getSymbol();
			if (peek.equals(token[index].toString())) {
				leafNodeStack.pop().setToken(token[index]);
				stack.pop();
				index++;
			} 
			else if (TermSymbolSet.contains(peek))
				//err.addException(new SyntacticErrorException(token[index].toString(), token[index].getLineNum(), 1));
				throw new SyntacticErrorException(token[index].toString(), token[index].getLineNum(), 1);
			else if (UntermSymbolMap.get(peek).predictiveMap.get(token[index]
					.toString()) == null)
				throw new SyntacticErrorException(token[index].toString(), token[index].getLineNum(), 1);
				//err.addException(new SyntacticErrorException(token[index].toString(), token[index].getLineNum(), 1));
			else if (UntermSymbolMap.get(peek).predictiveMap
					.get(token[index].toString()).get(0).equals("empty")){
				stack.pop();
			}
			else {
				Node peekElement = stack.pop();
				LinkedList<String> production = UntermSymbolMap.get(peek).predictiveMap
						.get(token[index].toString());
				ListIterator<String> productionIter = production
						.listIterator(production.size());
				while (productionIter.hasPrevious()) {
					String temp = productionIter.previous();
					Node childNode = null;
					if(UntermSymbolMap.containsKey(temp))
						childNode = new SyntaxTreeNode(temp);
					else if(!temp.equals("empty")){
						childNode = new SyntaxLeafNode(temp);
						leafNodeStack.push((SyntaxLeafNode) childNode);
					}
					((SyntaxTreeNode) peekElement).addNewNode(childNode);
					stack.push(childNode);
				}
			}
		}
		return root;
	}
}