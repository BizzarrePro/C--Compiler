package team.weird.texteditor.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;

import team.weird.texteditor.lexer.Token;

public class PredictiveAnalytics extends PredictAnalyticalTable{
	private Stack<SyntaxTreeNode> stack = new Stack<SyntaxTreeNode>();
	public HashMap<String, Symbol> UntermSymbolMap;
	public Set<String> TermSymbolSet;
	private String entrance;
	public PredictiveAnalytics(HashMap<String, Symbol> UntermSymbolMap,
			HashSet<String> TermSymbolSet, String entrance) throws OverlappedSyntaxException{
		super(UntermSymbolMap, TermSymbolSet);
		this.UntermSymbolMap = UntermSymbolMap;
		this.TermSymbolSet = TermSymbolSet;
		this.entrance = entrance;
	}
	public void PredictAndAnalyze(Token[] token, int line) throws SyntacticErrorException{
		int index = 0;
		SyntaxTreeNode root = new SyntaxTreeNode(entrance);
		stack.push(root);
		while(!stack.isEmpty() && index < token.length){
			String peek = stack.peek().getSymbol();
			System.out.println("index: "+index);
			System.out.println(peek+" "+token[index].toString());
			if(peek.equals(token[index].toString())){
				stack.pop();
				index++;
			}
			else if(TermSymbolSet.contains(peek))
				throw new SyntacticErrorException("You have an error in your C- syntax; " +
						"check the manual that corresponds to your C- version for the right" +
						"syntax to use near '"+token[index].toString()+"' at line"+ line); 
			else if(UntermSymbolMap.get(peek).predictiveMap.get(token[index].toString()) == null)
				throw new SyntacticErrorException("You have an error in your C- syntax; " +
						"check the manual that corresponds to your C- version for the right" +
						"syntax to use near '"+token[index].toString()+"' at line"+ line);
			else if(UntermSymbolMap.get(peek).predictiveMap.get(token[index].toString()).get(0).equals("empty"))
				stack.pop();
			else{
				SyntaxTreeNode peekElement = stack.pop();
				LinkedList<String> production = UntermSymbolMap.get(peek).predictiveMap.get(token[index].toString());
				System.out.println(production.size());
				ListIterator<String> productionIter = production.listIterator(production.size());
				while(productionIter.hasPrevious()){
					System.out.println(peekElement.getSymbol()+" "+productionIter.hasPrevious()+" "+productionIter.previousIndex());
					String temp = productionIter.previous();
					SyntaxTreeNode childNode = new SyntaxTreeNode(temp);
					peekElement.addNewNode(childNode);
					stack.push(childNode);
				}
			}
		}
		System.out.println("Ok");
	} 
}

class SyntacticErrorException extends Exception {
	/**
	 * Report Syntactic Error
	 */
	private static final long serialVersionUID = 1L;

	public SyntacticErrorException() {

	}

	public SyntacticErrorException(String str) {
		super(str);
	}
}
