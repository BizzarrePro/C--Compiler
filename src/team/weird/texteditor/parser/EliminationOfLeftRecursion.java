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
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team.weird.texteditor.parser.Symbol.RightProduction;

class Symbol {
	private String unteminatedSymbol = null;
	public ArrayList<RightProduction> rightList = new ArrayList<RightProduction>();
	public Set<String> firstSet = new HashSet<String>();
	private LinkedList<Symbol> reversedList = new LinkedList<Symbol>();
	private LinkedList<Symbol> previousList = new LinkedList<Symbol>();
	public boolean preFlag = false;
	public boolean revFlag = false;
	public boolean hasEpsilon = false;
	public int classification = 0;
	class RightProduction {
		private LinkedList<String> SymbolList;

		public RightProduction(LinkedList<String> SymbolList) {
			this.SymbolList = SymbolList;
		}

		public LinkedList<String> getRightSymbolList() {
			return SymbolList;
		}

		public String getFirstRightSymbol() {
			return SymbolList.get(0);
		}
	}

	public Symbol(String unteminatedSymbol) {
		this.unteminatedSymbol = unteminatedSymbol;
	}
	
	public void putToList(String entry) {
		LinkedList<String> SymbolList = new LinkedList<String>();
		String[] symbol = entry.split(" ");
		for (String a : symbol)
			SymbolList.add(a);
		rightList.add(new RightProduction(SymbolList));
	}

	public String getUnterminatingString() {
		return unteminatedSymbol;
	}
	
//	public int getClassification(){
//		return classification;
//	}
	public void putNewTermToRightList(LinkedList<String> betaList) {
		this.rightList.add(new RightProduction(betaList));
	}

	public void replaceRightList(LinkedList<String>[] betaList, int count) {
		rightList.clear();
		for (int i = 0; i < count; i++)
			rightList.add(new RightProduction(betaList[i]));
	}
	
	public void putToReversedList(Symbol symbol){
		reversedList.add(symbol);
	}
	
	public void putToPreviousList(Symbol symbol){
		previousList.add(symbol);
	}
	
	public LinkedList<Symbol> getPreList(){
		return previousList;
	}
	
	public LinkedList<Symbol> getRevList(){
		return reversedList;
	}
	
	public boolean hasThisSymbolInReversedList(Symbol symbol){
		if(reversedList.contains(symbol))
			return true;
		else
			return false;	
	}
	
	public boolean hasThisSymbolInPreviousList(Symbol symbol){
		if(previousList.contains(symbol))
			return true;
		else
			return false;	
	}
	
	@Override
	public int hashCode(){
		return unteminatedSymbol.hashCode();
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o)	return true;
		if(o == null)	return false;
		if(getClass() != o.getClass())	 return false;
		Symbol other = (Symbol)o;
		if(this.unteminatedSymbol.equals(other.unteminatedSymbol))
			return true;
		return false;
	}
	
}

public class EliminationOfLeftRecursion {
	private HashMap<String, Symbol> UnterminatingSymbolTable = new HashMap<String, Symbol>();
	private Stack<Symbol> reversedStack = new Stack<Symbol>();
	private static int count = 1;
	public EliminationOfLeftRecursion() {
		extractSymbolFromFile();
		findIndirectLeftRecursion();
		eliminateImmediateLeftRecursion();
		markEpsilonEntry();
		reduceIndirectLeftRecursionToImmediateLeftRecursion();
		displayAfterElimination();
		displayBeforeDepthFirstOrder();
	}
	
