package team.weird.texteditor.astnode;

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
	
}
