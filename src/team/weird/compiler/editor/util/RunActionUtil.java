package team.weird.compiler.editor.util;

import java.io.IOException;

import team.weird.compiler.cminus.codegen.Instruction;
import team.weird.compiler.cminus.lexer.Lexer;
import team.weird.compiler.cminus.lexer.Token;
import team.weird.compiler.cminus.parser.EliminationOfLeftRecursion;
import team.weird.compiler.cminus.parser.ExtractProduction;
import team.weird.compiler.cminus.parser.FirstSet;
import team.weird.compiler.cminus.parser.FollowSet;
import team.weird.compiler.cminus.parser.OverlappedSyntaxException;
import team.weird.compiler.cminus.parser.PredictiveAnalytics;
import team.weird.compiler.cminus.parser.SelectSet;
import team.weird.compiler.cminus.semantic.ErrorList;
import team.weird.compiler.cminus.semantic.Semantic;
import team.weird.compiler.cminus.semantic.SemanticException;

public final class RunActionUtil {
	public static void run() throws Throwable {
		ErrorList err = ErrorList.getInstance();
		err.clear();
		Lexer lex = new Lexer();
		Token[] token = lex.getTokenStream();
		lex.print(token);
		EliminationOfLeftRecursion removeLeftRecursion = new EliminationOfLeftRecursion();
		removeLeftRecursion.print();
		FirstSet firstSet = new FirstSet(removeLeftRecursion.getSymbolMap());
		firstSet.createFirstSet();
		firstSet.print();
		FollowSet followSet = new FollowSet(firstSet.symbolMap);
		followSet.createFollowSet();
		followSet.print();
		SelectSet selectSet = new SelectSet(followSet.symbolMap,
				ExtractProduction.TerminatingSymbolTable);
		selectSet.createSelectSet();

		PredictiveAnalytics analysic = new PredictiveAnalytics(
				selectSet.getSymbolMap(), ExtractProduction.TerminatingSymbolTable,
				EliminationOfLeftRecursion.entrance);
		analysic.print();
		Semantic semantic = Semantic.getInstance(analysic.PredictAndAnalyze(token));
		semantic.init();
		Instruction ins = semantic.program.generateIntermediateCode();
		semantic.program.printIntermadiateCode(ins);
		err.throwsAllExceptions();
	}
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
/**		int a(int b){
		return 1; }
		int main(void){
		int a;
		a = 5;
		{
		 float a;
		 a = 6.0;
		 a(a);
		}$
**/
}