	private void reduceIndirectLeftRecursionToImmediateLeftRecursion() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String, Symbol> entry = it.next();
			Symbol temp = entry.getValue();
			if(!temp.revFlag){
				depthFirstOrder(temp);
			}
		}
		//System.out.println(reversedStack.size());
		//it = UnterminatingSymbolTable.entrySet().iterator()
		while(!reversedStack.isEmpty()){
			Symbol temp = reversedStack.pop();
			if(!temp.preFlag){
				depthFirstOrderPreviously(temp);	
			}
			count++;
		}
	}
	
	private void depthFirstOrder(Symbol temp) {
		temp.revFlag = true;
		for(int i = 0; i < temp.getRevList().size(); i++)
			if(!temp.getRevList().get(i).revFlag)
				depthFirstOrder(temp.getRevList().get(i));	
		reversedStack.push(temp);
	}
	
	private void depthFirstOrderPreviously(Symbol temp){
		temp.preFlag = true;
		temp.classification = count;
		for(int i = 0; i < temp.getPreList().size(); i++)
			if(!temp.getPreList().get(i).preFlag)
				depthFirstOrderPreviously(temp.getPreList().get(i));	
	}
	private void displayBeforeDepthFirstOrder(){
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable.entrySet().iterator();
		System.out.println("-------------------------Previous List-------------------------");
		while(it.hasNext()){
			Map.Entry<String, Symbol> entry = it.next();
			Symbol temp = entry.getValue();
			Iterator<Symbol> iterPre = temp.getPreList().iterator();
			if(temp.getPreList().size() > 0)
			System.out.print("<"+temp.getUnterminatingString()+"> ::");
			while(iterPre.hasNext()){
				System.out.print(iterPre.next().getUnterminatingString()+" ");
			}
			if(temp.getPreList().size() > 0)
				System.out.println();
		}
		System.out.println("+++++++++++++++++++++++++Reversed List+++++++++++++++++++++++++");
		it = UnterminatingSymbolTable.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Symbol> entry = it.next();
			Symbol temp = entry.getValue();
			Iterator<Symbol> iterRev = temp.getRevList().iterator();
			if(temp.getRevList().size() > 0)
				System.out.print("<"+temp.getUnterminatingString()+"> ::");
			while(iterRev.hasNext()){
				System.out.print(iterRev.next().getUnterminatingString()+" ");
			}
			if(temp.getRevList().size() > 0)
				System.out.println();
		}
		it = UnterminatingSymbolTable.entrySet().iterator();
		int[] classific = new int[UnterminatingSymbolTable.size()];
		int index = 0;
		while(it.hasNext()){
			Map.Entry<String, Symbol> entry = it.next();
			classific[index] = entry.getValue().classification;
			index++;
			if(entry.getValue().classification != 0)
				System.out.println("Entry: "+entry.getValue().getUnterminatingString()+" ID: "+ entry.getValue().classification);
		}
		//Arrays.sort(classific);
		for(int a : classific)
			System.out.println(a);
		//For debugging
