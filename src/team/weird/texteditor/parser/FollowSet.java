package team.weird.texteditor.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import team.weird.texteditor.parser.Symbol.RightProduction;

public class FollowSet {
	HashMap<String, Symbol> symbolMap;
	public FollowSet(HashMap<String, Symbol> symbolMap){
		this.symbolMap = symbolMap;
	}
	public int cnt = 1;
	public void createFollowSet(){
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet().iterator();
		while(symIter.hasNext()){
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			temp.followSet.add("$");
			if(temp.followSet.size() >= 1)
				disposeOfFollowSet(temp.followSet, temp.getUnterminatingString());
		}
	}
	public void disposeOfFollowSet(Set<String> followSet, String symbol){
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet().iterator();
		while(symIter.hasNext()){
			
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> proIter = temp.rightList.iterator();
			while(proIter.hasNext()){
				
				LinkedList<String> list = proIter.next().getRightSymbolList();
				Iterator<String> listIter = list.iterator();
				String tempSym ;
				while(listIter.hasNext()){
					tempSym = listIter.next();
					
					if(symbolMap.containsKey(tempSym) && tempSym.equals(symbol)){
						//System.out.println("Left: "+temp.getUnterminatingString()+" Right: "+tempSym + "Symbol: "+symbol);
						if(listIter.hasNext()){
							String followSymbol = listIter.next();
							if(symbolMap.containsKey(followSymbol)){
								Symbol elementSym = symbolMap.get(followSymbol);
								disposeOfFollowSet(symbolMap.get(followSymbol).followSet, followSymbol);
								followSet.addAll(symbolMap.get(followSymbol).followSet);
								followSet.addAll(elementSym.firstSet);
								followSet.remove("empty");
							}
							else
								followSet.add(followSymbol);
						}
						else if(!temp.getUnterminatingString().equals(tempSym) && temp.followSet.size() <= 1){
							disposeOfFollowSet(temp.followSet, temp.getUnterminatingString());
							followSet.addAll(temp.followSet);
						}
						else
							followSet.addAll(temp.followSet);
					}
				}
			}
		}
		cnt = 1;
	}
	
	public void display(){
		System.out.println("-------------------------Follow Set-------------------------");
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet().iterator();
		while(symIter.hasNext()){
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<String> setIter = temp.followSet.iterator();
			System.out.print(temp.getUnterminatingString()+" Size: "+temp.followSet.size()+" $$ {");
			while(setIter.hasNext()){
				String print = setIter.next();
				System.out.print(print);
				if(setIter.hasNext())
					System.out.print(" ");
			}
			System.out.print("}");
			System.out.println();
		}
	}
}
