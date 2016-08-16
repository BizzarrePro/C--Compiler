package team.weird.compiler.cminus.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import team.weird.compiler.cminus.lexer.Number;
import team.weird.compiler.cminus.lexer.Token;
import team.weird.compiler.cminus.lexer.Word;
import team.weird.compiler.cminus.semantic.ErrorList;
import team.weird.compiler.cminus.semantic.Node;
import team.weird.compiler.cminus.semantic.SemanticException;
import team.weird.compiler.cminus.semantic.SymbolAttr;
import team.weird.compiler.cminus.semantic.SyntaxLeafNode;
import team.weird.compiler.cminus.semantic.SyntaxTreeNode;
import team.weird.compiler.cminus.semantic.SymbolAttr.Attribute;

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
			throws SemanticException {
		FileWriter fw = null;
		File pa = new File("./compile/temp.pa");
		int index = 0;
		Node root = new SyntaxTreeNode(entrance);
		stack.push(root);
		try{
			fw = new FileWriter(pa);
			fw.write(" --------------------------------------------------\r\n");
			fw.write("|               Predictive Analytics               |\r\n");
			fw.write(" --------------------------------------------------\r\n");
			while (!stack.isEmpty() && index < token.length) {
				fw.write("-Top of Stack-\r\n");
				for(Node a : stack)
					fw.write("["+a.getSymbol()+"]\r\n");
	//		 For debugging
	//			System.out.println("---Stack Start---");
	//			for(Node a : stack)
	//				 System.out.println(a.getSymbol());
	//			if(token[index] instanceof Word)
	//				System.out.println(((Word)token[index]).getId());
	//			else 
	//				System.out.println(token[index]);
				fw.write("-Bottom of Stack-\r\n");
				fw.write("\r\n");
				String peek = stack.peek().getSymbol();
				if (peek.equals(token[index].getWord())) {
					leafNodeStack.pop().setToken(token[index]);
					fw.write("["+peek+"] matches ["+token[index]+"] "+"Pop!\r\n");
					stack.pop();
					index++;
				} 
				else if (TermSymbolSet.contains(peek)){
					err.addException(new SemanticException(token[index].getWord(), token[index].getLineNum(), 1));
					throw new SemanticException(token[index].getWord(), token[index].getLineNum(), 1);
				}
				else if (UntermSymbolMap.get(peek).predictiveMap.get(token[index]
						.getWord()) == null){
					System.out.println(token[index]);
					err.addException(new SemanticException(token[index].getWord(), token[index].getLineNum(), 1));
					throw new SemanticException(token[index].getWord(), token[index].getLineNum(), 1);
				}
				else if (UntermSymbolMap.get(peek).predictiveMap
						.get(token[index].getWord()).get(0).equals("empty")){
					fw.write("empty entry,Pop!\r\n");
					stack.pop();
				}
				else {
					Node peekElement = stack.pop();
					fw.write("Push:\r\n");
					LinkedList<String> production = UntermSymbolMap.get(peek).predictiveMap
							.get(token[index].getWord());
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
						fw.write("\t"+childNode.getSymbol()+"\r\n");
					}
					fw.write("\r\n");
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return root;
	}
}