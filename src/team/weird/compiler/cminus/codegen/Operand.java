package team.weird.compiler.cminus.codegen;

import java.io.FileWriter;
import java.io.IOException;

public class Operand implements printIntermadiateCode{
	private Object operand;
	private OperandType opType;
	
	public Operand(OperandType type, Object operand){
		this.operand = operand;
		this.opType = type;
	}

	public Object getOperand() {
		return operand;
	}

	public void setOperand(Object operand) {
		this.operand = operand;
	}

	public OperandType getOpType() {
		return opType;
	}

	public void setOpType(OperandType opType) {
		this.opType = opType;
	}

	@Override
	public void print(FileWriter fw) {
		// TODO Auto-generated method stub
		try{
			System.out.print(opType + " " + operand);
			fw.write(opType + " " + operand);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
