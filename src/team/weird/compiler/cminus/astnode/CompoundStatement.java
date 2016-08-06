package team.weird.compiler.cminus.astnode;

import java.util.ArrayList;

import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.SymbolAttribute;

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
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "CompoundStatement: {");
		for(VariableDeclaration vd : varDeclarations) {
			vd.print(tab + "\t");
		}
		
		for(Statement s : statements) {
			s.print(tab + "\t");
		}
		System.out.println(tab + "}");
	}
	@Override
	public void generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		for(VariableDeclaration v : varDeclarations)
			fun.getSymbolTable().put(v.getId(), 
					new SymbolAttribute(v.getId(), v.getType(), 
							v.getClass() == ArrayDeclaration.class ? true : false, fun.getNewRegisterNum()));
		for(Statement s : statements)
			s.generateIntermediateCode(fun);
	}
}
