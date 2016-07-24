package team.weird.texteditor.astnode;

import team.weird.texteditor.semantic.SymbolAttr.Type;

public class VariableDeclaration extends Declaration implements PrintASTree{
	public VariableDeclaration(String id, Type type, int line){
		super(id, type, line);
	}

	@Override
	public void print(String tab) {
		// TODO Auto-generated method stub
		System.out.println(tab + "VariableDeclaration: " + getId());
	}
}
