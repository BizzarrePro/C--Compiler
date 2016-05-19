package team.weird.texteditor.parser;

import team.weird.texteditor.lexer.Token;

public class Main {
	public static void main(String[] args) throws OverlappedSyntaxException,
			SyntacticErrorException {
		EliminationOfLeftRecursion removeLeftRecursion = new EliminationOfLeftRecursion();
		FirstSet firstSet = new FirstSet(removeLeftRecursion.getSymbolMap());
		firstSet.createFirstSet();
		firstSet.display();
		FollowSet followSet = new FollowSet(firstSet.symbolMap);
		followSet.createFollowSet();
		followSet.display();
		SelectSet selectSet = new SelectSet(followSet.symbolMap,
				ExtractProduction.TerminatingSymbolTable);
		selectSet.createSelectSet();
		PredictiveAnalytics analysic = new PredictiveAnalytics(
				selectSet.symbolMap, ExtractProduction.TerminatingSymbolTable,
				EliminationOfLeftRecursion.entrance);
		analysic.displayPredictiveTable();
		//Test Case 1
//		Token[] token = new Token[9];
//		token[0] = new Token("int", "TYPE");
//		token[1] = new Token("ID", "TYPE");
//		token[2] = new Token("(", "TYPE");
//		token[3] = new Token("int", "TYPE");
//		token[4] = new Token("ID", "TYPE");
//		token[5] = new Token(",", "TYPE");
//		token[6] = new Token("int", "TYPE");
//		token[7] = new Token("ID", "TYPE");
//		token[8] = new Token(")", "TYPE");


//		int a(int b){
//			if (a == 5)
//				return a;
//		}
		Token[] token = new Token[20];
		token[0] = new Token("int", "TYPE");
		token[1] = new Token("ID", "TYPE");
		token[2] = new Token("(", "IDENTIFY");
		token[3] = new Token("int", "TYPE");
		token[4] = new Token("ID", "TYPE");
		token[5] = new Token(")", "BORDER");
		token[6] = new Token("{", "BORDER");
		token[7] = new Token("int", "TYPE");
		token[8] = new Token("ID", "TYPE");
		token[9] = new Token(";", "TYPE");
		token[10] = new Token("if", "TYPE");
		token[11] = new Token("(", "TYPE");
		token[12] = new Token("ID", "IDENTIFY");
		token[13] = new Token("==", "TYPE");
		token[14] = new Token("NUM", "TYPE");
		token[15] = new Token(")", "BORDER");
		token[16] = new Token("return", "BORDER");
		token[17] = new Token("ID", "BORDER");
		token[18] = new Token(";", "BORDER");
		token[19] = new Token("}", "BORDER");
//		Token[] token = new Token[3];
//		token[0] = new Token("ID", "TYPE");
//		token[1] = new Token("*", "TYPE");
//		token[2] = new Token("ID", "TYPE");
//		token[3] = new Token("*", "TYPE");
//		token[4] = new Token("ID", "TYPE");
		analysic.PredictAndAnalyze(token, 1);
	}
}
//Token[] token = new Token[17];
//token[0] = new Token("int", "TYPE");
//token[1] = new Token("ID", "TYPE");
//token[2] = new Token("(", "IDENTIFY");
//token[3] = new Token("int", "TYPE");
//token[4] = new Token("ID", "TYPE");
//token[5] = new Token(")", "BORDER");
//token[6] = new Token("{", "BORDER");
//token[7] = new Token("if", "TYPE");
//token[8] = new Token("(", "TYPE");
//token[9] = new Token("ID", "IDENTIFY");
//token[10] = new Token("==", "TYPE");
//token[11] = new Token("NUM", "TYPE");
//token[12] = new Token(")", "BORDER");
//token[13] = new Token("return", "BORDER");
//token[14] = new Token("ID", "BORDER");
//token[15] = new Token(";", "BORDER");
//token[16] = new Token("}", "BORDER");
