package team.weird.texteditor.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import team.weird.texteditor.parser.Symbol.RightProduction;

public class EliminationOfLeftRecursion extends ExtractProduction {

	private static int count = 1;

	public EliminationOfLeftRecursion() {
		super();
		markEpsilonEntry();
		findIndirectLeftRecursion();
		eliminateImmediateLeftRecursion();
		markEpsilonEntry();
		//reduceIndirectLeftRecursionToImmediateLeftRecursion();
		createTerminatingSymbolTable();

		displayAfterElimination();
		// displayBeforeDepthFirstOrder();
	}

	private void reduceIndirectLeftRecursionToImmediateLeftRecursion() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable
				.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, Symbol> entry = it.next();
			Symbol temp = entry.getValue();
			if (!temp.revFlag) {
				depthFirstOrder(temp);
			}
		}
		while (!reversedStack.isEmpty()) {
			Symbol temp = reversedStack.pop();
			if (!temp.preFlag) {
				depthFirstOrderPreviously(temp);
			}
			count++;
		}
		Iterator<Map.Entry<String, Symbol>> iter = UnterminatingSymbolTable
				.entrySet().iterator();
		int[] array = new int[UnterminatingSymbolTable.size()];
		int count = 0;
		while(iter.hasNext()){
			array[count++] = iter.next().getValue().classification;
		}
		Arrays.sort(array);
		for(int a : array)
			System.out.println(a);
	}

	private void depthFirstOrder(Symbol temp) {
		temp.revFlag = true;
		for (int i = 0; i < temp.getRevList().size(); i++)
			if (!temp.getRevList().get(i).revFlag)
				depthFirstOrder(temp.getRevList().get(i));
		reversedStack.push(temp);
	}

	private void depthFirstOrderPreviously(Symbol temp) {
		temp.preFlag = true;
		temp.classification = count;
		for (int i = 0; i < temp.getPreList().size(); i++)
			if (!temp.getPreList().get(i).preFlag)
				depthFirstOrderPreviously(temp.getPreList().get(i));
	}

	private void displayBeforeDepthFirstOrder() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable
				.entrySet().iterator();
		System.out
				.println("-------------------------Previous List-------------------------");
		while (it.hasNext()) {
			Map.Entry<String, Symbol> entry = it.next();
			Symbol temp = entry.getValue();
			Iterator<Symbol> iterPre = temp.getPreList().iterator();
			if (temp.getPreList().size() > 0)
				System.out.print("<" + temp.getUnterminatingString() + "> ::");
			while (iterPre.hasNext()) {
				System.out.print(iterPre.next().getUnterminatingString() + " ");
			}
			if (temp.getPreList().size() > 0)
				System.out.println();
		}
		System.out
				.println("+++++++++++++++++++++++++Reversed List+++++++++++++++++++++++++");
		it = UnterminatingSymbolTable.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Symbol> entry = it.next();
			Symbol temp = entry.getValue();
			Iterator<Symbol> iterRev = temp.getRevList().iterator();
			if (temp.getRevList().size() > 0)
				System.out.print("<" + temp.getUnterminatingString() + "> ::");
			while (iterRev.hasNext()) {
				System.out.print(iterRev.next().getUnterminatingString() + " ");
			}
			if (temp.getRevList().size() > 0)
				System.out.println();
		}
		it = UnterminatingSymbolTable.entrySet().iterator();
		int[] classific = new int[UnterminatingSymbolTable.size()];
		int index = 0;
		while (it.hasNext()) {
			Map.Entry<String, Symbol> entry = it.next();
			classific[index] = entry.getValue().classification;
			index++;
			if (entry.getValue().classification != 0)
				System.out.println("Entry: "
						+ entry.getValue().getUnterminatingString() + " ID: "
						+ entry.getValue().classification);
		}
		// Arrays.sort(classific);
		for (int a : classific)
			System.out.println(a);
		// For debugging
		// System.out.println(reversedStack.size());
		// Iterator<Symbol> stackIter = reversedStack.iterator();
		// while(stackIter.hasNext()){
		// System.out.println(stackIter.next().getUnterminatingString());
		// }
	}

	public void displayAfterElimination() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable.entrySet().iterator();
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

	private void markEpsilonEntry() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable
				.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Symbol> entry = it.next();
			// System.out.println(entry.getKey());
			Symbol temp = entry.getValue();
			Iterator<RightProduction> iter = temp.rightList.iterator();
			while (iter.hasNext()) {
				if (iter.next().getRightSymbolList().getFirst().equals("empty"))
					temp.hasEpsilon = true;
			}
		}

	}

	public void findIndirectLeftRecursion() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable
				.entrySet().iterator();
		// Map<String, Symbol> tempMap = new HashMap<String, Symbol>();
		while (it.hasNext()) {
			Map.Entry<String, Symbol> entry = it.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> iter = temp.rightList.iterator();
			while (iter.hasNext()) {
				RightProduction rightFirst = iter.next();
				String rightFirstStr = rightFirst.getFirstRightSymbol();
				if (UnterminatingSymbolTable.containsKey(rightFirst
						.getFirstRightSymbol())
						&& !rightFirstStr.equals(temp.getUnterminatingString())) {
					Symbol adjacentNode = UnterminatingSymbolTable
							.get(rightFirstStr);
					if (!temp.hasThisSymbolInPreviousList(adjacentNode))
						temp.putToPreviousList(adjacentNode);
					if (!adjacentNode.hasThisSymbolInReversedList(temp))
						adjacentNode.putToReversedList(temp);
				}

			}
		}
	}

	// Unfinished
	public void eliminateIndirectLeftRecursion() {
		Iterator<Map.Entry<String, Symbol>> outerLevelIter = UnterminatingSymbolTable
				.entrySet().iterator();
		Iterator<Map.Entry<String, Symbol>> innerLevelIter = UnterminatingSymbolTable
				.entrySet().iterator();
		LinkedList<Symbol> tempList = new LinkedList<Symbol>();
		while (outerLevelIter.hasNext()) {
			Map.Entry<String, Symbol> hostEntry = outerLevelIter.next();
			Symbol hostTemp = hostEntry.getValue();
			tempList.add(hostTemp);
			while (innerLevelIter.hasNext()) {
				Map.Entry<String, Symbol> entry = innerLevelIter.next();
				Symbol temp = entry.getValue();
				if (temp != hostTemp
						&& temp.classification == hostTemp.classification)
					tempList.add(temp);
			}
			if (tempList.size() > 1) {
				Symbol temp = tempList.remove();
				while (tempList.size() != 1) {
					Iterator<Symbol> listIter = tempList.iterator();
					while (listIter.hasNext()) {
						Symbol listElement = listIter.next();
						Iterator<RightProduction> rightIter = temp.rightList
								.iterator();
						while (rightIter.hasNext()) {
							LinkedList<String> ref = rightIter.next()
									.getRightSymbolList();
							String rem = temp.getUnterminatingString();
							if (ref.contains(rem)) {
								int index = ref.indexOf(rem);
								// ref.add(index, )
							}
							// ref.remove(temp.getUnterminatingString());
							// ref.addAll(temp.rightList.)

						}
					}
					temp = tempList.remove();
				}
			} else
				tempList.clear();
		}
	}

	public void eliminateImmediateLeftRecursion() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable
				.entrySet().iterator();
		Map<String, Symbol> tempMap = new HashMap<String, Symbol>();
		while (it.hasNext()) {
			// System.out.println((cnt++)+" "+UnterminatingSymbolTable.size() );
			Map.Entry<String, Symbol> entry = it.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> iter = temp.rightList.iterator();
			boolean hasLeftRecursion = false;
			while (iter.hasNext()) {
				RightProduction rightFirst = iter.next();
				String rightFirstStr = rightFirst.getFirstRightSymbol();
				if (rightFirstStr.equals(temp.getUnterminatingString())) {
					hasLeftRecursion = true;
					break;
				}
			}
			if (hasLeftRecursion) {
				iter = temp.rightList.iterator();
				String newUnterminatingStr = temp.getUnterminatingString() + '`';
				Symbol newUnterminatingSymbol = new Symbol(newUnterminatingStr);
				// UnterminatingSymbolTable.put(newUnterminatingStr,
				// newUnterminatingSymbol);
				// Map.Entry<String, Symbol> tempEntry =
				// Entry(newUnterminatingStr, newUnterminatingSymbol);
				tempMap.put(newUnterminatingStr, newUnterminatingSymbol);
				@SuppressWarnings("unchecked")
				LinkedList<String>[] betaList = (LinkedList<String>[]) new LinkedList<?>[10];
				for (int i = 0; i < 10; i++)
					betaList[i] = new LinkedList<String>();
				int count = 0;
				while (iter.hasNext()) {
					RightProduction rightFirst = iter.next();
					if (rightFirst.getFirstRightSymbol().equals(
							temp.getUnterminatingString())) {
						LinkedList<String> alphaList = new LinkedList<String>();
						Iterator<String> alphaIter = rightFirst
								.getRightSymbolList().iterator();
						alphaIter.next();
						while (alphaIter.hasNext()) {
							alphaList.add(alphaIter.next());
						}
						alphaList.add(newUnterminatingStr);
						newUnterminatingSymbol.putNewTermToRightList(alphaList);
						LinkedList<String> epsilonList = new LinkedList<String>();
						epsilonList.add("empty");
						// newUnterminatingSymbol.hasEpsilon = true;
						newUnterminatingSymbol
								.putNewTermToRightList(epsilonList);
					} else {
						Iterator<String> betaIter = rightFirst
								.getRightSymbolList().iterator();
						while (betaIter.hasNext()) {
							String beta = betaIter.next();
							if (!beta.equals("empty"))
								betaList[count].add(beta);

						}
						betaList[count].add(newUnterminatingStr);
						count++;
					}
				}
				temp.replaceRightList(betaList, count);
			}

			hasLeftRecursion = false;
		}
		UnterminatingSymbolTable.putAll(tempMap);
		// System.out.println(UnterminatingSymbolTable.size());
	}

	public void createTerminatingSymbolTable() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable
				.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Symbol> entry = it.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> iter = temp.rightList.iterator();
			while (iter.hasNext()) {
				Iterator<String> iterator = iter.next().getRightSymbolList()
						.iterator();
				while (iterator.hasNext()) {
					String str = iterator.next();
					if (!UnterminatingSymbolTable.containsKey(str)
							&& !str.equals("empty"))
						TerminatingSymbolTable.add(str);
				}
			}
		}

	}

	public HashMap<String, Symbol> getSymbolMap() {
		return UnterminatingSymbolTable;
	}
}
