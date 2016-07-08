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
import team.weird.texteditor.semantic.FuncTable;
import team.weird.texteditor.semantic.Node;
import team.weird.texteditor.semantic.SymbolAttr;
import team.weird.texteditor.semantic.SymbolAttr.Attribute;
import team.weird.texteditor.semantic.SyntaxLeafNode;
import team.weird.texteditor.semantic.SyntaxTreeNode;
import team.weird.texteditor.semantic.SymbolTable;

public class PredictiveAnalytics extends PredictAnalyticalTable {
	private Stack<Node> stack = new Stack<Node>();
	public HashMap<String, Symbol> UntermSymbolMap;
	public Set<String> TermSymbolSet;
	private String entrance;
	public SymbolTable symTable = SymbolTable.getInstance();
	public FuncTable funcTable = FuncTable.getInstance();
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
		boolean CalParameter = false;
		String identify = null;
		String variable = null;
		String type = null;
		String currFunc = null;
		//Temporary function parameters list
		ArrayList<String> TypeList = new ArrayList<String>();
		ArrayList<SymbolAttr> AttrList = new ArrayList<SymbolAttr>();
		Node root = new SyntaxTreeNode(entrance);
		stack.push(root);
		while (!stack.isEmpty() && index < token.length) {
//		 For debugging
			System.out.println("---Stack Start---");
			for(Node a : stack)
				 System.out.println(a.getSymbol());
			if(token[index] instanceof Word)
				System.out.println(((Word)token[index]).getId());
			else 
				System.out.println(token[index]);
			String peek = stack.peek().getSymbol();
			if (peek.equals(token[index].toString())) {
				if((peek.equals("int") || peek.equals("double") || peek.equals("float") || peek.equals("bool"))){
					type = peek;
					identify = ((Word)token[index+1]).getId();
				}
				else if(peek.equals("{")){
					symTable.createNewScope();
					symTable.putAllInSymbolTable(TypeList, AttrList, TypeList.size());
					TypeList.clear();
					AttrList.clear();
				}
				else if(peek.equals("}")){
					symTable.destroyOldScope();
				}
				stack.pop();
				index++;
			} 
			else if (TermSymbolSet.contains(peek))
				throw new SyntacticErrorException(token[index].toString(), token[index].getLineNum(), 1);
			else if (UntermSymbolMap.get(peek).predictiveMap.get(token[index]
					.toString()) == null)
				throw new SyntacticErrorException(token[index].toString(), token[index].getLineNum(), 1);
			else if (UntermSymbolMap.get(peek).predictiveMap
					.get(token[index].toString()).get(0).equals("empty")){
				Node peekElement = stack.pop();
				((SyntaxTreeNode)peekElement).addNewNode(new SyntaxLeafNode());
				if(CalParameter && peekElement.equals("param-temp")){
					if(!TypeList.contains(identify)){
						TypeList.add(identify);
						AttrList.add(new SymbolAttr(type, Attribute.VAR));
						funcTable.getSymbolAttribute(currFunc).addParameter(identify);
					}
					else
						throw new SyntacticErrorException(identify, token[index].getLineNum(), 2);
				}
			}
			else {
				Node peekElement = stack.pop();
				if(CalParameter && token[index].equals(")"))
					CalParameter = false;
				switch(peekElement.toString()){
					case "var-declaration":	
						switch(token[index].toString()){
							case ";": 	
								if(symTable.checkKeyState(identify))
									symTable.putInSymbolTable(identify, new SymbolAttr(type, Attribute.VAR));
								else
									throw new SyntacticErrorException(identify, token[index].getLineNum(), 2);
								break;
							case "[":	
								if(symTable.checkKeyState(identify))
									symTable.putInSymbolTable(identify, new SymbolAttr(type, Attribute.ARRAY, ((Number)token[index+1]).getNum()));
								else
									throw new SyntacticErrorException(identify, token[index].getLineNum(), 2);
								break;
						}
						break;
					case "fun-declaration":
						if(token[index].equals("(")){
							CalParameter = true;
							currFunc = identify;
							if(funcTable.checkKeyState(identify))
								funcTable.putInFuncTable(identify, new SymbolAttr(type, Attribute.FUNC));
							else
								throw new SyntacticErrorException(identify, token[index].getLineNum(), 2);
						}
						break;
					case "param-temp":
						if(CalParameter && token[index].equals("[")){
							if(!TypeList.contains(identify)){
								TypeList.add(identify);
								AttrList.add(new SymbolAttr(type, Attribute.ARRAY));
								funcTable.getSymbolAttribute(currFunc).addParameter(identify);
							}
							else
								throw new SyntacticErrorException(identify, token[index].getLineNum(), 2);
						}
						break;
					case "factor":
						if(token[index].equals("ID"))
							variable = ((Word)token[index]).getId();
						break;
					case "factor-temp":
						if(token[index].equals("(") && funcTable.checkKeyState(variable))
							throw new SyntacticErrorException(variable, token[index].getLineNum(), 3);
						else if(!token[index].equals("(") && !symTable.checkVariableExist(variable))
							throw new SyntacticErrorException(variable, token[index].getLineNum(), 0);	
						break;
					case "expression":
						if(token[index].equals("ID"))
							variable = ((Word)token[index]).getId();
						break;
					case "expression-sub":
						if(token[index].equals("(") && funcTable.checkKeyState(variable))
							throw new SyntacticErrorException(variable, token[index].getLineNum(), 3);
						else if(!token[index].equals("(") && !symTable.checkVariableExist(variable))
							throw new SyntacticErrorException(variable, token[index].getLineNum(), 0);
						break;		
				}	
				LinkedList<String> production = UntermSymbolMap.get(peek).predictiveMap
						.get(token[index].toString());
				ListIterator<String> productionIter = production
						.listIterator(production.size());
				while (productionIter.hasPrevious()) {
					String temp = productionIter.previous();
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