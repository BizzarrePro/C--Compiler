package team.weird.texteditor.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map.Entry;

import team.weird.texteditor.parser.Symbol.RightProduction;

public class PredictAnalyticalTable {
	public HashMap<String, Symbol> UntermSymbolMap;
	public Set<String> TermSymbolSet;
	public static final int BLANKWIDTH = 9;

	public PredictAnalyticalTable(HashMap<String, Symbol> UntermSymbolMap,
			HashSet<String> TermSymbolSet) throws OverlappedSyntaxException {
		this.UntermSymbolMap = UntermSymbolMap;
		this.TermSymbolSet = TermSymbolSet;
		TermSymbolSet.add("$");
		TermSymbolSet.remove("empty");
		initialPredictAnalyticalTable();
		createPredictAnalyticalTable();
	}

	public void createPredictAnalyticalTable() throws OverlappedSyntaxException {
		Iterator<Entry<String, Symbol>> symIter = UntermSymbolMap.entrySet()
				.iterator();
		while (symIter.hasNext()) {

			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> proIter = temp.rightList.iterator();
			
			while (proIter.hasNext()) {
				RightProduction proTemp = proIter.next();
				String firstRightSymbol = proTemp.getFirstRightSymbol();
				if(temp.getUnterminatingString().equals("declaration-list`"))
					System.out.println("++++++++"+proTemp.getRightSymbolList());
				if ((UntermSymbolMap.containsKey(firstRightSymbol) && UntermSymbolMap
						.get(firstRightSymbol).firstSet.contains("empty"))) {
					Iterator<String> followSymbolIter = temp.followSet
							.iterator();
					while (followSymbolIter.hasNext()) {
						String key = followSymbolIter.next();
						if(temp.predictiveMap.get(key) == null || temp.predictiveMap.get(key).equals("empty"))
						temp.predictiveMap.put(key,
							proTemp.getRightSymbolList());
					}

				} else if (UntermSymbolMap.containsKey(firstRightSymbol)) {
					Iterator<String> selectSymbolIter = UntermSymbolMap
							.get(firstRightSymbol).selectSet.iterator();
					while (selectSymbolIter.hasNext()) {
						String key = selectSymbolIter.next();
						// if(temp.predictiveMap.get(key) != null)
						// throw new OverlappedSyntaxException();
						if(temp.predictiveMap.get(key) == null || temp.predictiveMap.get(key).equals("empty"))
						temp.predictiveMap.put(key,
								proTemp.getRightSymbolList());

					}
				} else if (firstRightSymbol.equals("empty")) {
					Iterator<String> followIter = temp.followSet.iterator();
					while (followIter.hasNext()) {
						String str = followIter.next();
						if(temp.predictiveMap.get(str) == null || temp.predictiveMap.get(str).equals("empty"))
							temp.predictiveMap.put(str,
									proTemp.getRightSymbolList());

					}
					
				} else{
						temp.predictiveMap.put(firstRightSymbol,
							proTemp.getRightSymbolList());
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
				String key = termSet.next();
				temp.predictiveMap.put(key, null);
			}
		}
	}

	public void displayPredictiveTable() {
		System.out.println();
		System.out
				.println("----------------------Predictive Table----------------------");
		System.out.println();
		Iterator<Entry<String, Symbol>> symIter = UntermSymbolMap.entrySet()
				.iterator();
		Iterator<Entry<String, Symbol>> termIter = UntermSymbolMap.entrySet()
				.iterator();
		Symbol sym = termIter.next().getValue();
		Iterator<String> setIter = sym.predictiveMap.keySet().iterator();
		for (int i = 0; i < 16; i++)
			System.out.print(" ");
		while (setIter.hasNext()) {
			String termSymbol = setIter.next();
			System.out.print(termSymbol);
			if (termSymbol.length() < BLANKWIDTH)
				for (int i = 0; i < BLANKWIDTH - termSymbol.length(); i++)
					System.out.print(" ");
		}
		System.out.println();
		while (symIter.hasNext()) {
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			String blankStr = temp.getUnterminatingString();
			System.out.print(blankStr + ":");
			if (blankStr.length() < 15)
				for (int i = 0; i < 15 - blankStr.length(); i++)
					System.out.print(" ");
			Iterator<Entry<String, LinkedList<String>>> preIter = temp.predictiveMap
					.entrySet().iterator();
			while (preIter.hasNext()) {
				// Iterator<String> termSetIter = TermSymbolSet.iterator();
				Entry<String, LinkedList<String>> preEntry = preIter.next();
				if (preEntry.getValue() != null) {
					int blankCnt = 0;
					for (String str : preEntry.getValue()) {
						System.out.print(str);
						blankCnt += str.length();
					}
					if (blankCnt < BLANKWIDTH)
						for (int i = 0; i < BLANKWIDTH - blankCnt; i++)
							System.out.print(" ");
				} else
					System.out.print("error    ");

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
