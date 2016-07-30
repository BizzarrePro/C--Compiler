package team.weird.texteditor.astnode;

import java.util.ArrayList;
import java.util.Iterator;

public class Program {
	private static final Program INSTANCE = new Program();
	private ArrayList<Declaration> declarations = new ArrayList<Declaration>();
	public void addDeclaration(Declaration dec) {
		declarations.add(dec);
	}
	private Program(){
		
	}
	public static Program getInstance(){
		return INSTANCE;
	}
	public ArrayList<Declaration> getDeclarations() {
		return declarations;
	}
	public void setDeclarations(ArrayList<Declaration> declarations) {
		this.declarations = declarations;
	}
	public void printASTree(){
		Iterator<Declaration> iter = declarations.iterator();
		while(iter.hasNext()){
			iter.next().print("");
		}
	}
}
