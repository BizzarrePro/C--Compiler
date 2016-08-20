package team.weird.compiler.cminus.astnode;

import java.io.FileWriter;
import java.io.IOException;

import team.weird.compiler.cminus.codegen.Function;

public class ExpressionStatement extends Statement{
	private Expression exp = null;
	public ExpressionStatement(Expression exp){
		this.exp = exp;
	}
	public ExpressionStatement(){
		
	}
	public Expression getExp() {
		return exp;
	}
	public void setExp(Expression exp) {
		this.exp = exp;
	}
	@Override
	public void print(String tab, FileWriter fw) {
		// TODO Auto-generated method stub
		if(exp != null) {
			try{
				System.out.println(tab + "ExpressionStatement: ");
				fw.write(tab + "ExpressionStatement: \r\n");
				exp.print(tab + "\t", fw);
				System.out.println();
				fw.write("\r\n");
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	@Override
	public void generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		if(exp != null) {
			exp.generateIntermediateCode(fun);
		}
	}
	
}
