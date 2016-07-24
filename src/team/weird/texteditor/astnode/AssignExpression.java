package team.weird.texteditor.astnode;


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
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab+"AssignExpression: ");
		lex.print(tab+"\t");
		System.out.println("\n" + tab +  "\t=");
		rex.print(tab + "\t");
		System.out.println();
	}
	
}
