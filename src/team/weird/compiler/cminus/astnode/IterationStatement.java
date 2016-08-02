package team.weird.compiler.cminus.astnode;

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
	
}
