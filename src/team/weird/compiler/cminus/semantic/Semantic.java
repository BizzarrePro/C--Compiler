package team.weird.compiler.cminus.semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import team.weird.compiler.cminus.astnode.ArrayDeclaration;
import team.weird.compiler.cminus.astnode.AssignExpression;
import team.weird.compiler.cminus.astnode.BinaryExpression;
import team.weird.compiler.cminus.astnode.CallExpression;
import team.weird.compiler.cminus.astnode.CompoundStatement;
import team.weird.compiler.cminus.astnode.Declaration;
import team.weird.compiler.cminus.astnode.Expression;
import team.weird.compiler.cminus.astnode.ExpressionStatement;
import team.weird.compiler.cminus.astnode.FunctionDeclaration;
import team.weird.compiler.cminus.astnode.IterationStatement;
import team.weird.compiler.cminus.astnode.LiteralExpression;
import team.weird.compiler.cminus.astnode.Operator;
import team.weird.compiler.cminus.astnode.Program;
import team.weird.compiler.cminus.astnode.ReturnStatement;
import team.weird.compiler.cminus.astnode.SelectionStatement;
import team.weird.compiler.cminus.astnode.Statement;
import team.weird.compiler.cminus.astnode.Variable;
import team.weird.compiler.cminus.astnode.VariableDeclaration;
import team.weird.compiler.cminus.astnode.VariableExpression;
import team.weird.compiler.cminus.lexer.FloatNumber;
import team.weird.compiler.cminus.lexer.Number;
import team.weird.compiler.cminus.lexer.Token;
import team.weird.compiler.cminus.lexer.Word;
import team.weird.compiler.cminus.semantic.Type;
import team.weird.compiler.cminus.semantic.SymbolAttr.Attribute;

public class Semantic {
	private Node root = null;
	public Program program = new Program();
	private ErrorList err = ErrorList.getInstance();
	public static final HashMap<String, VariableDeclaration> globalSymbolTable = new HashMap<>();
	public static final HashMap<String, FunctionDeclaration> globalFuntionTable = new HashMap<>();
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
//		err.throwsAllExceptions();
		program.printASTree();
	}
	private void program(Node currNode){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		declaration_list(child);
	}
	private void printASTNode(){
		for(Declaration d : program.getDeclarations()){
			System.out.println(d);
			if(d instanceof FunctionDeclaration)
				for(Variable v : ((FunctionDeclaration)d).getParameters())
					System.out.println(v);
		}
	}
//have done
	private void declaration_list(Node currNode){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		program.addDeclaration(declaration(child1));
		Node child0 = ((SyntaxTreeNode)currNode).getChild(0);
		declaration_list_elim(child0);
	}
//have done
	private Declaration declaration(Node currNode){
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

	private void declaration_list_elim(Node currNode){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 0)
			return;
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		Node child0 = ((SyntaxTreeNode)currNode).getChild(0);
		program.addDeclaration(declaration(child1));
		declaration_list_elim(child0);	
	}
