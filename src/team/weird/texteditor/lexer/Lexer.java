package team.weird.texteditor.lexer;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Lexer {
	public StringBuffer fullLine = new StringBuffer();
	public LinkedList<Token> list = new LinkedList<Token>();
	public Lexer() throws IOException{
		while(true){
			list.add(scan());
			//System.out.print(" "+scan().toString()+"\n");
		}
	}
//	private int line = 0;
//	private char temp = ' ';
//	private ReservedWord table = new ReservedWord();
//	public int getLine(){
//		return line;
//	}
/*	private int line = 0;
	private char temp = ' ';
	private ReservedWord table = new ReservedWord();
	public int getLine(){
		return line;
	}*/
	private int line = 0;
	private char temp = ' ';
	private ReservedWord table = new ReservedWord();
	public int getLine(){
		return line;
	}
	public void getChar() throws IOException{
		temp = (char)System.in.read();
		fullLine.append(temp);
		if(temp == '\n')
			line++;
	}
	public boolean getNextChar(char ch) throws IOException{
		getChar();
		if(temp == ch)
			return true;
		temp = ' ';
		return false;
	}
	public Token scan() throws IOException{
		for( ; ; getChar()){
			if(temp == ' ' || temp == '\t');
			else if(temp == '\n'){
				System.out.println(line+ " "+fullLine.toString().trim());
				fullLine = new StringBuffer("");
				Iterator<Token> iter = list.iterator();
				while(iter.hasNext())
					System.out.println(iter.next().toString());
				list.clear();
			}
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
//						if(temp == '\n'){
//							System.out.println(line);
//							line++;
//						}
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
			if(temp != '.')	return new Number(num, "DIGIT");
			double out = num;
			double bit = 10;
			while(true){
				getChar();
				if(!Character.isDigit(temp))	break;
				out += ((temp - '0')/bit);
				bit *= 10;
			}
			return new Float(out, "FLOATINGDIGIT");
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
				value = new Token(str, "IDENTIFY");
				table.putInTable(str, value);
				return value;
			}
			else
				return value;
		}
		Bound bo = new Bound(temp);
		temp = ' ';
		return bo;
	}
}