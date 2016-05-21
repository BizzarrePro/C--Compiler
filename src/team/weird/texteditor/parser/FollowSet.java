package team.weird.texteditor.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Set;

import pers.siyuan.compilers.paser.Symbol.RightProduction;

public class FollowSet {
	public HashMap<String, Symbol> symbolMap;
	public static int cnt1 = 0;
	public static int cnt2 = 0;

	public FollowSet(HashMap<String, Symbol> symbolMap) {
		this.symbolMap = symbolMap;
	}

	public int cnt = 1;

	public void createFollowSet() {
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet()
				.iterator();
		while (symIter.hasNext()) {
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			temp.followSet.add("$");
			if (!temp.hasRecursion)
				disposeOfFollowSet(temp.followSet,
						temp.getUnterminatingString());
		}
	}

	public void disposeOfFollowSet(Set<String> followSet, String symbol) {
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet()
				.iterator();
		symbolMap.get(symbol).hasRecursion = true;
		while (symIter.hasNext()) {

			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> proIter = temp.rightList.iterator();
			while (proIter.hasNext()) {

				LinkedList<String> list = proIter.next().getRightSymbolList();
				Iterator<String> listIter = list.iterator();
				String tempSym;

				while (listIter.hasNext()) {
					tempSym = listIter.next();
					
					if (symbolMap.containsKey(tempSym)
							&& tempSym.equals(symbol)) {
						if (listIter.hasNext()) {
							
							String followSymbol = listIter.next();
							// System.out.println(followSymbol);
							if (symbolMap.containsKey(followSymbol)) {
								if (symbolMap.get(followSymbol).hasEpsilon) {
									Symbol elementSym = symbolMap
											.get(followSymbol);
									followSet.addAll(elementSym.firstSet);
									if (symbolMap.get(followSymbol).hasRecursion)
										followSet.addAll(elementSym.followSet);
									else {
										disposeOfFollowSet(
												elementSym.followSet,
												followSymbol);
										followSet.addAll(elementSym.followSet);
									}
									followSet.remove("empty");
								} else {
									followSet.addAll(symbolMap
											.get(followSymbol).firstSet);
									followSet.remove("empty");
								}

							} else if (!symbolMap.containsKey(followSymbol)
									&& !followSymbol.equals("empty"))
								followSet.add(followSymbol);
						} else if (!temp.getUnterminatingString().equals(
								tempSym)){
							if(!temp.hasRecursion){
								disposeOfFollowSet(temp.followSet,
										temp.getUnterminatingString());
								followSet.addAll(temp.followSet);
							} else {
								followSet.addAll(temp.followSet);
							}
						} 
//						else if (temp.getUnterminatingString().equals(
//								tempSym))
//								followSet.addAll(temp.firstSet);
					}
				}
			}
		}
		cnt = 1;
	}

	public void display() {
		System.out.println();
		System.out
				.println("-------------------------Follow Set-------------------------");
		System.out.println();
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet()
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
}
/*
 * left equals right 
 */
// if(symbolMap.get(symbol).hasEpsilon){
// Iterator<RightProduction> productionIter =
// symbolMap.get(symbol).rightList
// .iterator();
// while (productionIter.hasNext()) {
// LinkedList<String> list = productionIter.next()
// .getRightSymbolList();
// String key = list.get(list.size() - 1);
// if (!symbolMap.containsKey(key))
// ;
// else {
// if (symbolMap.get(key).hasRecursion)
// followSet.addAll(symbolMap.get(key).followSet);
// else {
// disposeOfFollowSet(symbolMap.get(key).followSet, symbolMap
// .get(key).getUnterminatingString());
// followSet.addAll(symbolMap.get(key).followSet);
// }
// }
// }


// if (symbolMap.containsKey(followSymbol)
// && symbolMap.get(followSymbol).hasEpsilon
// && symbolMap.get(followSymbol).hasRecursion) {
// Symbol elementSym = symbolMap.get(followSymbol);
// // System.out.println("'"+(cnt2++)+"'");
// disposeOfFollowSet(elementSym.followSet,
// followSymbol);
// followSet.addAll(elementSym.followSet);
// followSet.addAll(elementSym.firstSet);
// followSet.remove("empty");
// } else if (symbolMap.containsKey(followSymbol)) {
// followSet
// .addAll(symbolMap.get(followSymbol).firstSet);
//
// if(symbolMap.get(followSymbol).hasEpsilon &&
// symbolMap.get(followSymbol).hasRecursion)
// followSet
// .addAll(symbolMap.get(followSymbol).followSet);
// else if(symbolMap.get(followSymbol).hasEpsilon &&
// !symbolMap.get(followSymbol).hasRecursion){
// disposeOfFollowSet(symbolMap.get(followSymbol).followSet,
// followSymbol);
// followSet
// .addAll(symbolMap.get(followSymbol).followSet);
// }
// followSet.remove("empty");