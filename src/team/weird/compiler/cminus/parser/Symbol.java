
package team.weird.compiler.cminus.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Symbol {
	private String unteminatedSymbol = null;
	public ArrayList<RightProduction> rightList = new ArrayList<RightProduction>();
	public Set<String> firstSet = new HashSet<String>();
	public Set<String> followSet = new HashSet<String>();
	public Set<String> selectSet = new HashSet<String>();
	private LinkedList<Symbol> reversedList = new LinkedList<Symbol>();
	private LinkedList<Symbol> previousList = new LinkedList<Symbol>();
	public Map<String, Integer> predictiveMapHasEpsilon = new HashMap<String, Integer>();
	public Map<String, LinkedList<String>> predictiveMap = new HashMap<String, LinkedList<String>>();
	public boolean preFlag = false;
	public boolean revFlag = false;
	public boolean hasEpsilon = false;
	public boolean hasRecursion = false;
	public int classification = 0;

	class RightProduction {
		private LinkedList<String> SymbolList;

		public RightProduction(LinkedList<String> SymbolList) {
			this.SymbolList = SymbolList;
		}

		public LinkedList<String> getRightSymbolList() {
			return SymbolList;
		}
		
		public String getSymbolByIndex(int index){
			if(index < SymbolList.size())
				return SymbolList.get(index);
			return null;
		}
		public String getFirstRightSymbol() {
			if(SymbolList.size() > 0)
				return SymbolList.get(0);
			return null;
		}
		public void putToList(String str){
			SymbolList.add(str);
		}
	}

	public Symbol(String unteminatedSymbol) {
		this.unteminatedSymbol = unteminatedSymbol;
	}

	public void putToList(String entry, HashMap<String, Symbol> untermMap) {
		LinkedList<String> SymbolList = new LinkedList<String>();
		String[] symbol = entry.split(" ");
		for (String a : symbol) 
			SymbolList.add(a);
		rightList.add(new RightProduction(SymbolList));
	}

	public String getUnterminatingString() {
		return unteminatedSymbol;
	}

	// public int getClassification(){
	// return classification;
	// }
	public void putNewTermToRightList(LinkedList<String> betaList) {
		this.rightList.add(new RightProduction(betaList));
	}

	public void replaceRightList(LinkedList<String>[] betaList, int count) {
		rightList.clear();
		for (int i = 0; i < count; i++)
			rightList.add(new RightProduction(betaList[i]));
	}

	public void putToReversedList(Symbol symbol) {
		reversedList.add(symbol);
	}

	public void putToPreviousList(Symbol symbol) {
		previousList.add(symbol);
	}

	public LinkedList<Symbol> getPreList() {
		return previousList;
	}

	public LinkedList<Symbol> getRevList() {
		return reversedList;
	}

	public boolean hasThisSymbolInReversedList(Symbol symbol) {
		if (reversedList.contains(symbol))
			return true;
		else
			return false;
	}

	public boolean hasThisSymbolInPreviousList(Symbol symbol) {
		if (previousList.contains(symbol))
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		return unteminatedSymbol.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Symbol other = (Symbol) o;
		if (this.unteminatedSymbol.equals(other.unteminatedSymbol))
			return true;
		return false;
	}

}
