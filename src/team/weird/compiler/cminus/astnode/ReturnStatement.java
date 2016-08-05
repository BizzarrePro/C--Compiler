package team.weird.compiler.cminus.astnode;

import team.weird.compiler.cminus.codegen.Function;

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
		
	}
}
