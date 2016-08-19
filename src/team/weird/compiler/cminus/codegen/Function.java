package team.weird.compiler.cminus.codegen;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import team.weird.compiler.cminus.astnode.VariableDeclaration;
import team.weird.compiler.cminus.codegen.OperandType;
import team.weird.compiler.cminus.semantic.Type;

public class Function extends Instruction{
	private Type retType;
	private String name;
	private Parameter firstParam;
	private BasicBlock firstBlock;
	private BasicBlock lastBlock;
	private BasicBlock currBlock;
	private BasicBlock retBlock;
	private int registerNum;
	private HashMap<String, SymbolAttribute> symbolTable;
	public Function(){
		
	}
	public Function(Type retType, String name){
		this.retType = retType;
		this.name = name;
		this.symbolTable = new HashMap<String, SymbolAttribute>();
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
	public HashMap<String, SymbolAttribute> getSymbolTable() {
		return symbolTable;
	}
	public void setSymbolTable(HashMap<String, SymbolAttribute> symbolTable) {
		this.symbolTable = symbolTable;
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
	public void print(FileWriter fw) {
		// TODO Auto-generated method stub
		System.out.println("(FUNCTION	"+name);
		try{
			fw.write("(FUNCTION	"+name+"\r\n");
			Parameter temp = firstParam;
			while(temp != null){
				if(temp != firstParam){
					System.out.print(" ");
					fw.write(" ");
				}
				System.out.print(temp.getType()+" "+temp.getName());
				fw.write(temp.getType()+" "+temp.getName());
				temp = temp.getNextParam();
			}
			BasicBlock bb = firstBlock;
			while(bb != null){
				bb.print(fw);
				bb = bb.getNextBlock();
			}
			System.out.println(")");
			fw.write(")\r\n");
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	public BasicBlock constructRetBlock() {
		retBlock = new BasicBlock(this);
		Operation op = new Operation(OperandType.FUNC_EXIT, retBlock);
		retBlock.appendOperation(op);
		op = new Operation(OperandType.RET, retBlock);
		Operand src = new Operand(OperandType.RET, "ret");
		op.setSrcOperand(0, src);
		retBlock.appendOperation(op);
		return retBlock;
	}
	public void appendBlock(BasicBlock block) {
		lastBlock.setNextBlock(block);
		block.setPrevBlock(lastBlock);
		BasicBlock curr = block;
		while(curr.getNextBlock() != null)
			curr = curr.getNextBlock();
		lastBlock = curr;
	}
	public void appendToCurrBlock(BasicBlock block) {
		BasicBlock curr = getCurrBlock();
		curr.setNextBlock(block);
		block.setPrevBlock(curr);
		BasicBlock last = block;
		while(last.getNextBlock() != null)
			last = last.getNextBlock();
		if (this.lastBlock == currBlock)
			this.lastBlock = last;
		//
	}

}
