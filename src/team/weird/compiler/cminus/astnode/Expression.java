package team.weird.compiler.cminus.astnode;

import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.semantic.Type;

public abstract class Expression {
	private String id = null;
	private int line = 0;
	private int regNum = 0;
	public Expression(){
		
	}
	public Expression(String id, int line){
		this.id = id;
		this.line = line;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getRegNum() {
		return regNum;
	}
	public void setRegNum(int regNum) {
		this.regNum = regNum;
	}
	public abstract void print(String tab);
	public abstract Type generateIntermediateCode(Function fun);
}
