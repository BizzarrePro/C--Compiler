package team.weird.compiler.cminus.astnode;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Operand;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.codegen.Operation;
import team.weird.compiler.cminus.semantic.ErrorList;
import team.weird.compiler.cminus.semantic.Semantic;
import team.weird.compiler.cminus.semantic.SemanticException;
import team.weird.compiler.cminus.semantic.Type;

public class CallExpression extends Expression{
	private String id;
	private ArrayList<Expression> argsList = new ArrayList<Expression>();
	public CallExpression(String id, ArrayList<Expression> argsList){
		this.id = id;
		this.argsList = argsList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<Expression> getArgsList() {
		return argsList;
	}
	public void setArgsList(ArrayList<Expression> argsList) {
		this.argsList = argsList;
	}
	@Override
	public void print(String tab, FileWriter fw) {
		// TODO Auto-generated method stub
		System.out.println(tab + "CallExpression: " + id + " ( ");
		try{
			fw.write(tab + "CallExpression: " + id + " ( \r\n");
			for(Expression exp : argsList) {
				exp.print(tab + "\t", fw);
				System.out.println();
				fw.write("\r\n");
			}
			System.out.println(tab + " )");
			fw.write(tab + " )\r\n");
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	@Override
	public Type generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		ErrorList err = ErrorList.getInstance();
		FunctionDeclaration funDec = null;
		if(Semantic.globalFuntionTable.containsKey(id))
			funDec = Semantic.globalFuntionTable.get(id);
		else 
			err.addException(new SemanticException(id, super.getLine(), 8));
		
		for(int i = argsList.size() - 1; i >= 0; i--){
			Operation op = new Operation(OperandType.PUSH, fun.getCurrBlock());
			Type paramType = argsList.get(i).generateIntermediateCode(fun);
			if(funDec != null){
				if(funDec.getParameters().get(i) == null || funDec.getParameters().get(i).getType() != paramType ||
						(fun.getSymbolTable().get(id) != null && (funDec.getParameters().get(i).isArray() != 
						fun.getSymbolTable().get(id).isArray()))){
					err.addException(new SemanticException(id, super.getLine(), 11));
				}
			}
			Operand oper = null;
			if(argsList.get(i) instanceof LiteralExpression){
				Object num = ((LiteralExpression)argsList.get(i)).getNumber();
				if(num.getClass() == Integer.class)
					oper = new Operand(OperandType.INT, (int)num);
				else
					oper = new Operand(OperandType.FLOAT, (double)num);
			}
			else 
				oper = new Operand(OperandType.REG, argsList.get(i).getRegNum());
			op.setSrcOperand(0, oper);
			fun.getCurrBlock().appendOperation(op);
		}
		Operation op = new Operation(OperandType.CALL, fun.getCurrBlock());
		//Attribute
		Operand oper = new Operand(OperandType.FUNC_NAME, getId());
		op.setSrcOperand(0, oper);
		fun.getCurrBlock().appendOperation(op);
		
		op = new Operation(OperandType.MOV, fun.getCurrBlock());
		oper = new Operand(OperandType.RET, "ret");
		op.setSrcOperand(0, oper);
		
		super.setRegNum(fun.getNewRegisterNum());
		oper = new Operand(OperandType.REG, super.getRegNum());
		op.setDestOperand(0, oper);
		fun.getCurrBlock().appendOperation(op);
		if(funDec != null)
			return funDec.getType();
		else
			return Type.NULL;
	}
	
}
