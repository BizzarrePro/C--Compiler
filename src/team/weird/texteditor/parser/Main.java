package team.weird.texteditor.parser;

public class Main {
	public static void main(String[] args) throws OverlappedSyntaxException{
		FirstSet firstSet = new FirstSet();
		firstSet.createFirstSet();
		firstSet.display();
		FollowSet followSet = new FollowSet(firstSet.symbolMap);
		followSet.createFollowSet();
		followSet.display();
		SelectSet selectSet = new SelectSet(followSet.symbolMap,
				firstSet.TerminatingSymbolTable);
		selectSet.createSelectSet();
		PredictAnalyticalTable predict = new PredictAnalyticalTable(
				selectSet.symbolMap, firstSet.TerminatingSymbolTable);
		predict.initialPredictAnalyticalTable();
		predict.createPredictAnalyticalTable();
		predict.displayPredictiveTable();
	}
}
