package team.weird.texteditor.semantic;

import java.io.IOException;
import java.util.Scanner;

import team.weird.texteditor.lexer.Lexer;
import team.weird.texteditor.lexer.LexerErrorException;
import team.weird.texteditor.lexer.Token;
import team.weird.texteditor.parser.EliminationOfLeftRecursion;
import team.weird.texteditor.parser.ExtractProduction;
import team.weird.texteditor.parser.FirstSet;
import team.weird.texteditor.parser.FollowSet;
import team.weird.texteditor.parser.OverlappedSyntaxException;
import team.weird.texteditor.parser.PredictiveAnalytics;
import team.weird.texteditor.parser.SelectSet;
import team.weird.texteditor.parser.SyntacticErrorException;

public class Main {
	public static void main(String[] args) throws OverlappedSyntaxException,
			SyntacticErrorException, IOException, LexerErrorException {
		EliminationOfLeftRecursion removeLeftRecursion = EliminationOfLeftRecursion.getInstance();
		
		FirstSet firstSet = new FirstSet(removeLeftRecursion.getSymbolMap());
		firstSet.createFirstSet();

		FollowSet followSet = new FollowSet(firstSet.symbolMap);
		followSet.createFollowSet();
		
		SelectSet selectSet = new SelectSet(followSet.symbolMap,
				ExtractProduction.TerminatingSymbolTable);
		selectSet.createSelectSet();
		
		PredictiveAnalytics analysic = new PredictiveAnalytics(
				selectSet.getSymbolMap(), ExtractProduction.TerminatingSymbolTable,
				EliminationOfLeftRecursion.entrance);
		Lexer lex = Lexer.getInstance();
//		for(Token a:lex.getTokenStream())
//			System.out.println(a.getClass().getSimpleName());
		AbstractSyntaxTree tree = new AbstractSyntaxTree(
				analysic.PredictAndAnalyze(lex.getTokenStream()), 
				ExtractProduction.TerminatingSymbolTable);
/*		&&&&&Display model&&&&&
		Scanner pause = new Scanner(System.in);
		pause.next();
		removeLeftRecursion.displayAfterElimination();
		firstSet.display();
		followSet.display();
		selectSet.display();
		analysic.displayPredictiveTable();
*/
		//Test Case 1
/**		
  		int a(int b,int c)
 */
		//Test Case 2 
		//This parser couldn't deal with "dangling else" problem, because it used LL1 syntax
/**		
  		int a(int b){
			if (a == 5)
				return a;
			else
				;
		}
*/
		//Test Case 3
		/*The reason for following case which can not pass is syntax has ambiguity in 
			"expression:factor = expression|simple-expression"
			this production will lead to ambiguity between "ID = NUM" and "ID == NUM",
			I have tried my best but still not solve it.
			Please help me modify syntax and let this case pass.
			This bug has been fixed by Qian_Yang
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
/**		
  		bool isAnimal(float a){
			 double b;
			 b = 1.51;
			 b = b + a;
			}$
*/
/*
 		//Test Case 4
/**
		   void sort ( int a[], int low, int high )
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
		//Test Case 5
		/* A program to perform selection sort on a 10 element array. */
/**		int x[10];
		int minloc ( int a[], int low, int high )
		{ 
			int i; int x; int k;
			k = low;
			x = a[low];
			i = low + 1;
			while (i < high)
			{ 
				if (a[i] < x)
				{ 
					x = a[i];
					k = i; 
				}
				else
					;
				i = i + 1;
			}
				return k;
		}
		void sort ( int a[], int low, int high )
		{ 
			int i; int k;
			i = low;
			while (i < high-1)
			{ 
				int t;
				k = minloc (a,i,high);
				t =a[k];
				a[k] = a[i];
				a[i] = t;
				i = i + 1;
			}
		}
		void main (void)
		{ 	
			int i;
			i = 0;
			while (i < 10)
			{ 
				x[i] = input;
				i = i + 1;
				sort (x,0,10);
				i = 0;
				while (i < 10)
				{ 
					output(x[i]);
					i = i + 1;
				}
			}
		}
*/
	}
}