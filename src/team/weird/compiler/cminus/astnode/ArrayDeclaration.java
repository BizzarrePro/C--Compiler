package team.weird.compiler.cminus.astnode;

import team.weird.compiler.cminus.codegen.Instruction;
import team.weird.compiler.cminus.codegen.IntermediateCodeGen;
import team.weird.compiler.cminus.semantic.ErrorList;
import team.weird.compiler.cminus.semantic.Semantic;
import team.weird.compiler.cminus.semantic.SemanticException;
import team.weird.compiler.cminus.semantic.Type;

public class ArrayDeclaration extends VariableDeclaration implements PrintASTree, IntermediateCodeGen{
	private int length = 0;
	public ArrayDeclaration(String id, Type type, int length, int line){
		super(id, type, line);
		this.length = length;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "VariableDeclaration: " + getId()+"[ " + length + " ]");
	}
	@Override
	public Instruction generateIntermediateCode() {
		// TODO Auto-generated method stub
		ErrorList err = ErrorList.getInstance();
		if(!Semantic.globalSymbolTable.containsKey(id))
			Semantic.globalSymbolTable.put(id, this);
		else
			err.addException(new SemanticException(id, line, 2));
		return null;
	}
}
