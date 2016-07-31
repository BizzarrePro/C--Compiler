package team.weird.texteditor.astnode;

import team.weird.texteditor.codegen.Function;
import team.weird.texteditor.codegen.InstructionType;
import team.weird.texteditor.codegen.Operand;
import team.weird.texteditor.codegen.OperandType;
import team.weird.texteditor.codegen.Operation;
import team.weird.texteditor.lexer.Number;
import team.weird.texteditor.semantic.ErrorList;
import team.weird.texteditor.semantic.SemanticException;
import team.weird.texteditor.semantic.Type;


public class AssignExpression extends Expression{
	private Expression lex;
	private Expression rex;
	public AssignExpression(Expression lex, Expression rex){
		this.lex = lex;
		this.rex = rex;
	}
	public AssignExpression(){
		
	}
	public Expression getLex() {
		return lex;
	}
	public void setLex(Expression lex) {
		this.lex = lex;
	}
	public Expression getRex() {
		return rex;
	}
	public void setRex(Expression rex) {
		this.rex = rex;
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab+"AssignExpression: ");
		lex.print(tab+"\t");
		System.out.println("\n" + tab +  "\t=");
		rex.print(tab + "\t");
		System.out.println();
	}
	@Override
	public Type generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		ErrorList err = ErrorList.getInstance();
		Type leftType = lex.generateIntermediateCode(fun);
		Type rightType = rex.generateIntermediateCode(fun);
		if(leftType != rightType)
			err.addException(new SemanticException(leftType, rightType, lex.getLine()));
		Operation op = new Operation(InstructionType.ASSIGN, fun.getCurrBlock());
		fun.getCurrBlock().appendOperation(op);
		Operand dest = new Operand(OperandType.REG, lex.getRegNum());
		op.setDestinationReg(dest);
		Operand src = null;
		if(rex.getClass() != LiteralExpression.class)
			src = new Operand(OperandType.REG, rex.getRegNum());
		else{
			Object obj = ((LiteralExpression)rex).getNumber();
			if(obj instanceof Integer)
				src = new Operand(OperandType.INT, (int)obj);
			else
				src = new Operand(OperandType.INT, (double)obj);

		}
		op.setSourceReg(src);	
		return null;
	}
	
}
