package team.weird.texteditor.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import team.weird.texteditor.parser.Symbol.RightProduction;

public class SelectSet {
	HashMap<String, Symbol> symbolMap;
	HashSet<String> termMap;
	public SelectSet(HashMap<String, Symbol> symbolMap, HashSet<String> termMap){
		this.symbolMap = symbolMap;
		this.termMap = termMap;
	}
	public void createSelectSet(){
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet().iterator();
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
}
