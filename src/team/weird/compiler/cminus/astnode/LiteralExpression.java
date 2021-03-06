package team.weird.compiler.cminus.astnode;

import java.io.FileWriter;
import java.io.IOException;

import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.semantic.Type;

public class LiteralExpression extends Expression {
	private Object number = null;
	public LiteralExpression(Object number, int line){
		this.number = number;
		super.setLine(line);
	}
	public Object getNumber() {
		return number;
	}
	public void setNumber(Object number) {
		this.number = number;
	}
	@Override
	public void print(String tab, FileWriter fw) {
		// TODO Auto-generated method stub
		System.out.println(tab + "LiteralExpression: " + number.toString());
		try {
			fw.write(tab + "LiteralExpression: " + number.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public Type generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		if(number instanceof Double)
			return Type.FLOAT;
		else if (number instanceof Integer)
			return Type.INT;
		return Type.NULL;
			
	}
	
}
