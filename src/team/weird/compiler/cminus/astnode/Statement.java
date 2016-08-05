package team.weird.compiler.cminus.astnode;

import team.weird.compiler.cminus.codegen.Function;

public abstract class Statement {
	public Statement(){
		
	}
	public abstract void print(String tab);
	public abstract void generateIntermediateCode(Function fun);
}
