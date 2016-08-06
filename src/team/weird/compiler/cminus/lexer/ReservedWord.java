package team.weird.compiler.cminus.lexer;

import java.util.HashMap;

public class ReservedWord {
	private static HashMap<String, Token> reservedSet;
	public ReservedWord(){
		reservedSet = new HashMap<String, Token>();
		createTable();
	}
	public Token lookUpTable(String word){
		if(reservedSet.containsKey(word))
			return reservedSet.get(word);
		else
			return null;
	}
	public static void createTable(){
		reservedSet.put(">=", new Token(">="));
		reservedSet.put("while", new Token("while"));
		reservedSet.put("==", new Token("=="));
		reservedSet.put("if", new Token("if"));
		reservedSet.put("]", new Token("]"));
		reservedSet.put("[", new Token("["));
		reservedSet.put("else", new Token("else"));
		reservedSet.put("int", new Token("int"));
		reservedSet.put("double", new Token("double"));
		reservedSet.put("float", new Token("float"));
		reservedSet.put("bool", new Token("bool"));
		reservedSet.put("int", new Token("int"));
		reservedSet.put("*", new Token("*"));
		reservedSet.put("return", new Token("return"));
		reservedSet.put("void", new Token("void"));
		reservedSet.put("+", new Token("+"));
		reservedSet.put(")", new Token(")"));
		reservedSet.put("(", new Token("("));
		reservedSet.put("/", new Token("/"));
		reservedSet.put(",", new Token(","));
		reservedSet.put("-", new Token("-"));
		reservedSet.put("<=", new Token("<="));
		reservedSet.put("!=", new Token("!="));
		reservedSet.put(";", new Token(";"));
		reservedSet.put(":", new Token(":"));
		reservedSet.put("}", new Token("}"));
		reservedSet.put("{", new Token("{"));
		reservedSet.put(">", new Token(">"));
		reservedSet.put("=", new Token("="));
		reservedSet.put("<", new Token("<"));
	}

}
//int gcd (int u, int v)
//{ if (v == 0) return u ;
//else return gcd(v,u-u/v*v);
///* u-u/v*v == u mod v */
//}
//void main(void)
//{ int x; int y;
//x = input(); y = input();
//output(gcd(x,y));
//}