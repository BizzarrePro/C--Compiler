package team.weird.compiler.cminus.astnode;

import team.weird.compiler.cminus.codegen.BasicBlock;
import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Operand;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.codegen.Operation;
import team.weird.compiler.cminus.semantic.ErrorList;
import team.weird.compiler.cminus.semantic.SemanticException;
import team.weird.compiler.cminus.semantic.Type;

public class IterationStatement extends Statement{
	private Expression iteration;
	private Statement state;
	public IterationStatement(){
		
	}
	public IterationStatement(Expression iteration, Statement state){
		this.iteration = iteration;
		this.state = state;
	}
	public Expression getIteration() {
		return iteration;
	}
	public void setIteration(Expression iteration) {
		this.iteration = iteration;
	}
	public Statement getState() {
		return state;
	}
	public void setState(Statement state) {
		this.state = state;
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "IterationStmt: ");
		iteration.print(tab + "\t");
		state.print(tab + "\t");
		System.out.println();
	}
	@Override
	public void generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		ErrorList err = ErrorList.getInstance();
		BasicBlock loop = new BasicBlock(fun);
		BasicBlock condition = new BasicBlock(fun);
		BasicBlock post = new BasicBlock(fun);
		fun.appendBlock(condition);
		fun.setCurrBlock(condition);
		Type iterType = iteration.generateIntermediateCode(fun);
		if(iteration.getClass() != BinaryExpression.class)
			err.addException(new SemanticException(iteration.getId(), iteration.getLine(), 15));
		Operation op = new Operation(OperandType.BEQ, fun.getCurrBlock());
		Operand oper = new Operand(OperandType.REG, iteration.getRegNum());
		op.setSrcOperand(0, oper);
		oper = new Operand(OperandType.typeConvert(iterType), 0);
		op.setSrcOperand(1, oper);
		oper = new Operand(OperandType.BLOCK, post.getBlockID());
		op.setSrcOperand(2, oper);
		//?
		condition.appendOperation(op);
		fun.appendToCurrBlock(loop);
		fun.appendToCurrBlock(loop);
		state.generateIntermediateCode(fun);
		op = new Operation(OperandType.JMP, fun.getCurrBlock());
		oper = new Operand(OperandType.BLOCK, condition.getBlockID());
		op.setSrcOperand(0, oper);
		loop.appendOperation(op);
		//?
		fun.appendToCurrBlock(post);
		fun.setCurrBlock(post);
	}
	
}
