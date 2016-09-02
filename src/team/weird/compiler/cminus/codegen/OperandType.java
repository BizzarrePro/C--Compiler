package team.weird.compiler.cminus.codegen;

import team.weird.compiler.cminus.astnode.Operator;
import team.weird.compiler.cminus.semantic.Type;

public enum OperandType {
	FUNC_DEC, FUNC_EXIT, MOV, INT, FLOAT, REG, BLOCK, ADD, SUB, MUL, DIV,
	EQUAL, GTH, LTH, GETH, CMP, LETH, NOTEQ, NULL, PUSH, POP, CALL, FUNC_NAME, RET, BEQ,
	BNE, JMP, LOAD, VAR_NAME, STORE, GR, FR, BLT, BLE, BGT, BGE;
	public static OperandType typeConvert(Operator opera){
		switch(opera){
			case MUL:	return MUL;
			case DIV:	return DIV;
			case ADD:	return ADD;
			case SUB:	return SUB;
			case NEQ:	return NOTEQ;
			case GTHAN:	return GTH;
			case LTHAN:	return LTH;
			case GTEQ:	return GETH;
			case LTEQ:	return LETH;
			case EQUAL:	return EQUAL;
		}
		return NULL;
	}
	public static OperandType literalConvert(Object o){
		if(o instanceof Double)
			return FLOAT;
		else if(o instanceof Integer)
			return INT;
		return NULL;
	}
	public static OperandType typeConvert(Type o){
		if(o == Type.FLOAT)
			return FLOAT;
		else if(o == Type.INT)
			return INT;
		return NULL;
	}
	public static boolean isBranchOp(OperandType type){
		return type == BEQ || type == BNE;
	}
	public static boolean isExpressionOp(OperandType opType) {
		return opType == ADD || opType == MUL || opType == DIV || opType == SUB;
	}
	public static boolean isOperand(OperandType op){
		return op == INT || op == FLOAT;
	}
}
