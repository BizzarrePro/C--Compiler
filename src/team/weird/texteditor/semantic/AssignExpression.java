package team.weird.texteditor.semantic;

import team.weird.texteditor.astnode.Expression;

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
	
}