//		System.out.println(reversedStack.size());
//		Iterator<Symbol> stackIter = reversedStack.iterator();
//		while(stackIter.hasNext()){
//			System.out.println(stackIter.next().getUnterminatingString());
//		}
	}
	
	private void displayAfterElimination() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Symbol> entry = it.next();
			//System.out.println(entry.getKey());
		    Symbol temp = entry.getValue();
		    System.out.println(temp.getUnterminatingString()+" "+temp.hasEpsilon);
		  /*  Iterator<RightProduction> iter = temp.rightList.iterator();
		    while(iter.hasNext()){
		    	Iterator<String> iterator = iter.next().getRightSymbolList().iterator();
		    	System.out.print(entry.getValue().getUnterminatingString()+" ::= ");
		    	while(iterator.hasNext()){
		    		String str = iterator.next();
		    		System.out.print(str+" ");
		    	}
		    	System.out.println();
		    }*/	
		}
		
	}
	
	private void markEpsilonEntry() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Symbol> entry = it.next();
			//System.out.println(entry.getKey());
		    Symbol temp = entry.getValue();
		    Iterator<RightProduction> iter = temp.rightList.iterator();
		    while(iter.hasNext()){
		    	if(iter.next().getRightSymbolList().getFirst().equals("empty"))
		    		temp.hasEpsilon = true;
		    }	
		}
		
	}
	
	public void extractSymbolFromFile() {
		FileReader fr;
		BufferedReader br;
		try {
			//productionUsed.txt
			fr = new FileReader(new File("productionUsed.txt"));
			br = new BufferedReader(fr);
			Pattern pattern = Pattern.compile("(\\-|\\w)+(?=\\:)");
			//Pattern epsilonPattern = Pattern.compile("(\\ *)^empty$");
			String line = br.readLine();
			while (line != null) {
				Matcher match = pattern.matcher(line);
				int index;
				if (match.find()) {
					String temp = match.group(0);
					UnterminatingSymbolTable.put(temp, new Symbol(temp));
					index = match.end(0);
					String[] child = line.substring(index + 1).split("\\|");
					for (int i = 0; i < child.length; i++){
						UnterminatingSymbolTable.get(temp).putToList(child[i]);
//						Matcher epsilonMatch = epsilonPattern.matcher(child[i]);
//						if(epsilonMatch.matches())
//							UnterminatingSymbolTable.get(temp).hasEpsilon = true;
					}
				}
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void findIndirectLeftRecursion() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable
				.entrySet().iterator();
//		Map<String, Symbol> tempMap = new HashMap<String, Symbol>();
		while (it.hasNext()) {
			Map.Entry<String, Symbol> entry = it.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> iter = temp.rightList.iterator();
			while (iter.hasNext()) {
				RightProduction rightFirst = iter.next();
				String rightFirstStr = rightFirst.getFirstRightSymbol();
				if (UnterminatingSymbolTable.containsKey(rightFirst.getFirstRightSymbol())
						&& !rightFirstStr.equals(temp.getUnterminatingString())) {
					Symbol adjacentNode = UnterminatingSymbolTable.get(rightFirstStr);
					if(!temp.hasThisSymbolInPreviousList(adjacentNode))
						temp.putToPreviousList(adjacentNode);
					if(!adjacentNode.hasThisSymbolInReversedList(temp))
						adjacentNode.putToReversedList(temp);
				}

			}
		}
	}
	
	public void eliminateIndirectLeftRecursion(){
		Iterator<Map.Entry<String, Symbol>> outerLevelIter = UnterminatingSymbolTable
					.entrySet().iterator();
		Iterator<Map.Entry<String, Symbol>> innerLevelIter = UnterminatingSymbolTable
				.entrySet().iterator();
		LinkedList<Symbol> tempList = new LinkedList<Symbol>();
		while(outerLevelIter.hasNext()){
			Map.Entry<String, Symbol> hostEntry = outerLevelIter.next();
			Symbol hostTemp =hostEntry.getValue();
			tempList.add(hostTemp);
			while(innerLevelIter.hasNext()){
				Map.Entry<String, Symbol> entry = innerLevelIter.next();
				Symbol temp = entry.getValue();
				if(temp != hostTemp && temp.classification == hostTemp.classification)
					tempList.add(temp);
			}
			if(tempList.size() > 1){
				Symbol temp = tempList.remove();
				while(tempList.size() != 1){
					Iterator<Symbol> listIter = tempList.iterator();
					while(listIter.hasNext()){
						Symbol listElement = listIter.next();
						Iterator<RightProduction> rightIter = temp.rightList.iterator();
						while(rightIter.hasNext()){
							LinkedList<String> ref = rightIter.next().getRightSymbolList();
							String rem = temp.getUnterminatingString();
							if(ref.contains(rem)){
								int index = ref.indexOf(rem);
								//ref.add(index, )
							}
//							ref.remove(temp.getUnterminatingString());
//							ref.addAll(temp.rightList.)
							
						}
					}
					temp = tempList.remove();
				}
			}
			else
				tempList.clear();
		}
	}
	public void eliminateImmediateLeftRecursion() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable
				.entrySet().iterator();
		Map<String, Symbol> tempMap = new HashMap<String, Symbol>();
		while (it.hasNext()) {
			//System.out.println((cnt++)+" "+UnterminatingSymbolTable.size() );
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
						//newUnterminatingSymbol.hasEpsilon = true;
						newUnterminatingSymbol.putNewTermToRightList(epsilonList);
					} else {
						Iterator<String> betaIter = rightFirst
								.getRightSymbolList().iterator();
						while (betaIter.hasNext()) {
							betaList[count].add(betaIter.next());
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
		//System.out.println(UnterminatingSymbolTable.size());
	}
	
	public HashMap<String, Symbol> getSymbolMap(){
		return UnterminatingSymbolTable;
	}
}
