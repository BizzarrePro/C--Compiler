package team.weird.compiler.cminus.codegen;

import java.io.FileWriter;
import java.io.IOException;

public class BasicBlock implements printIntermadiateCode{
	private Function function;
	private BasicBlock prevBlock;
	private BasicBlock nextBlock;
	private Operation firstOper;
	private Operation lastOper;
	private int BlockID;
	public BasicBlock(Function function){
		this(function, null);
	}
	public BasicBlock(Function func, BasicBlock prev){
		this.function = func;
		this.prevBlock = prev;
		this.BlockID = func.getNewRegisterNum();
		if(prev != null)
			prev.setNextBlock(this);

	}
	public BasicBlock getPrevBlock() {
		return prevBlock;
	}
	public void setPrevBlock(BasicBlock prevBlock) {
		this.prevBlock = prevBlock;
	}
	public BasicBlock getNextBlock() {
		return nextBlock;
	}
	public void setNextBlock(BasicBlock nextBlock) {
		this.nextBlock = nextBlock;
	}
	public Operation getFirstOper() {
		return firstOper;
	}
	public void setFirstOper(Operation firstOper) {
		this.firstOper = firstOper;
	}
	public Operation getLastOper() {
		return lastOper;
	}
	public void setLastOper(Operation lastOper) {
		this.lastOper = lastOper;
	}
	public int getBlockID() {
		return BlockID;
	}
	public void setBlockID(int blockID) {
		BlockID = blockID;
	}
	public Function getFunction() {
		return function;
	}
	public void setFunction(Function function) {
		this.function = function;
	}
	public int incBlockId(){
		return ++BlockID;
	}
	public void appendOperation(Operation op) {
		if(firstOper != null){
			lastOper.setNextOper(op);
			lastOper = op;
		} 
		else {
			firstOper = op;
			lastOper = op;
		}
	}
	@Override
	public void print(FileWriter fw) {
		// TODO Auto-generated method stub
		System.out.println("  BLOCK " + this.getBlockID()+": ");
		try{
			fw.write("  BLOCK " + this.getBlockID()+": \r\n");
			Operation op = firstOper;
			while(op != null){
				op.print(fw);
				op = op.getNextOper();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
