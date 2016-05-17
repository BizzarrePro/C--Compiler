package team.weird.texteditor.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractProduction {
	public static HashMap<String, Symbol> UnterminatingSymbolTable = new HashMap<String, Symbol>();
	public static HashSet<String> TerminatingSymbolTable =  new HashSet<String>();
	public static String entrance ;
	private boolean hasGotten = false;
	protected Stack<Symbol> reversedStack = new Stack<Symbol>();
	public ExtractProduction(){
		extractSymbolFromFile();
		displayTerminatingSymbol();
		ExtractLeftCommonFactoring removeAmbiguity = new ExtractLeftCommonFactoring(
				UnterminatingSymbolTable);
		removeAmbiguity.excuteExtractionOfCommonFactoring();
	}
	
	private void displayTerminatingSymbol() {
		// TODO Auto-generated method stub
		Iterator<String> it = TerminatingSymbolTable.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
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
					if(!hasGotten){
						entrance = new String(temp);
						hasGotten = true;
					}
					UnterminatingSymbolTable.put(temp, new Symbol(temp));
					index = match.end(0);
					String[] child = line.substring(index + 1).split("\\|");
					for (int i = 0; i < child.length; i++){
						UnterminatingSymbolTable.get(temp).putToList(child[i],UnterminatingSymbolTable);
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

}
