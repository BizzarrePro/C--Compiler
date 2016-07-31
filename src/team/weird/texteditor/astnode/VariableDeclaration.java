package team.weird.texteditor.astnode;

import team.weird.texteditor.codegen.Instruction;
import team.weird.texteditor.codegen.IntermediateCodeGen;
import team.weird.texteditor.semantic.ErrorList;
import team.weird.texteditor.semantic.Semantic;
import team.weird.texteditor.semantic.SemanticException;
import team.weird.texteditor.semantic.Type;

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
		ErrorList err = ErrorList.getInstance();
		if(!Semantic.globalSymbolTable.containsKey(id))
			Semantic.globalSymbolTable.put(id, this);
		else
			err.addException(new SemanticException(id, line, 2));
		return null;
	}
}
