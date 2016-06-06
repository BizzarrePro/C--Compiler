package team.weird.texteditor.lexer;

import java.io.IOException;
import java.util.ArrayList;

public class Lexer {
	public StringBuffer fullLine = new StringBuffer();
//	public LinkedList<Token> list = new LinkedList<Token>();
	public ArrayList<Token> tokenList = new ArrayList<Token>();
	private boolean exitTag = false;
	public Lexer() {}
	private int line = 1;
	private char temp = ' ';
	private ReservedWord table = new ReservedWord();
	public int getLine(){
		return line;
	}
	public Token[] getTokenStream() throws IOException{
		while(true){
			Token element = scan();
			if(element == null)
				break;
			element.setLineNum(line);
			tokenList.add(element);
		}
		Token[] tokenStream = new Token[tokenList.size()];
		tokenList.toArray(tokenStream);
		return tokenStream;
	}
	public void getChar() throws IOException{
		temp = (char)System.in.read();
		if(temp == '$')
			exitTag = true;
		fullLine.append(temp);
	}
	public boolean getNextChar(char ch) throws IOException{
		getChar();
		if(temp != ch)
			return false;
		temp = ' ';
		return true;
	}
	public Token scan() throws IOException{
		for( ; ; getChar()){
			if(exitTag)
				return null;
			if(temp == ' ' || temp == '\t'||temp == '\r') continue;
			else if(temp == '\n')
				line++;
			else if(temp == '/'){
				getChar();
				if(temp == '*'){
					StringBuffer buf = new StringBuffer("");
					char prev = ' ';
					buf.append('/');
					do{
						prev = temp;
						buf.append(temp);
						getChar();
					}while(!((temp == '/') && (prev == '*')));
					buf.append('/');
					String note = buf.toString();
					System.out.print(note);
				}
				else if(temp == '/'){
					StringBuffer buf = new StringBuffer("");
					buf.append('/');
					do{
						buf.append(temp);
						getChar();
					}while(temp != '\n');
					String note = buf.toString();
					System.out.print(note);
				}
					
			}
			else
				break;
		}
		switch(temp){
		case '>':	
			if(getNextChar('='))	
				return table.lookUpTable(">=");	
			else	
				return table.lookUpTable(">");
		case '<':	
			if(getNextChar('='))	
				return table.lookUpTable("<=");
			else	
				return table.lookUpTable("<");	
		case '=':	
			if(getNextChar('='))	
				return table.lookUpTable("==");	
			else	
				return table.lookUpTable("=");	
		case '|':	
			if(getNextChar('|'))	
				return table.lookUpTable("||");	
			else	
				return table.lookUpTable("|");
		case '&':	
			if(getNextChar('&'))	
				return table.lookUpTable("&&");		
			else	
				return table.lookUpTable("&");
		case '!':	
			if(getNextChar('='))	
				return table.lookUpTable("!=");	
			else	
				return table.lookUpTable("!");	
		}
		
		if( Character.isDigit(temp)){
			int num = 0;
			do{
				num = num*10 + (temp - '0');
				getChar();
			}while(Character.isDigit(temp));
			
			if(temp != '.')	return new Number(num, "NUM");
			double out = num;
			double bit = 10;
			while(true){
				getChar();
				if(!Character.isDigit(temp))	break;
				out += ((temp - '0')/bit);
				bit *= 10;
			}
			return new Float(out, "NUM");
		}
		if(	Character.isLetter(temp)){
			StringBuffer buf = new StringBuffer();
			do{
				buf.append(temp);
				getChar();
			} while(Character.isLetterOrDigit(temp) || temp == '_');
			String str = buf.toString();
			Token value = table.lookUpTable(str);
			if(value == null){
				value = new Word(str, "ID");
				return value;
			}
			else
				return value;
		}
		Token bo = new Token(""+temp);
		temp = ' ';
		return bo;
	}
}