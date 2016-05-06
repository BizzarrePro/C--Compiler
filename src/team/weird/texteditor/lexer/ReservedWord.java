package team.weird.texteditor.lexer;

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
	public void putInTable(String id, Token tok){
		reservedSet.put(id, tok);
	}
	public static void createTable(){
		reservedSet.put("public", new Token("public", "ACCESS"));
		reservedSet.put("private", new Token("private", "ACCESS"));
		reservedSet.put("protected", new Token("protected", "ACCESS"));
		
		reservedSet.put("class", new Token("class", "MODIFIER"));
		reservedSet.put("final", new Token("final", "MODIFIER"));
		reservedSet.put("static", new Token("static", "MODIFIER"));
		
		reservedSet.put("break", new Token("break", "RUNTIME"));
		reservedSet.put("continue", new Token("continue", "RUNTIME"));
		reservedSet.put("return", new Token("return", "RUNTIME"));
		reservedSet.put("break", new Token("break", "RUNTIME"));
		reservedSet.put("do", new Token("do", "RUNTIME"));
		reservedSet.put("while", new Token("while", "RUNTIME"));
		reservedSet.put("if", new Token("if", "RUNTIME"));
		reservedSet.put("else", new Token("else", "RUNTIME"));
		reservedSet.put("for", new Token("for", "RUNTIME"));
		reservedSet.put("switch", new Token("switch", "RUNTIME"));
		reservedSet.put("case", new Token("case", "RUNTIME"));
		reservedSet.put("default", new Token("default", "RUNTIME"));
		reservedSet.put("new", new Token("new", "RUNTIME"));
		reservedSet.put("throws", new Token("throws", "RUNTIME"));
	
		reservedSet.put("void", new Token("void", "TYPE"));
		reservedSet.put("int", new Token("int", "TYPE"));
		reservedSet.put("double", new Token("double", "TYPE"));
		reservedSet.put("float", new Token("float", "TYPE"));
		reservedSet.put("boolean", new Token("boolean", "TYPE"));
		reservedSet.put("byte", new Token("byte", "TYPE"));
		reservedSet.put("long", new Token("long", "TYPE"));
		reservedSet.put("char", new Token("char", "TYPE"));
		reservedSet.put("true", new Token("true", "TYPE"));
		reservedSet.put("false", new Token("false", "TYPE"));
		
		reservedSet.put("&&", new Token("&&", "COMPARATION"));
		reservedSet.put("||", new Token("||", "COMPARATION"));
		reservedSet.put("==", new Token("==", "COMPARATION"));
		reservedSet.put("<=", new Token("<=", "COMPARATION"));
		reservedSet.put(">=", new Token(">=", "COMPARATION"));
		reservedSet.put("!=", new Token("!=", "COMPARATION"));
		reservedSet.put(">", new Token(">", "COMPARATION"));
		reservedSet.put("<", new Token("<", "COMPARATION"));
		reservedSet.put("=", new Token("=", "COMPARATION"));
		
		reservedSet.put("&", new Token("&", "BITOPERATION"));
		reservedSet.put("|", new Token("|", "BITOPERATION"));
		reservedSet.put("!", new Token("!", "BITOPERATION"));
	}

}
