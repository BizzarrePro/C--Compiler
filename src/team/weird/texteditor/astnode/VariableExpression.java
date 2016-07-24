package team.weird.texteditor.astnode;


public class VariableExpression extends Expression {
	Expression array = null;
	public VariableExpression(String id, int line){
		super(id, line);
	}
	public VariableExpression(String id, int line, Expression array){
		this(id, line);
		this.array = array;
	}
	public Expression getArray() {
		return array;
	}
	public void setArray(Expression array) {
		this.array = array;
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "VariableExpression: " + getId());
		if(array != null) {
			System.out.println("[ \n");
			array.print(tab + "\t");
			System.out.println("\n" + tab + "]\n");
		}
	}
}
