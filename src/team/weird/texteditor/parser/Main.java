package team.weird.texteditor.parser;

import team.weird.texteditor.lexer.Token;

public class Main {
	public static void main(String[] args) throws OverlappedSyntaxException,
			SyntacticErrorException {
		EliminationOfLeftRecursion removeLeftRecursion = new EliminationOfLeftRecursion();
		FirstSet firstSet = new FirstSet(removeLeftRecursion.getSymbolMap());
		firstSet.createFirstSet();
		//firstSet.display();
		FollowSet followSet = new FollowSet(firstSet.symbolMap);
		followSet.createFollowSet();
		//followSet.display();
		SelectSet selectSet = new SelectSet(followSet.symbolMap,
				ExtractProduction.TerminatingSymbolTable);
		selectSet.createSelectSet();
		PredictiveAnalytics analysic = new PredictiveAnalytics(
				selectSet.symbolMap, ExtractProduction.TerminatingSymbolTable,
				EliminationOfLeftRecursion.entrance);
		analysic.displayPredictiveTable();
		Token[] token = new Token[5];
		token[0] = new Token("int", "TYPE");
		token[1] = new Token("ID", "TYPE");
		token[2] = new Token("=", "IDENTIFY");
		token[3] = new Token("NUM", "TYPE");
		token[4] = new Token(";", "TYPE");
//		token[5] = new Token(")", "BORDER");
//		token[6] = new Token(";", "BORDER");
		analysic.PredictAndAnalyze(token, 1);
	}
}
