package team.weird.compiler.cminus.astnode;

import java.util.ArrayList;

import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Operand;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.codegen.Operation;
import team.weird.compiler.cminus.semantic.Semantic;
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
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "CallExpression: " + id + " ( ");
		for(Expression exp : argsList) {
			exp.print(tab + "\t");
			System.out.println();
		}
		System.out.println(tab + " )");
	}
	@Override
	public Type generateIntermediateCode(Function fun) {
		// TODO Auto-generated method stub
		for(int i = argsList.size() - 1; i >= 0; i--){
			Operation op = new Operation(OperandType.PUSH, fun.getCurrBlock());
			argsList.get(i).generateIntermediateCode(fun);
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
		
		op = new Operation(OperandType.ASSIGN, fun.getCurrBlock());
		oper = new Operand(OperandType.RET, "ret");
		op.setSrcOperand(0, oper);
		
		super.setRegNum(fun.getNewRegisterNum());
		oper = new Operand(OperandType.REG, super.getRegNum());
		op.setDestOperand(0, oper);
		fun.getCurrBlock().appendOperation(op);
		if(Semantic.globalFuntionTable.containsKey(id))
			return Semantic.globalFuntionTable.get(id).getType();
		else
			return Type.NULL;
	}
	
}
