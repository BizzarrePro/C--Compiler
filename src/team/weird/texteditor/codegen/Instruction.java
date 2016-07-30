package team.weird.texteditor.codegen;

public abstract class Instruction {
	private Instruction nextIns;
	public Instruction(){
		
	}
	public Instruction getNextIns() {
		return nextIns;
	}
	public void setNextIns(Instruction nextIns) {
		this.nextIns = nextIns;
	}
	public abstract void print();
}
