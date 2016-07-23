package team.weird.texteditor.astnode;


public class BinaryExpression extends Expression{
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
	
}
