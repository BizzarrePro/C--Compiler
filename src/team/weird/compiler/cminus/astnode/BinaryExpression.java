package team.weird.compiler.cminus.astnode;

import java.io.FileWriter;
import java.io.IOException;

import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Operand;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.codegen.Operation;
import team.weird.compiler.cminus.semantic.ErrorList;
import team.weird.compiler.cminus.semantic.SemanticException;
import team.weird.compiler.cminus.semantic.Type;


public class BinaryExpression extends Expression {
	private Operator operator = null;
	private Expression lChild = null;
	private Expression rChild = null;
	public BinaryExpression(){
		
	}
	public BinaryExpression(Operator operator, Expression lChild, Expression rChild){
		this.operator = operator;
		this.lChild = lChild;
		this.rChild = rChild;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public Expression getlChild() {
		return lChild;
	}
	public void setlChild(Expression lChild) {
		this.lChild = lChild;
	}
	public Expression getrChild() {
		return rChild;
	}
	public void setrChild(Expression rChild) {
		this.rChild = rChild;
	}
	@Override
	public void print(String tab, FileWriter fw) {
		// TODO Auto-generated method stub
		System.out.println(tab + "BinaryExpression: ");
		try {
			fw.write(tab + "BinaryExpression: \r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		lChild.print(tab + "\t", fw);
		System.out.println("\n" + tab + "\t" + operator.toString());
		try {
			fw.write("\n" + tab + "\t" + operator.toString()+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		rChild.print(tab + "\t", fw);
		System.out.println();
		try {
			fw.write("\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Type generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		ErrorList err = ErrorList.getInstance();
		Type ltype = lChild.generateIntermediateCode(fun);
		Type rtype = rChild.generateIntermediateCode(fun);
		if(ltype != rtype)
			err.addException(new SemanticException(ltype, rtype, lChild.getLine()));
		Operation op = new Operation(OperandType.typeConvert(this.operator), fun.getCurrBlock());
		fun.getCurrBlock().appendOperation(op);
		setRegNum(fun.getNewRegisterNum()); 
		Operand oper = new Operand(OperandType.REG, getRegNum());
		op.setDestOperand(0, oper);
		if(lChild instanceof LiteralExpression){
			LiteralExpression lexp = (LiteralExpression)lChild;
			oper = new Operand(OperandType.literalConvert(lexp.getNumber()), lexp.getNumber());
		}
		else
			oper = new Operand(OperandType.REG, lChild.getRegNum());
		op.setSrcOperand(0, oper);
		if(rChild instanceof LiteralExpression){
			LiteralExpression rexp = (LiteralExpression)rChild;
			oper = new Operand(OperandType.literalConvert(rexp.getNumber()), rexp.getNumber());
		}
		else
			oper = new Operand(OperandType.REG, rChild.getRegNum());
		op.setSrcOperand(1, oper);
		return ltype;
	}
	
}
