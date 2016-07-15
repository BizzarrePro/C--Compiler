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
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		declaration(child1);
		Node child0 = ((SyntaxTreeNode)currNode).getChild(0);
		declaration_list_elim(child0);
		
	}
	private Type declaration(Node currNode){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 1){
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
			return general_declaration(child2, identify, type, line); 
		}
		else {
			Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
			Type retType = compound_stmt(child2);
			symTable.destroyOldScope();
			return retType;
//			if(type == Type.VOID && retType != Type.NULL)
//				err.addException(new SyntacticErrorException(identify, line, 6));
//			else if(type != Type.VOID && retType != type)
//				err.addException(new SyntacticErrorException(identify, line, 7));
			
		}
	}
	private void declaration_list_elim(Node currNode){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 0)
			return;
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		Node child0 = ((SyntaxTreeNode)currNode).getChild(0);
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
	private Type general_declaration(Node currNode, String identify, Type type, int line){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		if(child instanceof SyntaxTreeNode){
			var_daclaration(child, identify, type, line);
			return Type.NULL;
		}
		else{
			symTable.createNewScope();
			Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
			List<SymbolAttr> paramList = new ArrayList<SymbolAttr>();
			params(child1, paramList);
			if(funcTable.checkKeyState(identify))
				funcTable.putInFuncTable(identify, new SymbolAttr(identify, type, Attribute.FUNC, paramList));
			else
				err.addException(new SyntacticErrorException(identify, line, 14));
			return type;
		}
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
		Node child0 = ((SyntaxTreeNode)currNode).getChild(2);
		local_declarations(child0);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
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
		if(((SyntaxTreeNode)currNode).getChildNumber() == 2){
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			expression(child);
		}
	}
	private void selection_stmt(Node currNode){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(3);
		Type type = expression(child1);
		Node child2 = ((SyntaxTreeNode)currNode).getChild(4);
		Token tok = ((SyntaxLeafNode)child2).getToken();
		int line = tok.getLineNum();
		if(type == Type.VOID)
			err.addException(new SyntacticErrorException("(", line, 12));
		Node child3 = ((SyntaxTreeNode)currNode).getChild(1);
		statement(child3);
		Node child4 = ((SyntaxTreeNode)currNode).getChild(0);
		selection_stmt_temp(child4);
	}
	private void selection_stmt_temp(Node currNode){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
			statement(child2);
		}
	}
	private void iteration_stmt(Node currNode){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(2);
		Type type = expression(child1);
		Node child3 = ((SyntaxTreeNode)currNode).getChild(3);
		Token tok = ((SyntaxLeafNode)child3).getToken();
		int line = tok.getLineNum();
		if(type == Type.NULL)
			err.addException(new SyntacticErrorException("(", line, 13));
		Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
		statement(child2);
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
			{
				Token tok = ((SyntaxLeafNode)child0).getToken();
				String variable = ((Word)tok).getId();
				int line = tok.getLineNum();
				Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
				Attribute attr = expression_sub(child1, variable, line);
				if(attr != Attribute.FUNC){
					if(!symTable.checkVariableExist(variable))
						err.addException(new SyntacticErrorException(identify, line, 3));
					else
						return symTable.getAttributeofVariable(variable).getType();
				}
				else {
					if(funcTable.checkKeyState(variable))
						err.addException(new SyntacticErrorException(identify, line, 8));
					else
						return funcTable.getReturnType(variable);
				}
				return Type.NULL;
			}
			case ")":{
				Node child = ((SyntaxTreeNode)currNode).getChild(1);
				int line = ((SyntaxLeafNode)child).getToken().getLineNum();
				Node child1 = ((SyntaxTreeNode)currNode).getChild(2);
				Type type1 = expression(child1);
				Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
				Type type2 = expression_none(child2, line);
				if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
					err.addException(new SyntacticErrorException(type1, type2, line));
				return type1;
			}
			case "NUM":{
				Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
				Token tok = ((SyntaxLeafNode)child0).getToken();
				int line = tok.getLineNum();
				Type type = expression_none(child1, line);
				if(tok instanceof Number){
					if(type != Type.INT && type != Type.NULL)
						err.addException(new SyntacticErrorException(type, Type.INT, line));
					return Type.INT;
				}
				else {
					if(type != Type.FLOAT && type != Type.NULL)
						err.addException(new SyntacticErrorException(type, Type.FLOAT, line));
					return Type.FLOAT;	
				}
			}
		}
		return Type.NULL;
	}	
	private Attribute expression_sub(Node currNode, String identify, int line){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		if(child1 instanceof SyntaxTreeNode){
			Attribute attr = var(child1, identify, line);
			Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
			Type type1 = expression_sub_sub(child2, line);
			Type type2 = null;
			if(symTable.getAttributeofVariable(identify) == null)
				err.addException(new SyntacticErrorException(identify, line, 3));
			else{
				type2 = symTable.getAttributeofVariable(identify).getType();
				if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
					err.addException(new SyntacticErrorException(type1, type2, line));
			}
			return attr;
			
		} else {
			List<Type> paramList = new ArrayList<Type>();
			Node child2 = ((SyntaxTreeNode)currNode).getChild(2);
			args(child2, paramList);
			if(funcTable.getSymbolAttribute(identify) != null && paramList.size() == funcTable.getSymbolAttribute(identify).getListSize()){
				if(!funcTable.parametersMatched(paramList, identify))
					err.addException(new SyntacticErrorException(identify, line, 11));
			}
			else{
				err.addException(new SyntacticErrorException(identify, line, 11));
			}
			return Attribute.FUNC;
		}
	}
	private Attribute var(Node currNode, String identify, int line){
		Attribute attr = null;
		if(((SyntaxTreeNode)currNode).getChildNumber() == 0){
			if(symTable.getAttributeofVariable(identify) == null)
				err.addException(new SyntacticErrorException(identify, line, 3));
			else if (symTable.getAttributeofVariable(identify).getAttribute() == Attribute.ARRAY)
				err.addException(new SyntacticErrorException(identify, line, 9));
			attr = Attribute.VAR;
		}
		else {
			if(symTable.getAttributeofVariable(identify) == null)
				err.addException(new SyntacticErrorException(identify, line, 3));
			else if (symTable.getAttributeofVariable(identify).getAttribute() == Attribute.VAR)
				err.addException(new SyntacticErrorException(identify, line, 10));
			attr = Attribute.ARRAY;
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			expression(child);
		}
		return attr;
	}
	private Type expression_none(Node currNode, int line){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(2);
		Type type1 = term_temp(child1, line);
		Node child2 = ((SyntaxTreeNode)currNode).getChild(1);
		Type type2 = additive_expression_temp(child2, line);
		if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
			err.addException(new SyntacticErrorException(type1, type2, line));
		Node child3 = ((SyntaxTreeNode)currNode).getChild(0);
		Type type3 = simple_expression(child3, line);
		if(type2 != type3 && type2 != Type.NULL && type3 != Type.NULL)
			err.addException(new SyntacticErrorException(type2, type3, line));
		return type1;
	}
	private Type expression_sub_sub(Node currNode, int line){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		if(((SyntaxTreeNode)currNode).getChildNumber() == 1)
			return expression_none(child, line);
		else{
			return expression(child);
		}	
	}
	private Type term_temp(Node currNode, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			Node child3 = ((SyntaxTreeNode)currNode).getChild(2);
			mulop(child3);
			Node child2 = ((SyntaxTreeNode)currNode).getChild(1);
			Type type2 = factor(child2);
			Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
			Type type1 = term_temp(child1, line);
			//need to add code for generating intermediate code
			if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
				err.addException(new SyntacticErrorException(type1, type2, line));
			return type1;
		}
		else 
			return Type.NULL;
	}
	private Type additive_expression_temp(Node currNode, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			Node child1 = ((SyntaxTreeNode)currNode).getChild(2);
			addop(child1);
			//need to add code for generating intermediate code
			Node child2 = ((SyntaxTreeNode)currNode).getChild(1);
			Type type1 = term(child2, line);
			Node child3 = ((SyntaxTreeNode)currNode).getChild(0);
			Type type2 = additive_expression_temp(child3, line);
			if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
				err.addException(new SyntacticErrorException(type1, type2, line));
			return type1;
		}
		else 
			return Type.NULL;
	}
	private Type simple_expression(Node currNode, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			Node child = ((SyntaxTreeNode)currNode).getChild(0);
			return additive_expression(child, line);
		}
		else {
			return Type.NULL;
		}
	}
	private Type additive_expression(Node currNode, int line){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		Type type1 = term(child1, line);
		Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
		Type type2 = additive_expression_temp(currNode, line);
		if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
			err.addException(new SyntacticErrorException(type1, type2, line));
		return type1;
	}
	private Type factor(Node currNode){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		if(child instanceof SyntaxTreeNode){
			Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
			Token tok = ((SyntaxLeafNode)child1).getToken();
			String identify = ((Word)tok).getId();
			int line = tok.getLineNum();
			Attribute attr = factor_temp(child, identify, line);
			if(attr != Attribute.FUNC){
				if(!symTable.checkVariableExist(identify))
					err.addException(new SyntacticErrorException(identify, line, 3));
				else
					return symTable.getAttributeofVariable(identify).getType();
			}
			else {
				if(funcTable.checkKeyState(identify))
					err.addException(new SyntacticErrorException(identify, line, 8));
				else
					return funcTable.getReturnType(identify);
			}
			return Type.NULL;
				
		}
		else {
			if(child.getSymbol().equals("NUM"))
				return Type.INT;
			else {
				Node child2 = ((SyntaxTreeNode)currNode).getChild(1);
				return expression(child2);
			}
		}
	}
	private Attribute factor_temp(Node currNode, String identify, int line){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		if(child instanceof SyntaxTreeNode)
			return var(child, identify, line);
		else {
			List<Type> paramList = new ArrayList<Type>();
			Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
			args(child1, paramList);
			if(funcTable.getSymbolAttribute(identify) != null && paramList.size() == funcTable.getSymbolAttribute(identify).getListSize()){
				if(!funcTable.parametersMatched(paramList, identify))
					err.addException(new SyntacticErrorException(identify, line, 11));
			}
			else{
				err.addException(new SyntacticErrorException(identify, line, 11));
			}
			return Attribute.FUNC;
		}
	}
	private Type term(Node currNode, int line){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		Type type1 = factor(child1);
		Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
		Type type2 = term_temp(child2, line);
		if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
			err.addException(new SyntacticErrorException(type1, type2, line));
		return type1;
	}
	private void args(Node currNode, List<Type> list){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			Node child = ((SyntaxTreeNode)currNode).getChild(0);
			args_list(child, list);
		} 
		else {
			list.add(Type.VOID);
		}
	}
	private void args_list(Node currNode, List<Type> list){
		Node child = ((SyntaxTreeNode)currNode).getChild(1);
		Type type = expression(child);
		list.add(type);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
		args_list_elim(child1, list);
	}
	private void args_list_elim(Node currNode, List<Type> list){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			Type type = expression(child);
			list.add(type);
			Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
			args_list_elim(child1, list);
		}
	}
	private void mulop(Node currNode){
		
	}
	private void addop(Node currNode){
		
	}
}
