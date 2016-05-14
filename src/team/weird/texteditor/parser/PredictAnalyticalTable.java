package team.weird.texteditor.parser;

import java.util.HashMap;

public class PredictAnalyticalTable {
	HashMap<String, Symbol> UntermSymbolMap;
	HashMap<String, Symbol> TermSymbolMap;
	
	public PredictAnalyticalTable(HashMap<String, Symbol> UntermSymbolMap,
			HashMap<String, Symbol> TermSymbolMap) {
		this.UntermSymbolMap = UntermSymbolMap;
		this.TermSymbolMap = TermSymbolMap;
	}

	public void createPredictAnalyticalTable() {
		
	}
}
