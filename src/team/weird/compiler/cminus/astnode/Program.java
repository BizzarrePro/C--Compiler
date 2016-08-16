package team.weird.compiler.cminus.astnode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
		FileWriter fw = null;
		File fi = new File("./compile/temp.ast");
		Iterator<Declaration> iter = declarations.iterator();
		try{
			fw = new FileWriter(fi);
			while(iter.hasNext()){
				iter.next().print("", fw);
			}
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException ex){
				ex.printStackTrace();
			}
		}
	}
	public void declareVarAndFun(){
		Iterator<Declaration> iter = declarations.iterator();
		while(iter.hasNext()){
			iter.next().declare();
		}
	}
}
