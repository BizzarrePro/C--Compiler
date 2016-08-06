package team.weird.compiler.cminus.astnode;

import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Operand;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.codegen.Operation;

public class ReturnStatement extends Statement{
	private Expression ret = null;
	public ReturnStatement(Expression ret){
		this.ret = ret;
	}
	public ReturnStatement(){
		
	}
	public Expression getRet() {
		return ret;
	}
	public void setRet(Expression ret) {
		this.ret = ret;
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "ReturnStmt: ");
		ret.print(tab + "\t");
	}
	@Override
	public void generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		ret.generateIntermediateCode(fun);
		Operation assign = new Operation(OperandType.ASSIGN, fun.getCurrBlock());
		Operand oper = new Operand(OperandType.REG, ret.getRegNum());
		assign.setSrcOperand(0, oper);
		oper = new Operand(OperandType.RET, "ret");
		assign.setDestOperand(0, oper);
		fun.getCurrBlock().appendOperation(assign);
	}
}
