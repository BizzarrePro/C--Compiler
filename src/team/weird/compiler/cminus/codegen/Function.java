package team.weird.compiler.cminus.codegen;

import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.semantic.Type;

public class Function extends Instruction{
	private Type retType;
	private String name;
	private Parameter firstParam;
	private BasicBlock firstBlock;
	private BasicBlock lastBlock;
	private BasicBlock currBlock;
	private int registerNum;
	public Function(){
		
	}
	public Function(Type retType, String name){
		this.retType = retType;
		this.name = name;
	}
	public Type getRetType() {
		return retType;
	}
	public void setRetType(Type retType) {
		this.retType = retType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Parameter getFirstParam() {
		return firstParam;
	}
	public void setFirstParam(Parameter firstParam) {
		this.firstParam = firstParam;
	}
	public BasicBlock getFirstBlock() {
		return firstBlock;
	}
	public void setFirstBlock(BasicBlock firstBlock) {
		this.firstBlock = firstBlock;
	}
	public BasicBlock getLastBlock() {
		return lastBlock;
	}
	public void setLastBlock(BasicBlock lastBlock) {
		this.lastBlock = lastBlock;
	}
	public BasicBlock getCurrBlock() {
		return currBlock;
	}
	public void setCurrBlock(BasicBlock currBlock) {
		this.currBlock = currBlock;
	}
	public int getRegisterNum() {
		return registerNum;
	}
	public void setRegisterNum(int registerNum) {
		this.registerNum = registerNum;
	}
	public int getNewRegisterNum() {
		// TODO Auto-generated method stub
		return registerNum++;
	}
	public void createBlock(){
		firstBlock = new BasicBlock(this, null);
		lastBlock = firstBlock;
		Operation op = new Operation(OperandType.FUNC_DEC, firstBlock);
		firstBlock.appendOperation(op);
	}
	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}
	public BasicBlock constructRetBlock() {
		
		return null;
	}
	public void appendBlock(BasicBlock block) {
		
	}

}
