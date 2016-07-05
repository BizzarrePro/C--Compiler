package team.weird.texteditor.lexer;

public class LexerErrorException extends Exception{

	/**
	 * 
	 */
	private String message = null;
	private static final long serialVersionUID = 1L;
	
	public LexerErrorException(String identify, int line){
		this.message = "You have an error in your C- syntax; \r\n"
				+ "Invalid suffix \""+identify+"\" on Integer constant at line "+line;
	}
	public String toString(){
		return message;
	}
	public String getMessage(){
		return message;
	}
	
}
