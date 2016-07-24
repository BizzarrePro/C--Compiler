package team.weird.texteditor.astnode;

import java.util.ArrayList;
import java.util.List;

import team.weird.texteditor.semantic.SymbolAttr.Type;

public class FunctionDeclaration extends Declaration implements PrintASTree{
	private List<Variable> parameters = new ArrayList<Variable>();
	private CompoundStatement statement;
	public FunctionDeclaration(String id, Type type, int line){
		super(id, type, line);
	}
	public List<Variable> getParameters() {
		return parameters;
	}
	public void setParameters(List<Variable> parameters) {
		this.parameters = parameters;
	}
	public CompoundStatement getStatement() {
		return statement;
	}
	public void setStatement(CompoundStatement statement) {
		this.statement = statement;
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println("FunctionDeclaration: " + getType() + " " + getId() + " ( ");
		for(Variable v : parameters) {
			v.print(tab);
		}
		System.out.println(")");
		statement.print(tab);
		System.out.println();
	}

}
