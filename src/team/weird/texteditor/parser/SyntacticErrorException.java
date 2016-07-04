package team.weird.texteditor.parser;

public class SyntacticErrorException extends Exception {
	/**
	 * Report Syntactic Error
	 */
	private static final long serialVersionUID = 1L;
	private String message = null;
	public SyntacticErrorException() {

	}

	public SyntacticErrorException(String args, int line) {
		this.message = "You have an error in your C- syntax; \r\n"
				+ "check the manual that corresponds to your C- version for the right"
				+ "syntax to use near '"
				+ args + "' at line "
				+ line;
	}
	public String toString(){
		return message;
	}
	public String getMessage(){
		return message;
	}
	
}