//have done
	private Type type_declaration(Node currNode){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		switch(child.getSymbol()){
			case "int":
				return Type.INT;
			case "double":
				return Type.FLOAT;
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
//			if(symTable.checkKeyState(identify))
//				symTable.putInSymbolTable(identify, new SymbolAttr(identify, type, Attribute.VAR));
//			else
//				err.addException(new SyntacticErrorException(identify, line, 2));
			return new VariableDeclaration(identify, type, line);
		}
		else {
			Node child = ((SyntaxTreeNode)currNode).getChild(2);
			int length = 0;
			Token tok = ((SyntaxLeafNode)child).getToken();
			length = ((Number)tok).getNum();
//			if(tok instanceof Number){
//				if(symTable.checkKeyState(identify))
//					symTable.putInSymbolTable(identify, new SymbolAttr(identify, type, Attribute.ARRAY, length));
//				else
//					err.addException(new SyntacticErrorException(identify, line, 2));
//			}
//			else 
//				err.addException(new SyntacticErrorException(identify, line, 5));
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
			err.addException(new SemanticException(identify, line, 2));
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
//have done
	private ExpressionStatement expression_stmt(Node currNode){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 2){
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			return new ExpressionStatement(expression(child));
		}
		return new ExpressionStatement();
	}
//have done
	private SelectionStatement selection_stmt(Node currNode){
		SelectionStatement select = new SelectionStatement();
		Node child1 = ((SyntaxTreeNode)currNode).getChild(3);
		select.setCondition(expression(child1));
		Node child3 = ((SyntaxTreeNode)currNode).getChild(1);
		select.setIfStmt(statement(child3));
		Node child4 = ((SyntaxTreeNode)currNode).getChild(0);
		select.setElseStmt(selection_stmt_temp(child4));
		return select;
	}
//have done
	private Statement selection_stmt_temp(Node currNode){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
			return statement(child2);
		}
		return null;
	}
//have done
	private IterationStatement iteration_stmt(Node currNode){
		IterationStatement iteration = new IterationStatement();
		Node child1 = ((SyntaxTreeNode)currNode).getChild(2);
		iteration.setIteration(expression(child1));
		Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
		iteration.setState(statement(child2));
		return iteration;
	}
//have done
	private ReturnStatement return_stmt(Node currNode){
		ReturnStatement ret = new ReturnStatement();
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		if(child0 instanceof SyntaxTreeNode)
			ret.setRet(expression(child0));
		return ret;
			
	}
//have done
	private Expression expression(Node currNode){
		Node child0 = ((SyntaxTreeNode)currNode).getChild(1);
		String identify = ((SyntaxLeafNode)child0).getSymbol();
		switch(identify){
			case "ID":	{
				Token tok = ((SyntaxLeafNode)child0).getToken();
				String variable = ((Word)tok).getId();
				int line = tok.getLineNum();
				Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
				return expression_sub(child1, variable, line);
			}
			case ")":	{
				Node child = ((SyntaxTreeNode)currNode).getChild(1);
				int line = ((SyntaxLeafNode)child).getToken().getLineNum();
				Node child1 = ((SyntaxTreeNode)currNode).getChild(2);
				Expression exp = expression(child1);
				Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
				return expression_none(child2, exp, line);
				
			}
			case "NUM":	{
				Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
				Token tok = ((SyntaxLeafNode)child0).getToken();
				Expression exp = null;
				int line = tok.getLineNum();
				exp = expression_none(child1, exp, line);
				if(exp == null){
					if(tok instanceof Number)
						return new LiteralExpression(((Number)tok).getNum(), line);
					else
						return new LiteralExpression(((FloatNumber)tok).getFloat(), line);
				}
				return exp;
			}
		}
		return null;
	}	
//have done
	//can be reused
	//	if(funcTable.getSymbolAttribute(identify) != null && paramList.size() == funcTable.getSymbolAttribute(identify).getListSize()){
	//	if(!funcTable.parametersMatched(paramList, identify))
	//		err.addException(new SyntacticErrorException(identify, line, 11));
	//}
	//	else{
	//	err.addException(new SyntacticErrorException(identify, line, 11));
	//}
	private Expression expression_sub(Node currNode, String identify, int line){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		if(child1 instanceof SyntaxTreeNode){
			Expression var = var(child1, identify, line);
			Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
			return expression_sub_sub(child2, var, line);
		} 
		else {
			ArrayList<Expression> args = new ArrayList<Expression>();
			Node child2 = ((SyntaxTreeNode)currNode).getChild(2);
			args(child2, args);
			return new CallExpression(identify, args);
		}
	}
//have done
	// can be reused
	//			if(symTable.getAttributeofVariable(identify) == null)
	//				err.addException(new SyntacticErrorException(identify, line, 3));
	//			else if (symTable.getAttributeofVariable(identify).getAttribute() == Attribute.VAR)
	//				err.addException(new SyntacticErrorException(identify, line, 10));
	private Expression var(Node currNode, String identify, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() == 0){
			return new VariableExpression(identify, line);
		}
		else {
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			return new VariableExpression(identify, line, expression(child));
		}
	}
