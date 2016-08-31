package team.weird.compiler.cminus.asmgen;

import team.weird.compiler.cminus.codegen.BasicBlock;
import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Instruction;
import team.weird.compiler.cminus.codegen.Operand;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.codegen.Operation;
import team.weird.compiler.cminus.codegen.Parameter;
import team.weird.compiler.cminus.parser.FirstSet;

public class ObjectCodeGenerator {
	private static final int PARAM_OFFSET = 8;
	private Instruction ins;
	public ObjectCodeGenerator(Instruction ins){
		this.ins = ins;
	}
	public void generateASM(){
		convertFuncEntry();
		convertRetReg();
		convertOperations();
	}
	private void convertOperations() {
		
		
	}
	private void convertFuncExit(BasicBlock b) {
		b.removeOper(b.getFirstOper());
		Operation oper1 = new Operation(OperandType.MOV, b);
		Operand src0 = new Operand(OperandType.GR, "BP");
		oper1.setSrcOperand(0, src0);
		Operand dest0 = new Operand(OperandType.GR, "SP");
		oper1.setDestOperand(0, dest0);
		b.InsertOper(b.getFirstOper(), oper1);
		
		Operation oper2 = new Operation(OperandType.POP, b);
		dest0 = new Operand(OperandType.GR, "BP");
		oper2.setDestOperand(0, dest0);
		b.InsertOper(oper1, oper2);
		
	}
	private void convertFuncDec(BasicBlock b) {
		b.removeOper(b.getFirstOper());
		Operation op1 = new Operation(OperandType.PUSH, b);
		Operand src0 = new Operand(OperandType.GR, "BP");
		op1.setSrcOperand(0, src0);
		if(b.getFirstOper() == null) {
			b.appendOperation(op1);
		}
		else {
			b.InsertOper(b.getFirstOper(), op1);
		}
		
		Operation op2 = new Operation(OperandType.MOV, b);
		src0 = new Operand(OperandType.GR, "SP");
		op2.setSrcOperand(0, src0);
		Operand dest0 = new Operand(OperandType.GR, "BP");
		op2.setDestOperand(0, dest0);
		b.InsertOper(op1, op2);
		
		Operation insertedOp = op2;
		
		Function fun = b.getFunction();
		int paramOffset = PARAM_OFFSET;
		for(Parameter currParam = fun.getFirstParam(); currParam != null;
				currParam = currParam.getNextParam() ){
			String name = currParam.getName();
			int regNum = fun.getSymbolTable().get(name).getRegNum();
			Operation loadOp = new Operation(OperandType.LOAD, b);
			src0 = new Operand(OperandType.GR, "SP");
			loadOp.setSrcOperand(0, src0);
			Operand src1 = new Operand(OperandType.INT, paramOffset);
			loadOp.setSrcOperand(1, src1);
			dest0 = new Operand(OperandType.REG, regNum);
			loadOp.setDestOperand(0, dest0);
			b.InsertOper(insertedOp, loadOp);
			insertedOp = loadOp;
			paramOffset += 4;
		}
	}
	private void convertRetReg() {
		for (Instruction curr = ins; curr != null; curr = curr.getNextIns()){
			Function func = (Function)curr;
			for(BasicBlock currBlock = func.getFirstBlock(); currBlock != null; 
					currBlock = currBlock.getNextBlock()){
				for(Operation currOper = currBlock.getFirstOper(); currOper != null;
					currOper = currOper.getNextOper()){
					for (int i = 0; i < Operation.MAX_DEST_OPERAND_NUM; i++) {
						Operand currOperand = currOper.getDestOperand(i);
						if( currOperand != null && 
								currOperand.getOpType() == OperandType.RET){
							currOperand.setOpType(OperandType.GR);
							currOperand.setOperand("AX");
						}
					}
					for (int i = 0; i < Operation.MAX_SRC_OPERAND_NUM; i++) {
						Operand currOperand = currOper.getSrcOperand(i);
						if ( currOperand != null && 
								currOperand.getOpType() == OperandType.RET) {
							currOperand.setOpType(OperandType.GR);
							currOperand.setOperand("AX");
						}
					}
				}
			}
		}
		
	}
	private void convertFuncEntry() {
		for (Instruction curr = ins; curr != null; ins = ins.getNextIns()){
			Function fun = (Function)curr;
			for(BasicBlock b = fun.getFirstBlock(); b != null; b = b.getNextBlock()){
				if(b.getFirstOper() != null &&
						b.getFirstOper().getOpType() == OperandType.FUNC_DEC){
					convertFuncDec(b);
				}
				else if (b.getFirstOper() != null &&
						b.getFirstOper().getOpType() == OperandType.FUNC_EXIT){
					convertFuncExit(b);
				}
			}
		}
	}
}
