package team.weird.compiler.cminus.astnode;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Operand;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.codegen.Operation;
import team.weird.compiler.cminus.codegen.SymbolAttribute;
import team.weird.compiler.cminus.semantic.ErrorList;
import team.weird.compiler.cminus.semantic.Semantic;
import team.weird.compiler.cminus.semantic.SemanticException;
import team.weird.compiler.cminus.semantic.Type;


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
	@Override
	public void print(String tab, FileWriter fw) {
		// TODO Auto-generated method stub
		try{
			System.out.print(tab + "VariableExpression: " + getId());
			fw.write(tab + "VariableExpression: " + getId());
			if(array != null) {
				System.out.println("[ ");
				fw.write("[ \r\n");
				array.print(tab + "\t", fw);
				System.out.println("\n" + tab + "]");
				fw.write("\n" + tab + "] \r\n");
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	@Override
	public Type generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		ErrorList err = ErrorList.getInstance();
		HashMap<String, SymbolAttribute> local = fun.getSymbolTable();
		if(local.containsKey(super.getId())){
			setRegNum(local.get(getId()).getRegNum());
			return local.get(getId()).getType();
		}
		else if(Semantic.globalSymbolTable.containsKey(getId())){
			setRegNum(fun.getNewRegisterNum());
			Operation op = new Operation(OperandType.LOAD, fun.getCurrBlock());
			Operand oper = new Operand(OperandType.REG, getRegNum());
			fun.getCurrBlock().appendOperation(op);
			op.setDestOperand(0, oper);
			oper = new Operand(OperandType.VAR_NAME, getId());
			return Semantic.globalSymbolTable.get(getId()).getType();
		}
		else
			err.addException(new SemanticException(getId(), getLine(), 3));
		return Type.NULL;
	}
}
