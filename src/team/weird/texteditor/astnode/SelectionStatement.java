package team.weird.texteditor.astnode;

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
	
}
