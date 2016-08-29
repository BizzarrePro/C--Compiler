package team.weird.compiler.cminus.optimizer;

import team.weird.compiler.cminus.codegen.BasicBlock;
import team.weird.compiler.cminus.codegen.Function;
import team.weird.compiler.cminus.codegen.Instruction;
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
			basicBlockOptimize((Function)f);
		}
	}
	//strength reduction
	//
	private void basicBlockOptimize(Function fun){
		for(BasicBlock b = fun.getFirstBlock(); b != null; b = b.getNextBlock()){
			if(b.getFirstOper() != null)
				continue;
			if(b.getNextBlock() == null)
				break;
			fun.removeBlock(b);
		}
		for(BasicBlock b = fun.getFirstBlock(); b != null; b = b.getNextBlock()){
			for (Operation o = b.getFirstOper(); o  != null; o = o.getNextOper()){
				
			}
		}
	}

}
