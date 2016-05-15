package team.weird.texteditor.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;

import team.weird.texteditor.parser.Symbol.RightProduction;

public class PredictAnalyticalTable {
	HashMap<String, Symbol> UntermSymbolMap;
	Set<String> TermSymbolSet;
	
	public PredictAnalyticalTable(HashMap<String, Symbol> UntermSymbolMap,
			HashSet<String> TermSymbolSet) {
		this.UntermSymbolMap = UntermSymbolMap;
		this.TermSymbolSet = TermSymbolSet;
		TermSymbolSet.add("$");
		initialPredictAnalyticalTable();
	}

	public void createPredictAnalyticalTable() throws OverlappedSyntaxException{
		Iterator<Entry<String, Symbol>> symIter = UntermSymbolMap.entrySet().iterator();
		while(symIter.hasNext()){
			
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> proIter = temp.rightList.iterator();
			while(proIter.hasNext()){
				RightProduction proTemp = proIter.next();
				String firstRightSymbol = proTemp.getFirstRightSymbol();
				if ((UntermSymbolMap.containsKey(firstRightSymbol) && 
						UntermSymbolMap.get(firstRightSymbol).firstSet.contains("empty")) || 
						firstRightSymbol.equals("empty")){
					Iterator<String> followSymbolIter = temp.followSet.iterator();
					while(followSymbolIter.hasNext()){
						String key = followSymbolIter.next();
//						if(temp.predictiveMap.get(key) != null)
//							throw new OverlappedSyntaxException();
						temp.predictiveMap.put(key, proTemp.getRightSymbolList());
					}
				}	
				else {
					Iterator<String> selectSymbolIter = temp.selectSet.iterator();
					while(selectSymbolIter.hasNext()){
						String key = selectSymbolIter.next();
//						if(temp.predictiveMap.get(key) != null)
//							throw new OverlappedSyntaxException();
						temp.predictiveMap.put(key, proTemp.getRightSymbolList());
						//System.out.println(temp.getUnterminatingString()+": "+proTemp.getRightSymbolList());
					}
				}
				
			}
		}
	}

	public void initialPredictAnalyticalTable() {
		Iterator<Entry<String, Symbol>> symIter = UntermSymbolMap.entrySet()
				.iterator();
		while (symIter.hasNext()) {
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<String> termSet = TermSymbolSet.iterator();
			while (termSet.hasNext()) {
				temp.predictiveMap.put(termSet.next(), null);
			}
		}
	}
	
	public void displayPredictiveTable(){
		Iterator<String> termSet = TermSymbolSet.iterator();	
		System.out.print("          ");
		while(termSet.hasNext()){
			System.out.print(termSet.next()+"       ");
		}
		System.out.println();
		Iterator<Entry<String, Symbol>> symIter = UntermSymbolMap.entrySet()
				.iterator();
		while (symIter.hasNext()) {
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			System.out.print(temp.getUnterminatingString());
			Iterator<Entry<String, LinkedList<String>>> preIter = temp.predictiveMap.entrySet().iterator();
			while(preIter.hasNext()){
				//Iterator<String> termSetIter = TermSymbolSet.iterator();
				Entry<String, LinkedList<String>> preEntry = preIter.next();
				if(preEntry.getValue() != null){
					for(String str : preEntry.getValue())
						System.out.print(str);
					System.out.print("   ");
				}
				else
					System.out.print("     ~     ");
				
			}
			System.out.println();
		}
	}
}

class OverlappedSyntaxException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OverlappedSyntaxException() {

	}

	public OverlappedSyntaxException(String str) {
		super(str);
	}
}
