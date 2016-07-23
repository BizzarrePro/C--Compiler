package team.weird.texteditor.semantic;

import java.util.ArrayList;
import java.util.List;

import team.weird.texteditor.astnode.ArrayDeclaration;
import team.weird.texteditor.astnode.BinaryExpression;
import team.weird.texteditor.astnode.CallExpression;
import team.weird.texteditor.astnode.CompoundStatement;
import team.weird.texteditor.astnode.Declaration;
import team.weird.texteditor.astnode.Expression;
import team.weird.texteditor.astnode.ExpressionStatement;
import team.weird.texteditor.astnode.FunctionDeclaration;
import team.weird.texteditor.astnode.IterationStatement;
import team.weird.texteditor.astnode.LiteralExpression;
import team.weird.texteditor.astnode.Operator;
import team.weird.texteditor.astnode.Program;
import team.weird.texteditor.astnode.ReturnStatement;
import team.weird.texteditor.astnode.SelectionStatement;
import team.weird.texteditor.astnode.Statement;
import team.weird.texteditor.astnode.Variable;
import team.weird.texteditor.astnode.VariableDeclaration;
import team.weird.texteditor.lexer.FloatNumber;
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
	private static Program program = Program.getInstance();
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
		program.addDeclaration(declaration(child1));
		Node child0 = ((SyntaxTreeNode)currNode).getChild(0);
		declaration_list_elim(child0);
		
	}
//done
	private Declaration declaration(Node currNode){
//		if(((SyntaxTreeNode)currNode).getChildNumber() != 1){
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
//		}
//		else {
//			Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
//			Type retType = compound_stmt(child2);
//			symTable.destroyOldScope();
//			if(type == Type.VOID && retType != Type.NULL)
//				err.addException(new SyntacticErrorException(identify, line, 6));
//			else if(type != Type.VOID && retType != type)
//				err.addException(new SyntacticErrorException(identify, line, 7));
			
//		}
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
//done	
	private Declaration general_declaration(Node currNode, String identify, Type type, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 1){
			Node child = ((SyntaxTreeNode)currNode).getChild(0);
			return var_daclaration(child, identify, type, line);
		}
		else{
			FunctionDeclaration function = new FunctionDeclaration(identify, type, line);
			Node child1 = ((SyntaxTreeNode)currNode).getChild(2);
			List<Variable> paramList = new ArrayList<Variable>();
			params(child1, paramList);
			function.setParameters(paramList);
			Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
			function.setStatement(compound_stmt(child2));
			return function;
//			if(funcTable.checkKeyState(identify))
//				funcTable.putInFuncTable(identify, new SymbolAttr(identify, type, Attribute.FUNC, paramList));
//			else
//				err.addException(new SyntacticErrorException(identify, line, 14));
		}
	}
//done
	private VariableDeclaration var_daclaration(Node currNode, String identify, Type type, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 1){
			if(symTable.checkKeyState(identify))
				symTable.putInSymbolTable(identify, new SymbolAttr(identify, type, Attribute.VAR));
			else
				err.addException(new SyntacticErrorException(identify, line, 2));
			return new VariableDeclaration(identify, type, line);
		}
		else {
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			int length = 0;
			Token tok = ((SyntaxLeafNode)child).getToken();
			length = ((Number)tok).getNum();
			if(tok instanceof Number){
				if(symTable.checkKeyState(identify))
					symTable.putInSymbolTable(identify, new SymbolAttr(identify, type, Attribute.ARRAY, length));
				else
					err.addException(new SyntacticErrorException(identify, line, 2));
			}
			else 
				err.addException(new SyntacticErrorException(identify, line, 5));
			return new ArrayDeclaration(identify, type, length, line);
		}
	}
//done	
	private void params(Node currNode, List<Variable> paramList){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		if(child instanceof SyntaxLeafNode)
			return;
		else 
			param_list(child, paramList);	
	}
//done
	private void param_list(Node currNode, List<Variable> paramList){
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		param(child0, paramList);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
		param_list_elim(child1, paramList);
	}
