package team.weird.texteditor.astnode;

import team.weird.texteditor.semantic.SymbolAttr.Type;

public class VariableDeclaration extends Declaration{
	public VariableDeclaration(String id, Type type){
		super(id, type);
	}
}
