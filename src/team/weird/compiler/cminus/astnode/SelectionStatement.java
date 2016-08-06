package team.weird.compiler.cminus.astnode;

import team.weird.compiler.cminus.codegen.BasicBlock;
import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Operand;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.codegen.Operation;

public class SelectionStatement extends Statement{
	private Expression condition;
	private Statement ifStmt;
	private Statement elseStmt;
	public SelectionStatement(){
		
	}
	public Expression getCondition() {
		return condition;
	}
	public void setCondition(Expression condition) {
		this.condition = condition;
	}
	public Statement getIfStmt() {
		return ifStmt;
	}
	public void setIfStmt(Statement ifStmt) {
		this.ifStmt = ifStmt;
	}
	public Statement getElseStmt() {
		return elseStmt;
	}
	public void setElseStmt(Statement elseStmt) {
		this.elseStmt = elseStmt;
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "SelectionStmt: if (");
		condition.print(tab + "\t");
		System.out.println(tab + " )");
		ifStmt.print(tab + "\t");
		if(elseStmt != null) {
			System.out.println("\n" + tab + "else");
			elseStmt.print(tab + "\t");
		}
	}
	@Override
	public void generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		BasicBlock ifBlock = new BasicBlock(fun);
		BasicBlock elseBlock = new BasicBlock(fun);
		BasicBlock condition = new BasicBlock(fun);
		BasicBlock post = new BasicBlock(fun);
		fun.appendToCurrBlock(condition);
		fun.setCurrBlock(condition);
		this.condition.generateIntermediateCode(fun);
		Operation op = new Operation(OperandType.BNE, fun.getCurrBlock());
		Operand oper = new Operand(OperandType.REG, this.condition.getRegNum());
		op.setSrcOperand(0, oper);
		oper = new Operand(OperandType.INT, 0);
		op.setSrcOperand(1, oper);
		oper = new Operand(OperandType.BLOCK, elseBlock.getBlockID());
		op.setSrcOperand(2, oper);
		condition.appendOperation(op);
		fun.appendToCurrBlock(ifBlock);
		fun.setCurrBlock(ifBlock);
		ifStmt.generateIntermediateCode(fun);
		//!!
		BasicBlock curr = fun.getCurrBlock();
		fun.appendToCurrBlock(elseBlock);
		fun.setCurrBlock(elseBlock);
		elseStmt.generateIntermediateCode(fun);
		op = new Operation(OperandType.JMP, fun.getCurrBlock());
		oper = new Operand(OperandType.BLOCK, post.getBlockID());
		op.setSrcOperand(0, oper);
		elseBlock.appendOperation(op);
		fun.setCurrBlock(curr);
	}
	
}
