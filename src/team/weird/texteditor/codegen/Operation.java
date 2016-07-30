package team.weird.texteditor.codegen;


public class Operation extends Instruction{
	private BasicBlock ownBlock;
	private Operation prevOper;
	private Operation nextOper;
	private InstructionType opType;
	private int opId;
	public Operation (){
		
	}
	public Operation (InstructionType ins, BasicBlock ownBlock){
		this.opType = ins;
		this.ownBlock = ownBlock;
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
	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}

}
