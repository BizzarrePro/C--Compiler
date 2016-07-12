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
		if(INSTANCE == null)
			INSTANCE = new Semantic(root);
		return INSTANCE;
	}
	public void init() throws Throwable{
		program(root);
		err.throwsAllExceptions();
		System.out.println("OK");
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
			identify = ((Word)tok).getId();
			line = tok.getLineNum();
			Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
			general_declaration(child2, identify, type, line);
		}
		else {
			Node child = ((SyntaxTreeNode)currNode).getChild(0);
			symTable.createNewScope();
			compound_stmt(child);
			symTable.destroyOldScope();
		}
	}
	private void declaration_list_elim(Node currNode){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 0)
			return;
		Node child0 = ((SyntaxTreeNode)currNode).getChild(0);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		declaration(child1);
		declaration_list_elim(child0);	
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
				symTable.putInSymbolTable(identify, new SymbolAttr(identify, type, Attribute.VAR));
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
					symTable.putInSymbolTable(identify, new SymbolAttr(identify, type, Attribute.ARRAY, length));
				else
					err.addException(new SyntacticErrorException(identify, line, 2));
			}
			else 
				err.addException(new SyntacticErrorException(identify, line, 5));
		}
	}
	private void fun_declaration(Node currNode, String identify, Type type, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 1){
			Node child = ((SyntaxTreeNode)currNode).getChild(0);
			Type retType = compound_stmt(child);
			if(type == Type.VOID && retType != Type.NULL)
				err.addException(new SyntacticErrorException(identify, line, 6));
			else if(type != Type.VOID && retType != type)
				err.addException(new SyntacticErrorException(identify, line, 7));;	
		}
		else {
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			List<SymbolAttr> paramList = new ArrayList<SymbolAttr>();
			params(child, paramList);
			if(funcTable.checkKeyState(identify)){
				funcTable.putInFuncTable(identify, new SymbolAttr(identify, type, Attribute.FUNC, paramList));
			}
			else
				err.addException(new SyntacticErrorException(identify, line, 5));
		}
	}
	private void params(Node currNode, List<SymbolAttr> paramList){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		if(child instanceof SyntaxLeafNode){
			paramList.add(new SymbolAttr(Type.VOID));
			return;
		}
		else 
			param_list(child, paramList);	
	}
	private void param_list(Node currNode, List<SymbolAttr> paramList){
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		param(child0, paramList);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
		param_list_elim(child1, paramList);
	}
	private void param(Node currNode, List<SymbolAttr> paramList){
		Type type = null;
		String identify = null;
		int line;
		Node child0 = ((SyntaxTreeNode)currNode).getChild(2);
		type = type_declaration(child0);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		Token tok = ((SyntaxLeafNode)child1).getToken();
		identify = ((Word)tok).getId();
		line = tok.getLineNum();
		Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
		param_temp(child2, identify, type, line, paramList);
	}
	private void param_temp(Node currNode, String identify, Type type, int line, List<SymbolAttr> paramList){
		SymbolAttr symbol = null;
		if(((SyntaxTreeNode)currNode).getChildNumber() == 1)
			symbol = new SymbolAttr(identify, type, Attribute.VAR);
		else 
			symbol = new SymbolAttr(identify, type, Attribute.ARRAY);
		if(!paramList.contains(symbol))
			paramList.add(symbol);
		else 
			err.addException(new SyntacticErrorException(identify, line, 2));
	}
	private void param_list_elim(Node currNode, List<SymbolAttr> paramList){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 0)
			return;
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		param(child0, paramList);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
		param_list_elim(child1, paramList);
	}
	private Type compound_stmt(Node currNode){
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		local_declarations(child0);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
		return statement_list(child1);
	}
	private void local_declarations(Node currNode){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		local_declarations_elim(child);
	}
	private Type statement_list(Node currNode){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 0)
			return Type.NULL;
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		Type type1 = statement(child0);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
		Type type2 = statement_list(child1);
		return type1 == Type.NULL ? type2 : type1;
	}
	private void local_declarations_elim(Node currNode){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 0)
			return;
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		declaration(child0);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
		local_declarations_elim(child1);
	}
	private Type statement(Node currNode){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		String statement = child.getSymbol();
		switch(statement){
			case "expression-stmt":
				expression_stmt(child);
				break;
			case "compound-stmt":
				symTable.createNewScope();
				compound_stmt(child);
				symTable.destroyOldScope();
				break;
			case "selection-stmt":
				selection_stmt(child);
				break;
			case "iteration-stmt":
				iteration_stmt(child);
				break;
			case "return-stmt":
				return return_stmt(child);	
		}
		return null;
	}
	private void expression_stmt(Node currNode){
		
	}
	private void selection_stmt(Node currNode){
		
	}
	private void iteration_stmt(Node currNode){
	
	}
	private Type return_stmt(Node currNode){
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		if(child0 instanceof SyntaxLeafNode)
			return Type.NULL;
		else
			return expression(child0);
	}
	private Type expression(Node currNode){
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		String identify = ((SyntaxLeafNode)child0).getSymbol();
		switch(identify){
			case "ID":
				Token tok = ((SyntaxLeafNode)child0).getToken();
				String variable = ((Word)tok).getId();
				int line = tok.getLineNum();
				Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
				Attribute attr = expression_sub(child1, variable);
				if(attr != Attribute.FUNC){
					if(!symTable.checkVariableExist(variable))
						err.addException(new SyntacticErrorException(identify, line, 3));
					else
						return symTable.getTypeofVariable(variable);
				}
				else {
					if(funcTable.checkKeyState(variable))
						err.addException(new SyntacticErrorException(identify, line, 8));
					else
						return funcTable.getReturnType(variable);
				}
				return Type.NULL;
			case ")":
				
				break;
			case "NUM":
				
		}
		
	}	
	private Attribute expression_sub(Node currNode, String identify){
		
	}
}
