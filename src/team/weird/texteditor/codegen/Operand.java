package team.weird.texteditor.codegen;

public class Operand {
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
	
}
