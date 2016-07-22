package team.weird.texteditor.astnode;

public class IterationStatement extends Statement{
	private Expression iteration;
	private Statement state;
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
	
}
