package team.weird.compiler.cminus.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import team.weird.compiler.cminus.parser.Symbol.RightProduction;

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
			if (!temp.hasRecursion){
				temp.hasRecursion = true;
				disposeOfFollowSet(temp.followSet,
						temp.getUnterminatingString());
			}
		}
		symbolMap.get("expression-sub").followSet.addAll(symbolMap.get("expression").followSet);
		symbolMap.get("expression-sub-sub").followSet.addAll(symbolMap.get("expression-sub").followSet);
	}

	public void disposeOfFollowSet(Set<String> followSet, String symbol) {
		Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet()
				.iterator();
		while (symIter.hasNext()) {
			Entry<String, Symbol> entry = symIter.next();
			Symbol temp = entry.getValue();
			Iterator<RightProduction> proIter = temp.rightList.iterator();
			while (proIter.hasNext()) {
				LinkedList<String> list = proIter.next().getRightSymbolList();
				Iterator<String> listIter = list.iterator();
				String tempSym = null;

				while (listIter.hasNext()) {
					tempSym = listIter.next();
					
					if (symbolMap.containsKey(tempSym) && tempSym.equals(symbol)) {
						if (listIter.hasNext()) {
							String followSymbol = listIter.next();
							if (symbolMap.containsKey(followSymbol)) {
								if (symbolMap.get(followSymbol).hasEpsilon) {
									Symbol elementSym = symbolMap
											.get(followSymbol);
									followSet.addAll(elementSym.firstSet);
									if (symbolMap.get(followSymbol).hasRecursion)
										followSet.addAll(elementSym.followSet);
									else {
										elementSym.hasRecursion = true;
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
								temp.hasRecursion = true;
								disposeOfFollowSet(temp.followSet,
										temp.getUnterminatingString());
								followSet.addAll(temp.followSet);
							} else {
								followSet.addAll(temp.followSet);
							}
						} 
					}
				}
			}
		}
		cnt = 1;
	}
	public void print() {
		FileWriter fw = null;
		File fol = new File("./compile/temp.fol");
		System.out.println();
		System.out
				.println("-------------------------Follow Set-------------------------");
		System.out.println();
		try{
			fw = new FileWriter(fol);
			fw.write(" --------------------------------------------------\r\n");
			fw.write("|               Compute Follow Set                 |\r\n");
			fw.write(" --------------------------------------------------\r\n");
			Iterator<Entry<String, Symbol>> symIter = symbolMap.entrySet()
					.iterator();
			while (symIter.hasNext()) {
				Entry<String, Symbol> entry = symIter.next();
				Symbol temp = entry.getValue();
				Iterator<String> setIter = temp.followSet.iterator();
				System.out.print(temp.getUnterminatingString() + " Size: "
						+ temp.followSet.size() + " $$ {");
				fw.write(temp.getUnterminatingString() + " Size: "
						+ temp.followSet.size() + " ^^ {");
				while (setIter.hasNext()) {
					String print = setIter.next();
					System.out.print(print);
					fw.write(print);
					if (setIter.hasNext()){
						System.out.print(" ");
						fw.write(" ");
					}
				}
				fw.write("}\r\n");
				System.out.print("}");
				System.out.println();
			}
		} catch (IOException e){
			e.printStackTrace();
			try {
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}