//done
	private void param(Node currNode, List<Variable> paramList){
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
//done
	private void param_temp(Node currNode, String identify, Type type, int line, List<Variable> paramList){
		Variable var = null;
		if(((SyntaxTreeNode)currNode).getChildNumber() == 1)
			var = new Variable(identify, type, false);
		else 
			var = new Variable(identify, type, true);;
		if(!paramList.contains(var))
			paramList.add(var);
		else 
			err.addException(new SyntacticErrorException(identify, line, 2));
	}
//done
	private void param_list_elim(Node currNode, List<Variable> paramList){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 0)
			return;
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		param(child0, paramList);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
		param_list_elim(child1, paramList);
	}
//done
	private CompoundStatement compound_stmt(Node currNode){
		CompoundStatement compound = new CompoundStatement();
		Node child0 = ((SyntaxTreeNode)currNode).getChild(2);
		local_declarations(child0, compound);
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		statement_list(child1, compound);
		return compound;
	}
//done
	private void local_declarations(Node currNode, CompoundStatement compound){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		local_declarations_elim(child, compound);
	}
//done
	private void statement_list(Node currNode, CompoundStatement compound){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 0)
			return;
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		compound.addStatement(statement(child0));
		Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
		statement_list(child1, compound);
		
	}
//done
	private void local_declarations_elim(Node currNode, CompoundStatement compound){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 0)
			return;
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		compound.addVariable(var_declaration_sub(child0));
		Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
		local_declarations_elim(child1, compound);
	}
//done
	private VariableDeclaration var_declaration_sub(Node currNode) {
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
		return var_daclaration(child2, identify, type, line);
	}
//done
	private Statement statement(Node currNode){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		String statement = child.getSymbol();
		switch(statement){
			case "expression-stmt":
				return expression_stmt(child);
			case "compound-stmt":
				return compound_stmt(child);
			case "selection-stmt":
				return selection_stmt(child);
			case "iteration-stmt":
				return iteration_stmt(child);
			case "return-stmt":
				return return_stmt(child);	
		}
		return null;
	}
//done
	private ExpressionStatement expression_stmt(Node currNode){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 2){
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			return new ExpressionStatement(expression(child));
		}
		return new ExpressionStatement();
	}
//done
	private SelectionStatement selection_stmt(Node currNode){
		SelectionStatement select = new SelectionStatement();
		Node child1 = ((SyntaxTreeNode)currNode).getChild(3);
		select.setCondition(expression(child1));
//		Node child2 = ((SyntaxTreeNode)currNode).getChild(4);
//		Token tok = ((SyntaxLeafNode)child2).getToken();
//		int line = tok.getLineNum();
//		if(type == Type.VOID)
//			err.addException(new SyntacticErrorException("(", line, 12));
		Node child3 = ((SyntaxTreeNode)currNode).getChild(1);
		select.setIfStmt(statement(child3));
		Node child4 = ((SyntaxTreeNode)currNode).getChild(0);
		select.setElseStmt(selection_stmt_temp(child4));
		return select;
	}
//done
	private Statement selection_stmt_temp(Node currNode){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
			return statement(child2);
		}
		return null;
	}
//done
	private IterationStatement iteration_stmt(Node currNode){
		IterationStatement iteration = new IterationStatement();
		Node child1 = ((SyntaxTreeNode)currNode).getChild(2);
		iteration.setIteration(expression(child1));
//		Node child3 = ((SyntaxTreeNode)currNode).getChild(3);
//		Token tok = ((SyntaxLeafNode)child3).getToken();
//		int line = tok.getLineNum();
//		if(type == Type.NULL)
//			err.addException(new SyntacticErrorException("(", line, 13));
		Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
		iteration.setState(statement(child2));
		return iteration;
	}
