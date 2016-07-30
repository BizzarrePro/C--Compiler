package team.weird.texteditor.astnode;

import team.weird.texteditor.codegen.Instruction;
import team.weird.texteditor.semantic.Semantic;
import team.weird.texteditor.semantic.SymbolAttr.Type;

public class VariableDeclaration extends Declaration implements PrintASTree, IntermediateCodeGen{
	public VariableDeclaration(String id, Type type, int line){
		super(id, type, line);
	}

	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "VariableDeclaration: " + getId());
	}

	@Override
	public Instruction generateIntermediateCode() {
		// TODO Auto-generated method stub
		Semantic.globalSymbolTable.put(id, this);
		return null;
	}
}
