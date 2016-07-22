package team.weird.texteditor.astnode;

public class ExpressionStatement extends Statement{
	private Expression exp = null;
	public ExpressionStatement(Expression exp){
		this.exp = exp;
	}
	public ExpressionStatement(){
		
	}
	public Expression getExp() {
		return exp;
	}
	public void setExp(Expression exp) {
		this.exp = exp;
	}
	
}