//done
	private ReturnStatement return_stmt(Node currNode){
		ReturnStatement ret = new ReturnStatement();
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		if(child0 instanceof SyntaxTreeNode)
			ret.setRet(expression(child0));
		return ret;
			
	}
	private Expression expression(Node currNode){
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		String identify = ((SyntaxLeafNode)child0).getSymbol();
		switch(identify){
			case "ID":
			{
				Token tok = ((SyntaxLeafNode)child0).getToken();
				String variable = ((Word)tok).getId();
				int line = tok.getLineNum();
				Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
				return expression_sub(child1, variable, line);
//				if(attr != Attribute.FUNC){
//					if(!symTable.checkVariableExist(variable))
//						err.addException(new SyntacticErrorException(identify, line, 3));
//					else
//						return symTable.getAttributeofVariable(variable).getType();
//				}
//				else {
//					if(funcTable.checkKeyState(variable))
//						err.addException(new SyntacticErrorException(identify, line, 8));
//					else
//						return funcTable.getReturnType(variable);
//				}
//				return Type.NULL;
			}
			case ")":{
				Node child = ((SyntaxTreeNode)currNode).getChild(1);
				int line = ((SyntaxLeafNode)child).getToken().getLineNum();
				Node child1 = ((SyntaxTreeNode)currNode).getChild(2);
				Expression exp = expression(child1);
				Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
				//if has bug, maybe here
				exp = expression_none(child2, exp, line);
//				if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
//					err.addException(new SyntacticErrorException(type1, type2, line));
				return exp;
			}
			case "NUM":{
				Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
				Token tok = ((SyntaxLeafNode)child0).getToken();
				Expression exp = null;
				int line = tok.getLineNum();
				exp = expression_none(child1, exp, line);
				return exp;
//				if(tok instanceof Number){
//					if(type != Type.INT && type != Type.NULL)
//						err.addException(new SyntacticErrorException(type, Type.INT, line));
//					return Type.INT;
//				}
//				else {
//					if(type != Type.FLOAT && type != Type.NULL)
//						err.addException(new SyntacticErrorException(type, Type.FLOAT, line));
//					return Type.FLOAT;	
//				}
			}
		}
		return null;
	}	
	private Expression expression_sub(Node currNode, String identify, int line){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		if(child1 instanceof SyntaxTreeNode){
//			Attribute attr = var(child1, identify, line);
			Expression var = var(child1, identify, line);
			Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
			return expression_sub_sub(child2, var, line);
//			Type type2 = null;
//			if(symTable.getAttributeofVariable(identify) == null)
//				err.addException(new SyntacticErrorException(identify, line, 3));
//			else{
//				type2 = symTable.getAttributeofVariable(identify).getType();
//				if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
//					err.addException(new SyntacticErrorException(type1, type2, line));
//			}
//			return attr;
			
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
//done
	private Expression var(Node currNode, String identify, int line){
//		Attribute attr = null;
		if(((SyntaxTreeNode)currNode).getChildNumber() == 0){
//			if(symTable.getAttributeofVariable(identify) == null)
//				err.addException(new SyntacticErrorException(identify, line, 3));
//			else if (symTable.getAttributeofVariable(identify).getAttribute() == Attribute.ARRAY)
//				err.addException(new SyntacticErrorException(identify, line, 9));
			return new VariableExpression(identify, line);
		}
		else {
//			if(symTable.getAttributeofVariable(identify) == null)
//				err.addException(new SyntacticErrorException(identify, line, 3));
//			else if (symTable.getAttributeofVariable(identify).getAttribute() == Attribute.VAR)
//				err.addException(new SyntacticErrorException(identify, line, 10));
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			return new VariableExpression(identify, line, expression(child));
		}
	}
// has problem
	private Expression expression_none(Node currNode, Expression lch, int line){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(2);
		term_temp(child1, lch, line);
		Node child2 = ((SyntaxTreeNode)currNode).getChild(1);
//		Type type2 = additive_expression_temp(child2, line);
//		if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
//			err.addException(new SyntacticErrorException(type1, type2, line));
		Node child3 = ((SyntaxTreeNode)currNode).getChild(0);
		Type type3 = simple_expression(child3, line);
//		if(type2 != type3 && type2 != Type.NULL && type3 != Type.NULL)
//			err.addException(new SyntacticErrorException(type2, type3, line));
		return type1;
	}
//not done
	private Expression expression_sub_sub(Node currNode, Expression exp, int line){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		if(((SyntaxTreeNode)currNode).getChildNumber() == 1){
			Expression expression = null;
			expression = expression_none(child, exp, line);
			return expression;
		}
		else{
			Expression expR = expression(child);
			AssignExpression assign = new AssignExpression(exp, expR);
			return assign;
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
//lack of weigh
	private Expression term_temp(Node currNode, Expression lch, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			BinaryExpression binary = null;
			Node child3 = ((SyntaxTreeNode)currNode).getChild(2);
			Operator op = mulop(child3);
			Node child2 = ((SyntaxTreeNode)currNode).getChild(1);
			Expression rch = factor(child2);
			Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
			Expression rsub = term_temp(child1, rch, line);
			if(rsub != null){
				binary = new BinaryExpression(op, lch, rsub);
			} else {
				binary = new BinaryExpression(op, lch, rch);
			}
//			if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
//				err.addException(new SyntacticErrorException(type1, type2, line));
//			return type1;
			return binary;
		}
		else 
			return null;
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
		Type type2 = additive_expression_temp(child2, line);
		if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
			err.addException(new SyntacticErrorException(type1, type2, line));
		return type1;
	}
//done
	private Expression factor(Node currNode){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		if(child instanceof SyntaxTreeNode){
			Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
			Token tok = ((SyntaxLeafNode)child1).getToken();
			String identify = ((Word)tok).getId();
			int line = tok.getLineNum();
			return factor_temp(child, identify, line);
//			Attribute attr = factor_temp(child, identify, line);
//			if(attr != Attribute.FUNC){
//				if(!symTable.checkVariableExist(identify))
//					err.addException(new SyntacticErrorException(identify, line, 3));
//				else
//					return symTable.getAttributeofVariable(identify).getType();
//			}
//			else {
//				if(funcTable.checkKeyState(identify))
//					err.addException(new SyntacticErrorException(identify, line, 8));
//				else
//					return funcTable.getReturnType(identify);
//			}
//			return Type.NULL;
				
		}
		else {
			if(child.getSymbol().equals("NUM")){
				Token tok = ((SyntaxLeafNode)child).getToken();
				int line = tok.getLineNum();
				if(tok instanceof Number)
					return new LiteralExpression(((Number)tok).getNum(), line);
				else
					return new LiteralExpression(((FloatNumber)tok).getFloat(), line);
			}
			else {
				Node child2 = ((SyntaxTreeNode)currNode).getChild(1);
				return expression(child2);
			}
		}
	}
	private Expression factor_temp(Node currNode, String identify, int line){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		if(child instanceof SyntaxTreeNode)
			return var(child, identify, line);
		else {	
			Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
			ArrayList<Expression> args = new ArrayList<Expression>();
			args(child1, args);
			return new CallExpression(identify, args);
//			if(funcTable.getSymbolAttribute(identify) != null && paramList.size() == funcTable.getSymbolAttribute(identify).getListSize()){
//				if(!funcTable.parametersMatched(paramList, identify))
//					err.addException(new SyntacticErrorException(identify, line, 11));
//			}
//			else{
//				err.addException(new SyntacticErrorException(identify, line, 11));
//			}
		}
	}
//done
	private void args(Node currNode, ArrayList<Expression> list){	
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			Node child = ((SyntaxTreeNode)currNode).getChild(0);
			args_list(child, list);
		} 
	}
//done
	private void args_list(Node currNode, ArrayList<Expression> list){
		Node child = ((SyntaxTreeNode)currNode).getChild(1);
		list.add(expression(child));
		Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
		args_list_elim(child1, list);
	}
//done
	private void args_list_elim(Node currNode, ArrayList<Expression> list){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			list.add(expression(child));
			Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
			args_list_elim(child1, list);
		}
	}
//done
	private Operator mulop(Node currNode){
		if(((SyntaxTreeNode)currNode).getChild(0).equals("*"))
			return Operator.MUL;
		else
			return Operator.DIV;
	}
//done
	private Operator addop(Node currNode){
		if(((SyntaxTreeNode)currNode).getChild(0).equals("+"))
			return Operator.ADD;
		else 
			return Operator.SUB;
	}
}
