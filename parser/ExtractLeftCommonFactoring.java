package team.weird.texteditor.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import team.weird.texteditor.parser.Symbol.RightProduction;

public class ExtractLeftCommonFactoring {
	public HashMap<String, Symbol> symbolMap;

	public ExtractLeftCommonFactoring(HashMap<String, Symbol> symbolMap) {
		this.symbolMap = symbolMap;
		excuteExtractionOfCommonFactoring();
//		displayAfterElimination();
	}

	public void excuteExtractionOfCommonFactoring() {
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet()
				.iterator();
		HashMap<String, Symbol> tempMap = new HashMap<String, Symbol>();
		while (symIter.hasNext()) {
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			int length = 0;
			boolean breakTag = false;
			for (int i = 0; i < 10; i++) {
				if (temp.rightList.size() <= 1)
					break;
				if (temp.rightList.get(0).getSymbolByIndex(i) == null
						|| breakTag) {
					//length = i - 1;
					break;
				}
				String compareStr = temp.rightList.get(0).getSymbolByIndex(i);
				int j;
				for (j = 1; j < temp.rightList.size(); j++) {
//					System.out.println(temp.getUnterminatingString()+" "+compareStr+" "+temp.rightList.get(j)
//							.getSymbolByIndex(i)+" "+i+" " +j);
//					if (temp.rightList.get(j).getSymbolByIndex(i) == null) {
//						breakTag = true;
//						break;
//					}
					if (compareStr.equals(temp.rightList.get(j)
							.getSymbolByIndex(i)))
						;
					else {
						breakTag = true;
						break;
					}
				}
				if(j == temp.rightList.size())
					length++;
			}
			if (length > 1) {
				String newUnterminatingStr = temp.getUnterminatingString()
						+ "``";
				Symbol newUnterminatingSymbol = new Symbol(newUnterminatingStr);
				for (int i = 0; i < temp.rightList.size(); i++) {
					LinkedList<String> list = new LinkedList<String>();
					//System.out.println(length+" "+temp.getUnterminatingString());
					ListIterator<String> tempIter = temp.rightList.get(i)
							.getRightSymbolList().listIterator(length);
					if(!tempIter.hasNext())
						list.add("empty");
					while(tempIter.hasNext()){
						String symbol = tempIter.next();
						list.add(symbol);
						//System.out.println(temp.getUnterminatingString()+" "+newUnterminatingStr+" "+symbol);
						tempIter.remove();
					}
					
					newUnterminatingSymbol.putNewTermToRightList(list);
					temp.rightList.get(i).getRightSymbolList().add(newUnterminatingStr);
					
				}
				for(int i = 1; i < temp.rightList.size(); i++)
					temp.rightList.remove(i);
				tempMap.put(newUnterminatingStr, newUnterminatingSymbol);
			}

		}
		symbolMap.putAll(tempMap);
	}
	public void displayAfterElimination() {
		Iterator<Map.Entry<String, Symbol>> it = symbolMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Symbol> entry = it.next();
			//System.out.println(entry.getKey());
		    Symbol temp = entry.getValue();
		   // System.out.println(temp.getUnterminatingString()+" "+temp.hasEpsilon);
		    Iterator<RightProduction> iter = temp.rightList.iterator();
		    while(iter.hasNext()){
		    	Iterator<String> iterator = iter.next().getRightSymbolList().iterator();
		    	System.out.print(entry.getValue().getUnterminatingString()+" ::= ");
		    	while(iterator.hasNext()){
		    		String str = iterator.next();
		    		System.out.print(str+" ");
		    	}
		    	System.out.println();
		    }
		}
		
	}
}
