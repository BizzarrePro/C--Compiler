package team.weird.texteditor.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import team.weird.texteditor.parser.Symbol.RightProduction;

public class SelectSet {
	HashMap<String, Symbol> symbolMap;
	HashSet<String> untermMap;
	public SelectSet(HashMap<String, Symbol> symbolMap, HashSet<String> untermMap){
		this.symbolMap = symbolMap;
		this.untermMap = untermMap;
	}
	public void createSelectSet(){
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet().iterator();
		while(symIter.hasNext()){
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> proIter = temp.rightList.iterator();
			while(proIter.hasNext()){
				String firstSymbol = proIter.next().getFirstRightSymbol();
				if(untermMap.contains(firstSymbol))
					temp.selectSet.add(firstSymbol);
				else{
					temp.selectSet.addAll(temp.firstSet);
					temp.selectSet.remove("empty");
				}
			}
		}
		
	}
}
