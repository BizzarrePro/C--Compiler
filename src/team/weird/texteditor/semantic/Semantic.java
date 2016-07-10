package team.weird.texteditor.semantic;

import java.util.ArrayList;
import java.util.List;

import team.weird.texteditor.lexer.Token;
import team.weird.texteditor.lexer.Word;
import team.weird.texteditor.lexer.Number;
import team.weird.texteditor.parser.SyntacticErrorException;
import team.weird.texteditor.semantic.SymbolAttr.Attribute;
import team.weird.texteditor.semantic.SymbolAttr.Type;

public class Semantic {
	private Node root = null;
	private SymbolTable symTable = SymbolTable.getInstance();
	private FuncTable funcTable = FuncTable.getInstance();
	private ErrorList err = ErrorList.getInstance();
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
		if(((SyntaxTreeNode)currNode).getChildNumber() == 3){
			Type type = null;
			String identify = null;
			int line = 0;
			Node child0 = ((SyntaxTreeNode)currNode).getChild(2);
			type = type_declaration(child0);
			Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
			Token tok = ((SyntaxLeafNode)child1).getToken();
			identify = ((Word)tok).getWord();
			line = tok.getLineNum();
			Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
			general_declaration(child2, identify, type, line);
		}
	}
	private void declaration_list_elim(Node currNode){
		
	}
	private Type type_declaration(Node currNode){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		switch(child.getSymbol()){
			case "int":
				return Type.INT;
			case "double":
				return Type.DOUBLE;
			case "float":
				return Type.FLOAT;
			case "bool":
				return Type.BOOL;
			default:
				return Type.VOID;	
		}
	}
	private void general_declaration(Node currNode, String identify, Type type, int line){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		if(child.equals("var-declaration"))
			var_daclaration(child, identify, type, line);
		else
			fun_declaration(child, identify, type, line);
	}
	private void var_daclaration(Node currNode, String identify, Type type, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 1){
			if(symTable.checkKeyState(identify))
				symTable.putInSymbolTable(identify, new SymbolAttr(type, Attribute.VAR));
			else
				err.addException(new SyntacticErrorException(identify, line, 2));
		}
		else {
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			int length = 0;
			Token tok = ((SyntaxLeafNode)child).getToken();
			if(tok instanceof Number){
				length = ((Number)tok).getNum();
				if(symTable.checkKeyState(identify))
					symTable.putInSymbolTable(identify, new SymbolAttr(type, Attribute.ARRAY, length));
				else
					err.addException(new SyntacticErrorException(identify, line, 2));
			}
			else 
				err.addException(new SyntacticErrorException(identify, line, 5));
		}
	}
	private void fun_declaration(Node currNode, String identify, Type type, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 1){
			
		}
		else {
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			List<SymbolAttr> paramList = new ArrayList<SymbolAttr>();
			params(child, paramList);
			if(funcTable.checkKeyState(identify)){
				funcTable.putInFuncTable(identify, new SymbolAttr(type, Attribute.FUNC, paramList));
			}
			else
				err.addException(new SyntacticErrorException(identify, line, 5));
		}
	}
	private void params(Node currNode, List<SymbolAttr> paramList){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		if(child instanceof SyntaxLeafNode)
			paramList.add(new SymbolAttr(Type.VOID));
		else {
			
		}
			
	}
}
