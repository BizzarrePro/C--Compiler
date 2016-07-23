package team.weird.texteditor.semantic;

import team.weird.texteditor.astnode.Expression;

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
}
