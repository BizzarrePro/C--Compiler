package team.weird.compiler.cminus.asmgen;

import team.weird.compiler.cminus.codegen.BasicBlock;
import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Instruction;
import team.weird.compiler.cminus.codegen.Operand;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.codegen.Operation;
import team.weird.compiler.cminus.codegen.Parameter;
import team.weird.compiler.cminus.parser.FirstSet;
import team.weird.compiler.cminus.semantic.Semantic;

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
		for(Instruction curr = ins; curr != null; curr = curr.getNextIns()) {
			Function fun = (Function)curr;
			for(BasicBlock currBlock = fun.getFirstBlock(); currBlock != null;
					currBlock = currBlock.getNextBlock()){
				for(Operation currOp = currBlock.getFirstOper(); currOp != null;
					currOp = currOp.getNextOper()){
					switch (currOp.getOpType()) {
					case LTH:
					case LETH:
					case GTH:
					case GETH:
					case EQUAL:
					case NOTEQ:
						convertComparison(currOp);
						break;
					case BEQ:
					case BNE:
						convertBranch(currOp);
						break;
					case ADD:
						convertAdd(currOp);
						break;
					case SUB:
						convertSubtract(currOp);
						break;
					case MUL:
						convertMultiply(currOp);
						break;
					case DIV:
						convertDivide(currOp);
						break;
					case CALL:
						convertCall(currOp);
						break;
					default:
					}
				}
			}
		}
		
	}
	private void convertCall(Operation currOp) {
		if(currOp.getSrcOperand(0).getOpType() != OperandType.FUNC_NAME)
			return;
		String funcName = (String)currOp.getSrcOperand(0).getOperand();
		int paramsNum = Semantic.globalFuntionTable.get(funcName).getParameters().size();
		int offset = paramsNum << 2;
		if(paramsNum > 0){
			BasicBlock block = currOp.getOwnBlock();
			Operation op = new Operation(OperandType.ADD, block);
			Operand src0 = new Operand(OperandType.GR, "SP");
			op.setSrcOperand(0, src0);
			Operand src1 = new Operand(OperandType.INT, offset);
			op.setSrcOperand(1, src1);
			Operand dest0 = new Operand(OperandType.GR, "SP");
			op.setDestOperand(0, dest0);
			block.InsertOper(currOp, op);
		}
	}
	private void convertSubtract(Operation currOp) {
		BasicBlock block = currOp.getOwnBlock();
		Operand dest = currOp.getDestOperand(0);
		if (dest.getOpType() == OperandType.REG){
			int destReg = (int)currOp.getDestOperand(0).getOperand();
			if(currOp.getSrcOperand(0).getOpType() == OperandType.REG &&
					destReg == (int)currOp.getSrcOperand(0).getOperand())
				return;
		}
		else if (dest.getOpType() == OperandType.GR) {
			String destGR = (String) currOp.getDestOperand(0).getOperand();
			if(currOp.getSrcOperand(0).getOpType() == OperandType.GR &&
					destGR.equals((String)currOp.getSrcOperand(0).getOperand()))
				return;
		}
		Operation mov1 = new Operation(OperandType.MOV, block);
		mov1.setSrcOperand(0, currOp.getSrcOperand(0));
		//int regNum = block.getFunction().getNewRegisterNum();
		mov1.setDestOperand(0, new Operand(currOp.getDestOperand(0).getOpType(), currOp.getDestOperand(0).getOperand()));
		block.InsertOper(currOp, mov1);
		currOp.setSrcOperand(0, new Operand(currOp.getDestOperand(0).getOpType(), currOp.getDestOperand(0).getOperand()));
	}
	private void convertAdd(Operation currOp) {
		BasicBlock block = currOp.getOwnBlock();
		Operand dest = currOp.getDestOperand(0);
		
		if (dest.getOpType() == OperandType.REG){
			int destReg = (int)currOp.getDestOperand(0).getOperand();
			if(currOp.getSrcOperand(0).getOpType() == OperandType.REG) {
				if(destReg == (int)currOp.getSrcOperand(0).getOperand())
					return;
			}
			
			if (currOp.getSrcOperand(1).getOpType() == OperandType.REG &&
					destReg == (int)currOp.getSrcOperand(1).getOperand()){
				Operand temp = currOp.getSrcOperand(0);
				currOp.setSrcOperand(0, currOp.getSrcOperand(1));
				currOp.setSrcOperand(1, temp);
				return;
			}
		}
		else if(dest.getOpType() == OperandType.GR) {
			String destGR = (String) currOp.getDestOperand(0).getOperand();
			if(currOp.getSrcOperand(0).getOpType() == OperandType.GR){
				if (destGR.equals((String)currOp.getSrcOperand(0).getOperand())){
					return;
				}
			}
			if(currOp.getSrcOperand(1).getOpType() == OperandType.GR &&
					destGR.equals((String)currOp.getSrcOperand(1).getOperand())){
				Operand temp = currOp.getSrcOperand(0);
				currOp.setSrcOperand(0, currOp.getSrcOperand(1));
				currOp.setSrcOperand(1, temp);
				return;
			}
		}
		//
		Operation mov1 = new Operation(OperandType.MOV, block);
		mov1.setSrcOperand(0, currOp.getSrcOperand(0));
		int regNum = block.getFunction().getNewRegisterNum();
		mov1.setDestOperand(0,  new Operand(currOp.getDestOperand(0).getOpType(), currOp.getDestOperand(0).getOperand()));
		block.InsertOper(currOp, mov1);
		currOp.setSrcOperand(0, new Operand(currOp.getDestOperand(0).getOpType(), currOp.getDestOperand(0)));
	}
	private void convertDivide(Operation currOp) {
		BasicBlock block = currOp.getOwnBlock();
		Operation mov1 = new Operation(OperandType.MOV, block);
		mov1.setSrcOperand(0, currOp.getSrcOperand(0));
		mov1.setDestOperand(0, new Operand(OperandType.GR, "AX"));
		block.InsertOper(currOp, mov1);
		
		if (currOp.getSrcOperand(1).getOpType() == OperandType.INT) {
			Operation mov2 = new Operation(OperandType.MOV, block);
			mov2.setSrcOperand(0, currOp.getSrcOperand(1));
			int regNum = block.getFunction().getNewRegisterNum();
			mov2.setDestOperand(0, new Operand(OperandType.REG, regNum));
			block.InsertOper(currOp, mov2);
			currOp.setSrcOperand(1, new Operand(mov2.getDestOperand(0).getOpType(), mov2.getDestOperand(0).getOperand()));
		}
		
		currOp.setSrcOperand(0, new Operand(OperandType.GR, "AX"));
		Operation postMov = new Operation(OperandType.MOV, block);
		postMov.setDestOperand(0, currOp.getDestOperand(0));
		postMov.setSrcOperand(0, new Operand(OperandType.GR, "AX"));
		block.InsertOper(currOp, postMov);
		currOp.setDestOperand(0, new Operand(OperandType.GR, "AX"));
		currOp.setDestOperand(1, new Operand(OperandType.GR, "DX"));
		
	}
	private void convertMultiply(Operation currOp) {
		BasicBlock block = currOp.getOwnBlock();
		Operation mov1 = new Operation(OperandType.MOV, block);
		mov1.setSrcOperand(0, currOp.getSrcOperand(0));
		mov1.setDestOperand(0, new Operand(OperandType.GR, "AX"));
		block.InsertOper(currOp, mov1);
		
		if (currOp.getSrcOperand(1).getOpType() == OperandType.INT) {
			Operation mov2 = new Operation(OperandType.MOV, block);
			mov2.setSrcOperand(0, currOp.getSrcOperand(1));
			int regNum = block.getFunction().getNewRegisterNum();
			mov2.setDestOperand(0, new Operand(OperandType.REG, regNum));
			block.InsertOper(currOp, mov2);
			currOp.setSrcOperand(1, new Operand(mov2.getDestOperand(0).getOpType(), mov2.getDestOperand(0).getOperand()));
		}
		
		currOp.setSrcOperand(0, new Operand(OperandType.GR, "AX"));
		Operation postMov = new Operation(OperandType.MOV, block);
		postMov.setDestOperand(0, currOp.getDestOperand(0));
		postMov.setSrcOperand(0, new Operand(OperandType.GR, "AX"));
		block.InsertOper(currOp, postMov);
		currOp.setDestOperand(0, new Operand(OperandType.GR, "AX"));
		currOp.setDestOperand(1, new Operand(OperandType.GR, "DX"));
		
	}
	private void convertBranch(Operation currOp) {
		
		
	}
	private void convertComparison(Operation currOp) {
		
		
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
