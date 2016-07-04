package team.weird.texteditor.parser;

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
		String identify = null;
		String type = null;
		boolean CalParameter = false;
		Node root = new SyntaxTreeNode(entrance);
		stack.push(root);
		while (!stack.isEmpty() && index < token.length) {
//		 For debugging
		 System.out.println("---Stack Start---");
			 for(Node a : stack)
				 System.out.println(a.getSymbol());
			String peek = stack.peek().getSymbol();
			if (peek.equals(token[index].toString())) {
				System.out.println(token[index]);
				if((peek.equals("int") || peek.equals("double") || peek.equals("float") || peek.equals("bool"))){
					type = peek;
					identify = ((Word)token[index+1]).getId();
				}
				else if(peek.equals("{")){
//					System.out.println(symTable.getSymbolTable().size());
//					for(Map.Entry<String, SymbolAttr> entry : symTable.getSymbolTable().entrySet())
//						System.out.println(entry.getKey());
					symTable.createNewScope();
				}
				else if(peek.equals("}")){
					symTable.destroyOldScope();
				}
				stack.pop();
				index++;
			} else if (TermSymbolSet.contains(peek))
				throw new SyntacticErrorException(token[index].toString(), token[index].getLineNum());
			else if (UntermSymbolMap.get(peek).predictiveMap.get(token[index]
					.toString()) == null)
				throw new SyntacticErrorException(token[index].toString(), token[index].getLineNum());
			else if (UntermSymbolMap.get(peek).predictiveMap
					.get(token[index].toString()).get(0).equals("empty")){
				((SyntaxTreeNode)stack.pop()).addNewNode(new SyntaxLeafNode());
			}
			else {
				Node peekElement = stack.pop();
				if(peekElement.equals("var-declaration") && token[index].equals(";")){
					if(symTable.checkKeyState(identify))
						symTable.putInSymbolTable(identify, new SymbolAttr(type, Attribute.VAR));
					else
						throw new SyntacticErrorException(identify, token[index].getLineNum());
						
				}
				else if(peekElement.equals("var-declaration") && token[index].equals("[")){
					if(symTable.checkKeyState(identify))
						symTable.putInSymbolTable(identify, new SymbolAttr(type, Attribute.ARRAY, ((Number)token[index]).getNum()));
					else
						throw new SyntacticErrorException(identify, token[index].getLineNum());
						
						
				}
				else if(peekElement.equals("fun-declaration") && token[index].equals("(")){
					CalParameter = true;
					if(funcTable.checkKeyState(identify))
						;
//						funcTable.putInFuncTable(identify, new SymbolAttr)
					//leave array problem
				}
				else if(peekElement.equals("param-temp") && token[index].equals(")")){
					CalParameter = false;
				}
				else if(peekElement.equals("factor"))
					if(token[index].equals("ID") && 
							!symTable.checkVariableExist(((Word)token[index]).getId()))
						throw new SyntacticErrorException(((Word)token[index]).getId(), token[index].getLineNum());
				else if(true){}
				//Need to add simantic action
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
//int d;
//int a(void){
//	  int b;
//	  while(1){
//	  int a;
//	  int b;
//	  }
//	}$
