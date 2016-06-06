package team.weird.texteditor.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import team.weird.texteditor.parser.Symbol.RightProduction;

public class SelectSet {
	private HashMap<String, Symbol> symbolMap;
	private HashSet<String> termMap;
	public SelectSet(HashMap<String, Symbol> symbolMap, HashSet<String> termMap){
		this.setSymbolMap(symbolMap);
		this.termMap = termMap;
	}
	public void createSelectSet(){
		Iterator<Entry<String, Symbol>> symIter = getSymbolMap().entrySet().iterator();
		while(symIter.hasNext()){
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> proIter = temp.rightList.iterator();
			while(proIter.hasNext()){
				String firstSymbol = proIter.next().getFirstRightSymbol();
				if(termMap.contains(firstSymbol))
					temp.selectSet.add(firstSymbol);
				else if(firstSymbol.equals("empty"));
				else{
					temp.selectSet.addAll(temp.firstSet);
					temp.selectSet.remove("empty");
				}
			}
		}
		
	}
	public void display() {
		System.out.println();
		System.out
				.println("-------------------------Select Set-------------------------");
		System.out.println();
		Iterator<Entry<String, Symbol>> symIter = getSymbolMap().entrySet()
				.iterator();
		while (symIter.hasNext()) {
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<String> setIter = temp.followSet.iterator();
			System.out.print(temp.getUnterminatingString() + " Size: "
					+ temp.followSet.size() + " $$ {");
			while (setIter.hasNext()) {
				String print = setIter.next();
				System.out.print(print);
				if (setIter.hasNext())
					System.out.print(" ");
			}
			System.out.print("}");
			System.out.println();
		}
	}
	public HashMap<String, Symbol> getSymbolMap() {
		return symbolMap;
	}
	public void setSymbolMap(HashMap<String, Symbol> symbolMap) {
		this.symbolMap = symbolMap;
	}
}
