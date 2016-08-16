package team.weird.compiler.cminus.astnode;

import java.io.FileWriter;

import team.weird.compiler.cminus.codegen.Function;

public abstract class Statement {
	public Statement(){
		
	}
	public abstract void print(String tab, FileWriter fw);
	public abstract void generateIntermediateCode(Function fun);
}
