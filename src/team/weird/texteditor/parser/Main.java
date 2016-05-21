package team.weird.texteditor.parser;

import java.io.IOException;

import pers.siyuan.compilers.lexer.Lexer;
import pers.siyuan.compilers.lexer.Token;

public class Main {
	public static void main(String[] args) throws OverlappedSyntaxException,
			SyntacticErrorException, IOException {
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
		//selectSet.display();
		PredictiveAnalytics analysic = new PredictiveAnalytics(
				selectSet.symbolMap, ExtractProduction.TerminatingSymbolTable,
				EliminationOfLeftRecursion.entrance);
		//analysic.displayPredictiveTable();
		
		//Test Case 1
/**		
  		int a(int b,int c)
 */
		
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


/**		
  		int a(int b){
			if (a == 5)
				return a;
			else
				;
		}
*/
		//Test Case 2 
		//This parser couldn't deal with "dangling else" problem, because it used LL1 syntax
/* 		Token[] token = new Token[22];
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
		token[19] = new Token("else", "BORDER");
		token[20] = new Token(";", "BORDER");
		token[21] = new Token("}", "BORDER");
		*/

/**
		int gcd (int u, int v)
		{ if (v == 0) return u ;
		else return gcd(v,u-u/v*v);
		}
		void main(void)
		{ int x; int y;
		x = input(); 
		}
*/	
		//Test Case 2 
		/*The reason for following case which can not pass is syntax has ambiguity in 
			"expression:factor = expression|simple-expression"
			this production will lead to ambiguity between "ID = NUM" and "ID == NUM",
			I have tried my best but still not solve it.
			Please help me modify syntax and let this case pass.	Thx 
		*/
/*
 		Token[] token = new Token[53];
		
  		token[0] = new Token("int", "TYPE");
		token[1] = new Token("ID", "TYPE");
		token[2] = new Token("(", "IDENTIFY");
		token[3] = new Token("int", "TYPE");
		token[4] = new Token("ID", "TYPE");
		token[5] = new Token(",", "BORDER");
		token[6] = new Token("int", "BORDER");
		token[7] = new Token("ID", "TYPE");
		token[8] = new Token(")", "TYPE");
		token[9] = new Token("{", "TYPE");
		token[10] = new Token("if", "TYPE");
		token[11] = new Token("(", "TYPE");
		token[12] = new Token("ID", "IDENTIFY");
		token[13] = new Token("==", "TYPE");
		token[14] = new Token("NUM", "TYPE");
		token[15] = new Token(")", "BORDER");
		token[16] = new Token("return", "BORDER");
		token[17] = new Token("ID", "BORDER");
		token[18] = new Token(";", "BORDER");
		token[19] = new Token("else", "BORDER");
		token[20] = new Token("return", "BORDER");
		token[21] = new Token("ID", "BORDER");
		token[22] = new Token("(", "BORDER");
		token[23] = new Token("ID", "BORDER");
		token[24] = new Token(",", "BORDER");
		token[25] = new Token("ID", "BORDER");
		token[26] = new Token("-", "BORDER");
		token[27] = new Token("ID", "BORDER");
		token[28] = new Token("/", "BORDER");
		token[29] = new Token("ID", "BORDER");
		token[30] = new Token("*", "BORDER");
		token[31] = new Token("ID", "BORDER");
		token[32] = new Token(")", "BORDER");
		token[33] = new Token(";", "BORDER");
		token[34] = new Token("}", "BORDER");
		token[35] = new Token("void", "BORDER");
		token[36] = new Token("ID", "BORDER");
		token[37] = new Token("(", "BORDER");
		token[38] = new Token("void", "BORDER");
		token[39] = new Token(")", "BORDER");
		token[40] = new Token("{", "BORDER");
		token[41] = new Token("int", "BORDER");
		token[42] = new Token("ID", "BORDER");
		token[43] = new Token(";", "BORDER");
		token[44] = new Token("int", "BORDER");
		token[45] = new Token("ID", "BORDER");
		token[46] = new Token(";", "BORDER");
		token[47] = new Token("ID", "BORDER");
		token[48] = new Token("=", "BORDER");
		token[49] = new Token("ID", "BORDER");
		token[50] = new Token("(", "BORDER");
		token[51] = new Token(")", "BORDER");
		token[52] = new Token(";", "BORDER");
	*/
		/*
		 * void sort ( int a[], int low, int high )
			{ int i; int k;
				i = low;
				while (i < high-1)
				{ int t;
				k = minloc (a,i,high);
				t =a[k];
				a[k] = a[i];
				a[i] = t;
				i = i + 1;
			}
		 */
		//Test Case 3
//		Token[] token = new Token[55];
//		
//		token[0] = new Token("void", "TYPE");
//		token[1] = new Token("ID", "TYPE");
//		token[2] = new Token("(", "IDENTIFY");
//		token[3] = new Token("int", "TYPE");
//		token[4] = new Token("ID", "TYPE");
//		token[5] = new Token("[", "BORDER");
//		token[6] = new Token("]", "BORDER");
//		token[7] = new Token(",", "BORDER");
//		token[8] = new Token("int", "BORDER");
//		token[9] = new Token("ID", "TYPE");
//		token[10] = new Token(",", "BORDER");
//		token[11] = new Token("int", "BORDER");
//		token[12] = new Token("ID", "TYPE");
//		token[13] = new Token(")", "TYPE");
//		token[14] = new Token("{", "TYPE");
//		token[15] = new Token("int", "BORDER");
//		token[16] = new Token("ID", "TYPE");
//		token[17] = new Token(";", "BORDER");
//		token[18] = new Token("ID", "BORDER");
//		token[19] = new Token("=", "TYPE");
//		token[20] = new Token("ID", "BORDER");
//		token[21] = new Token("+", "TYPE");
//		token[22] = new Token("ID", "BORDER");
//		token[23] = new Token(";", "TYPE");
//		token[24] = new Token("while", "TYPE");
//		token[25] = new Token("(", "IDENTIFY");
//		token[26] = new Token("ID", "IDENTIFY");
//		token[27] = new Token("<", "TYPE");
//		token[28] = new Token("ID", "IDENTIFY");
//		token[29] = new Token("-", "BORDER");
//		token[30] = new Token("NUM", "BORDER");
//		token[31] = new Token(")", "BORDER");
//		token[32] = new Token("{", "TYPE");
//		token[33] = new Token("int", "TYPE");
//		token[34] = new Token("ID", "TYPE");
//		token[35] = new Token(";", "BORDER");
//		token[36] = new Token("ID", "TYPE");
//		token[37] = new Token("=", "BORDER");
//		token[38] = new Token("ID", "TYPE");
//		token[39] = new Token("(", "IDENTIFY");
//		token[40] = new Token("ID", "TYPE");
//		token[41] = new Token(",", "BORDER");
//		token[42] = new Token("ID", "TYPE");
//		token[43] = new Token(",", "BORDER");
//		token[44] = new Token("ID", "TYPE");
//		token[45] = new Token(")", "BORDER");
//		token[46] = new Token(";", "BORDER");
//		token[47] = new Token("ID", "TYPE");
//		token[48] = new Token("=", "BORDER");
//		token[49] = new Token("ID", "TYPE");
//		token[50] = new Token("[", "BORDER");
//		token[51] = new Token("ID", "BORDER");
//		token[52] = new Token("]", "BORDER");
//		token[53] = new Token(";", "BORDER");
//		token[54] = new Token("}", "BORDER");
		Lexer lex = new Lexer();
		AbstractSyntaxTree tree = new AbstractSyntaxTree(analysic.PredictAndAnalyze(lex.getTokenStream()));
	}
}
