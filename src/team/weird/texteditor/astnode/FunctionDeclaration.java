package team.weird.texteditor.astnode;

import java.util.ArrayList;
import java.util.List;

import team.weird.texteditor.semantic.SymbolAttr.Type;

public class FunctionDeclaration extends Declaration{
	private List<Variable> parameters = new ArrayList<Variable>();
	private CompoundStatement statement;
	public FunctionDeclaration(String id, Type type){
		super(id, type);
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
	
}
