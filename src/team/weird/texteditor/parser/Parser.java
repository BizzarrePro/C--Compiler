package team.weird.texteditor.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team.weird.texteditor.parser.Symbol.RightProduction;


class Symbol {
	private String unteminatedSymbol = null;
	public ArrayList<RightProduction> rightList = new ArrayList<RightProduction>();

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

	public void putNewTermToRightList(LinkedList<String> betaList) {
		this.rightList.add(new RightProduction(betaList));
	}

	public void replaceRightList(LinkedList<String>[] betaList, int count) {
		rightList.clear();
		for (int i = 0; i < count; i++)
			rightList.add(new RightProduction(betaList[i]));
	}
}

public class Parser {
	private HashMap<String, Symbol> UnterminatingSymbolTable = new HashMap<String, Symbol>();

	public Parser() {
		extractSymbolFromFile();
		eliminateImmediateLeftRecursion();
		displayAfterElimination();
	}

	private void displayAfterElimination() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Symbol> entry = it.next();
			//System.out.println(entry.getKey());
		    Symbol temp = entry.getValue();
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
//		    while(iter.hasNext()){
//		    	Iterator<String> iterator = iter.next().getRightSymbolList().iterator();
//		    	System.out.print(entry.getValue().getUnterminatingString()+" ::= ");
//		    	while(iterator.hasNext()){
//		    		String str = iterator.next();
//		    		System.out.print(str+" ");
//		    		}
//		    }	
		}
	
		    
		
	}

	public void extractSymbolFromFile() {
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(new File("production.txt"));
			br = new BufferedReader(fr);
			Pattern pattern = Pattern.compile("(\\-|\\w)+(?=\\:)");
			String line = br.readLine();
			while (line != null) {
				Matcher match = pattern.matcher(line);
				int index;
				if (match.find()) {
					String temp = match.group(0);
					UnterminatingSymbolTable.put(temp, new Symbol(temp));
					index = match.end(0);
					String[] child = line.substring(index + 1).split("\\|");
					for (int i = 0; i < child.length; i++)
						UnterminatingSymbolTable.get(temp).putToList(child[i]);
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

	public void eliminateImmediateLeftRecursion() {
		Iterator<Map.Entry<String, Symbol>> it = UnterminatingSymbolTable
				.entrySet().iterator();
		Map<String, Symbol> tempMap = new HashMap<String, Symbol>();
		int cnt = 0;
		while (it.hasNext()) {
			//System.out.println((cnt++)+" "+UnterminatingSymbolTable.size() );
			Map.Entry<String, Symbol> entry = it.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> iter = temp.rightList.iterator();
			boolean hasLeftRecursion = false;
			while (iter.hasNext()) {
				RightProduction rightFirst = iter.next();
				if (rightFirst.getFirstRightSymbol().equals(
						temp.getUnterminatingString())) {
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
}
