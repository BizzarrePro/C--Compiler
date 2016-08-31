package team.weird.compiler.cminus.optimizer;

import java.util.HashMap;

import team.weird.compiler.cminus.codegen.BasicBlock;
import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Instruction;
import team.weird.compiler.cminus.codegen.Operand;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.codegen.Operation;

public class AlgebraOptimize implements Optimize{
	private Instruction first;
	public AlgebraOptimize(Instruction first){
		this.first = first;
	}
	@Override
	public void optimize() {
		// TODO Auto-generated method stub
		for(Instruction f = this.first; f != null; f = f.getNextIns()){
			removeEmptyBlockOptimize((Function)f);
		}
	}
	//strength reduction
	//
	private void reductStrengthOptimize(Function fun){
		for(BasicBlock b = fun.getFirstBlock(); b != null; b = b.getNextBlock()){
			boolean[] isRefer = new boolean[fun.getRegisterNum()];
			for(int i = 0; i < isRefer.length; i++)
				isRefer[i] = false;
			for(Operation op = b.getFirstOper(); op != null; op = op.getNextOper()){
				//fold constant
				if(OperandType.isExpressionOp(op.getOpType())){
					if(OperandType.isOperand(op.getSrcOperand(0).getOpType()) 
							&& OperandType.isOperand(op.getSrcOperand(1).getOpType())){
						foldConstant(op);
					}
					if(op.getOpType() == OperandType.MUL){
						if(op.getSrcOperand(0).getOpType() == OperandType.INT &&
								(int)op.getSrcOperand(0).getOperand() == 2){
							op.setOpType(OperandType.ADD);
							op.setSrcOperand(0, op.getSrcOperand(1));
						}
						else if(op.getSrcOperand(1).getOpType() == OperandType.INT &&
								(int)op.getSrcOperand(1).getOperand() == 2){
							op.setOpType(OperandType.ADD);
							op.setSrcOperand(0, op.getSrcOperand(0));
						}	
					}
					else if(op.getOpType() == OperandType.DIV){
						if(op.getSrcOperand(1).getOpType() == OperandType.INT &&
								(int)op.getSrcOperand(1).getOperand() == 2){
							op.setOpType(OperandType.MUL);
							op.setSrcOperand(1, new Operand(OperandType.FLOAT, 0.5));
						}	
					}
				}	
				//remove unused assignment
				if(op.getOpType() == OperandType.MOV || OperandType.isExpressionOp(op.getOpType())){
					if(op.getOpType() == OperandType.MOV){
						if(op.getSrcOperand(0).getOpType() == OperandType.REG) {
							int RegNum = (int)op.getSrcOperand(0).getOperand();
							isRefer[RegNum] = true;
						}	
					} 
					else {
						for(int i = 0; i < 2; i++){
							if(op.getSrcOperand(i).getOpType() == OperandType.REG) {
								int RegNum = (int)op.getSrcOperand(i).getOperand();
								isRefer[RegNum] = true;
							}
						}
					}
				}
			}
			for(Operation opera = b.getFirstOper(); opera != null; opera = opera.getNextOper()){
				if(opera.getDestOperand(0).getOpType() == OperandType.REG){
					if(isRefer[(int)opera.getDestOperand(0).getOperand()]){
						opera.removeOperation();
					}
				}
			}
		}
	}
	private void foldConstant(Operation op) {
		Object src1 = op.getSrcOperand(0);
		Object src2 = op.getSrcOperand(1);
		Object result = null;
		if(src1 != null && src2 != null){
			switch(op.getOpType()){
			case MUL:	result = ( src1 instanceof Double ? 
					(double)(src1) * (double)(src2) : (int)(src1) * (int)(src2));
			case ADD:	result = ( src1 instanceof Double ? 
					(double)(src1) + (double)(src2) : (int)(src1) + (int)(src2));
			case DIV:	result = ( src1 instanceof Double ? 
					(double)(src1) / (double)(src2) : (int)(src1) / (int)(src2));
			case SUB:	result = ( src1 instanceof Double ? 
					(double)(src1) - (double)(src2) : (int)(src1) - (int)(src2));
			}	
		}
		op.setSrcOperand(0, new Operand(result instanceof Double ? 
				OperandType.FLOAT : OperandType.INT, result));
		op.setSrcOperand(1, null);
		op.decSrcNum();
		op.setOpType(OperandType.MOV);
	}
	private void removeEmptyBlockOptimize(Function fun){
		int maxBBNum = fun.getBlockNum();
		int[] record = new int[maxBBNum];
		for(int i = 0; i < record.length; i++)
			record[i] = 0;
		//remove empty block
		for(BasicBlock b = fun.getFirstBlock(); b != null; b = b.getNextBlock()){
			if(b.getFirstOper() != null)
				continue;
			if(b.getNextBlock() == null)
				break;
			int emptyBlockNum = b.getBlockID();
			int nextBlockNum = b.getNextBlock().getBlockID();
			record[emptyBlockNum] = nextBlockNum; 
			fun.removeBlock(b);
		}
		//modify empty block reference
		for(BasicBlock b = fun.getFirstBlock(); b != null; b = b.getNextBlock()){
			for (Operation o = b.getFirstOper(); o  != null; o = o.getNextOper()){
				int targetOperandNum = 0;
//				if ((o.getOpType() != OperandType.JMP) && (OperandType.isBranchOp(o.getOpType())))
//					continue;
				if (o.getOpType() == OperandType.JMP)
					targetOperandNum = 0;
				else if(OperandType.isBranchOp(o.getOpType()))
					targetOperandNum = 2;
				else
					continue;
				int target = (int)(o.getSrcOperand(targetOperandNum).getOperand());
				int newTarget = record[target];
				if( newTarget != 0)
					if(newTarget < maxBBNum)
						o.getSrcOperand(targetOperandNum).setOperand(newTarget);
			}
			
		}
	}
	//remove block which just contains jump operation
	private void justJumpBlockOptimize(Function fun) {
		for (BasicBlock b = fun.getFirstBlock(); b != null; b = b.getNextBlock()){
			for (Operation o = b.getFirstOper(); o != null; o = o.getNextOper()) {
				if((o.getOpType() != OperandType.JMP) 
						&& !OperandType.isBranchOp(o.getOpType()))
					continue;
				int targetBlockId = -1;
				Operand targetOperand;
				if (o.getOpType() == OperandType.JMP) {
					if(o.getSrcOperand(0).getOpType() == OperandType.BLOCK) {
						targetOperand = o.getSrcOperand(0);
						targetBlockId = (int)targetOperand.getOperand();
					}
				} 
				else {
					if(o.getSrcOperand(2).getOpType() == OperandType.BLOCK){
						targetOperand = o.getSrcOperand(2);
						targetBlockId = (int)targetOperand.getOperand();
					}
				}
				if (targetBlockId != -1){
					BasicBlock targetBlock = BasicBlock.getBlockByBlockId(fun, targetBlockId);
					Operation targetOper = targetBlock.getFirstOper();
					if((targetOper != null ) && (targetOper.getOpType() == OperandType.JMP)) {
						if(targetOper.getSrcOperand(0).getOpType() == OperandType.BLOCK){
							int targetBlock2Id = (int)targetOper.getSrcOperand(0).getOperand();
							targetBlockId = targetBlock2Id;
						}
					}
					if (b.getNextBlock() == null)
						continue;
					int nextBlockNum = b.getNextBlock().getBlockID();
					if( nextBlockNum != targetBlockId )
						continue;
					b.setLastOper(o.getPrevOper());
					if (o.getPrevOper() == null)
						b.setFirstOper(null);
					else
						o.getPrevOper().setNextOper(null);
				}
			}
		}
	}
	private void unreachableBlockOptimize(Function fun){
		boolean[] passibleBlocks = new boolean[fun.getBlockNum()+1];
		for (BasicBlock b = fun.getFirstBlock(); b != null; b = b.getNextBlock()) {
			if(b == fun.getFirstBlock())
				continue;
			Operation lastOp = b.getPrevBlock().getLastOper();
			if(lastOp == null)
				continue;
			if( (lastOp.getOpType() != OperandType.JMP) &&
					lastOp.getOpType() != OperandType.RET)
				continue;
			if (lastOp.getOpType() == OperandType.JMP){
				int lastTarget = (int)lastOp.getSrcOperand(0).getOperand();
				if (lastTarget == b.getBlockID())
					continue;
			}
			passibleBlocks[b.getBlockID()] = true;
		}
		
		for (BasicBlock b = fun.getFirstBlock(); b != null; b = b.getNextBlock()){
			for (Operation o = b.getFirstOper(); o != null; o = o.getNextOper()){
				int targetOperandNum;
				if(o.getOpType() == OperandType.JMP)
					targetOperandNum = 0;
				else if (OperandType.isBranchOp(o.getOpType()))
					targetOperandNum = 2;
				else
					continue;
				
				int targetBlock = (int)o.getSrcOperand(targetOperandNum).getOperand();
				passibleBlocks[targetBlock] = false;
				
			}
		}
		for (BasicBlock b = fun.getFirstBlock(); b != null; b = b.getNextBlock()){
			if(!passibleBlocks[b.getBlockID()])
				fun.removeBlock(b);
		}
	}
	

}
