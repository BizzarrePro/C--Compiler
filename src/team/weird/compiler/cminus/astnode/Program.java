package team.weird.compiler.cminus.astnode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import team.weird.compiler.cminus.codegen.Instruction;
import team.weird.compiler.cminus.codegen.IntermediateCodeGen;

public class Program implements IntermediateCodeGen{
	private ArrayList<Declaration> declarations = new ArrayList<Declaration>();
	public void addDeclaration(Declaration dec) {
		declarations.add(dec);
	}
	public Program(){
		
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
			Declaration dec = iter.next();
			dec.declare();
		}
	}
	@Override
	public Instruction generateIntermediateCode() {
		// TODO Auto-generated method stub
		declareVarAndFun();
		Instruction ins = null;
		Instruction first = null;
		for(Declaration e : declarations){
			if(ins == null){
				ins = e.generateIntermediateCode();
				first = ins;
			} else {
				Instruction temp = e.generateIntermediateCode();
				ins.setNextIns(temp);
				ins = temp;
			}
		}
		return first;
	}
	public void printIntermadiateCode(Instruction ins){
		FileWriter fw = null;
		try{
			fw = new FileWriter(new File("./compile/temp.qua"));
			ins.print(fw);
			Instruction temp = ins.getNextIns();
			while(temp != null){
				temp.print(fw);
				temp = temp.getNextIns();
			}
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
