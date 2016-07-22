package team.weird.texteditor.astnode;

import java.util.ArrayList;

public class CompoundStatement extends Statement{
	private ArrayList<VariableDeclaration> varDeclarations = new ArrayList<VariableDeclaration>();
	private ArrayList<Statement> statements = new ArrayList<Statement>();
	public CompoundStatement (){
		
	}
	public void addVariable(VariableDeclaration var){
		if(var != null)
			varDeclarations.add(var);
	}
	public void addStatement(Statement stmt){
		if(stmt != null)
			statements.add(stmt);
	}
}