//have done
	private Expression expression_none(Node currNode, Expression lch, int line){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(2);
		Expression lRetCh = term_temp(child1, lch, line);
		Node child2 = ((SyntaxTreeNode)currNode).getChild(1);
		Expression bexp = additive_expression_temp(child2, lRetCh, line);
		Node child3 = ((SyntaxTreeNode)currNode).getChild(0);
		Expression ret = simple_expression(child3, bexp, line);
		return ret;
	}
//have done
	private Expression expression_sub_sub(Node currNode, Expression lch, int line){
		Node child = ((SyntaxTreeNode)currNode).getChild(0);
		if(((SyntaxTreeNode)currNode).getChildNumber() == 1){
			Expression exp = expression_none(child, lch, line);
			return exp;
		}
		else{
			Expression expR = expression(child);
			AssignExpression assign = new AssignExpression(lch, expR);
			return assign;
		}	
	}
//have done
	private Expression term(Node currNode, Expression lch, int line){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		Expression exp = ( lch == null ) ? factor(child1) : lch;
		Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
		return term_temp(child2, exp, line);
	}
//have done
	private Expression term_temp(Node currNode, Expression lch, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			BinaryExpression binary = null;
			Node child3 = ((SyntaxTreeNode)currNode).getChild(2);
			Operator op = mulop(child3);
			Node child2 = ((SyntaxTreeNode)currNode).getChild(1);
			Expression rch = factor(child2);
			Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
			Expression rsub = term_temp(child1, rch, line);
			binary = new BinaryExpression(op, lch, rsub);
			return binary;
		}
		return lch;
	}
//have done
	private Expression additive_expression_temp(Node currNode, Expression lch, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			Node child1 = ((SyntaxTreeNode)currNode).getChild(2);
			Operator op = addop(child1);
			Node child2 = ((SyntaxTreeNode)currNode).getChild(1);
			Expression rch = term(child2, null, line);
			BinaryExpression binary = null;
			Node child3 = ((SyntaxTreeNode)currNode).getChild(0);
			Expression ret = additive_expression_temp(child3, rch, line);
			binary = new BinaryExpression(op, lch, ret);
			return binary;
		}
		return lch;
	}
//done
	private Expression simple_expression(Node currNode, Expression lch, int line){
		if(((SyntaxTreeNode)currNode).getChildNumber() != 0){
			Node child = ((SyntaxTreeNode)currNode).getChild(1);
			Operator op = relop(child);
			Node child1 = ((SyntaxTreeNode)currNode).getChild(0);
			Expression rch = additive_expression(child1, null, line);
			BinaryExpression binary = new BinaryExpression(op, lch, rch);
			return binary;
		}
		return lch;
	}
	private Expression additive_expression(Node currNode, Expression lch, int line){
		Node child1 = ((SyntaxTreeNode)currNode).getChild(1);
		Expression exp = term(child1, lch, line);
		Node child2 = ((SyntaxTreeNode)currNode).getChild(0);
		return additive_expression_temp(child2, exp, line);
//		Type type2 = additive_expression_temp(child2, line);
//		if(type1 != type2 && type1 != Type.NULL && type2 != Type.NULL)
//			err.addException(new SyntacticErrorException(type1, type2, line));
//		return type1;
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
//done
	private Operator relop(Node currNode){
		String operation = ((SyntaxTreeNode)currNode).getChild(0).getSymbol();
		switch(operation){
			case "<":	return Operator.LTHAN;
			case ">":	return Operator.GTHAN;
			case "<=":	return Operator.LTEQ;
			case ">=":	return Operator.GTEQ;
			case "==":	return Operator.EQUAL;
			default:	return Operator.NEQ;
		}
	}
}
