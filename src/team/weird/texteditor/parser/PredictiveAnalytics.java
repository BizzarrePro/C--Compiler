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
		//Temporary function parameters list
		ArrayList<String> TypeList = new ArrayList<String>();
		ArrayList<SymbolAttr> AttrList = new ArrayList<SymbolAttr>();
		String identify = null;
		String type = null;
		String currFunc = null;
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
//				System.out.println(token[index]);
				if((peek.equals("int") || peek.equals("double") || peek.equals("float") || peek.equals("bool"))){
					type = peek;
					identify = ((Word)token[index+1]).getId();
				}
				else if(peek.equals("{")){
					symTable.createNewScope();
//					System.out.println(TypeList.size());
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
				if(peekElement.equals("var-declaration") && token[index].equals(";")){
					if(symTable.checkKeyState(identify))
						symTable.putInSymbolTable(identify, new SymbolAttr(type, Attribute.VAR));
					else
						throw new SyntacticErrorException(identify, token[index].getLineNum(), 2);
				}
				else if(peekElement.equals("var-declaration") && token[index].equals("[")){
					if(symTable.checkKeyState(identify))
						symTable.putInSymbolTable(identify, new SymbolAttr(type, Attribute.ARRAY, ((Number)token[index+1]).getNum()));
					else
						throw new SyntacticErrorException(identify, token[index].getLineNum(), 2);
						
						
				}
				else if(peekElement.equals("fun-declaration") && token[index].equals("(")){
					CalParameter = true;
					currFunc = identify;
					if(funcTable.checkKeyState(identify))
						funcTable.putInFuncTable(identify, new SymbolAttr(type, Attribute.FUNC));
					else
						throw new SyntacticErrorException(identify, token[index].getLineNum(), 2);
				}
				else if(CalParameter && token[index].equals(")")){
					CalParameter = false;
				}
				else if(CalParameter && peekElement.equals("param-temp") && token[index].equals("[")){
					if(!TypeList.contains(identify)){
						TypeList.add(identify);
						AttrList.add(new SymbolAttr(type, Attribute.ARRAY));
						funcTable.getSymbolAttribute(currFunc).addParameter(identify);
					}
					else
						throw new SyntacticErrorException(identify, token[index].getLineNum(), 2);
				}
				else if(peekElement.equals("factor")){
					if(token[index].equals("ID") && 
							!symTable.checkVariableExist(((Word)token[index]).getId()))
						throw new SyntacticErrorException(((Word)token[index]).getId(), token[index].getLineNum(), 0);
				}
				else if(peekElement.equals("expression")){
					if(token[index].equals("ID") && 
							!symTable.checkVariableExist(((Word)token[index]).getId())) 
						throw new SyntacticErrorException(((Word)token[index]).getId(), token[index].getLineNum(), 0);
				}
				//Need to add simantic action
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
//bug
//int a(int a, int b){
//int b;
//}$