package team.weird.texteditor.astnode;

import team.weird.texteditor.semantic.SymbolAttr.Type;

public class ArrayDeclaration extends VariableDeclaration implements PrintASTree{
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
}
