package team.weird.texteditor.parser;

public class SyntacticErrorException extends Exception {
	/**
	 * Report Syntactic Error
	 */
	private static final long serialVersionUID = 1L;
	private String message = null;

	public SyntacticErrorException() {

	}

	public SyntacticErrorException(String args, int line, int type) {
		switch (type) {
		case 0:
			this.message = "You have an error in your C- syntax; \r\n	'" + args
					+ "' was not declared in the scope at line " + line;
			break;
		case 1:
			this.message = "You have an error in your C- syntax; \r\n"
					+ "		check the manual that corresponds to your C- version for the right"
					+ "syntax to use near '" + args + "' at line " + line;
			break;
		case 2:
			this.message = "You have an error in your C- syntax; \r\n"
					+ "			conflicting declaration '" + args + "' at line " + line;
			break;
		}
	}

	public String toString() {
		return message;
	}

	public String getMessage() {
		return message;
	}

}