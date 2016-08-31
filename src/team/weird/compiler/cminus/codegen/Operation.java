package team.weird.compiler.cminus.codegen;

import java.io.FileWriter;
import java.io.IOException;


public class Operation extends Instruction{
	public static final int MAX_DEST_OPERAND_NUM = 2;
	public static final int MAX_SRC_OPERAND_NUM = 4;
	private BasicBlock ownBlock;
	private Operation prevOper;
	private Operation nextOper;
	private OperandType opType;
	private Operand[] src;
	private Operand[] dest;
	private int opId;
	private Operand sourceReg;
	private Operand destinationReg;
	private int srcNum;
	private int destNum;
	public Operation (OperandType ins, BasicBlock ownBlock){
		this.opType = ins;
		this.ownBlock = ownBlock;
		
		src = new Operand[MAX_SRC_OPERAND_NUM];
		dest = new Operand[MAX_DEST_OPERAND_NUM];
		
		this.srcNum = -1;
		this.destNum = -1;
	}
	public OperandType getOpType() {
		return opType;
	}
	public void setOpType(OperandType opType) {
		this.opType = opType;
	}
	public Operand getSourceReg() {
		return sourceReg;
	}
	public void setSourceReg(Operand sourceReg) {
		this.sourceReg = sourceReg;
	}
	public Operand getDestinationReg() {
		return destinationReg;
	}
	public void setDestinationReg(Operand destinationReg) {
		this.destinationReg = destinationReg;
	}
	public BasicBlock getOwnBlock() {
		return ownBlock;
	}
	public void setOwnBlock(BasicBlock ownBlock) {
		this.ownBlock = ownBlock;
	}
	public Operation getPrevOper() {
		return prevOper;
	}
	public void setPrevOper(Operation prevOper) {
		this.prevOper = prevOper;
	}
	public Operation getNextOper() {
		return nextOper;
	}
	public void setNextOper(Operation nextOper) {
		this.nextOper = nextOper;
	}
	public int getOpId() {
		return opId;
	}
	public void setOpId(int opId) {
		this.opId = opId;
	}
	public void setSrcOperand(int index, Operand op){
		src[index] = op;
		if(srcNum < index)
			srcNum = index;
	}
	public Operand getSrcOperand(int index){
		return src[index];
	}
	public void setDestOperand(int index, Operand op){
		dest[index] = op;
		if(destNum < index)
			destNum = index;
	}
	public Operand getDestOperand(int index){
		return dest[index];
	}
	public void decSrcNum(){
		--srcNum;
	}
	public void incSrcNum(){
		++srcNum;
	}
	public void removeOperation() {
		if(this.getPrevOper() == null){
			this.getNextOper().setPrevOper(null);
		}
		else{
			this.getPrevOper().setNextOper(this.getNextOper());
		}
	}
	@Override
	public void print(FileWriter fw) {
		// TODO Auto-generated method stub
		try{
			System.out.print("\t"+getOpType()+" [");
			fw.write("\t"+getOpType()+" [");
			for(int i = 0; i <= destNum; i++) 
				if(dest[i] != null){
					if(i != 0)
						System.out.print(" ");
					dest[i].print(fw);	
				}
			System.out.print("]  [");
			fw.write("]  [");
			for(int i = 0; i <=srcNum; i++)
				if(src[i] != null){
					if(i != 0){
						System.out.print(" ");
						fw.write(" ");
					}
					src[i].print(fw);
				}
			System.out.print("]\r\n");
			fw.write("]\r\n");
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
