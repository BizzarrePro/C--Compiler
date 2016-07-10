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
			this.message = "'"+args
					+ "' was not declared in the scope at line " + line+"\r\n";
			break;
		case 1:
			this.message = "You have an error in your C- syntax; \r\n"
					+ "		check the manual that corresponds to your C- version for the right"
					+ "syntax to use near '" + args + "' at line " + line+"\r\n";
			break;
		case 2:
			this.message = "conflicting declaration '" + args + "' at line " + line + "\r\n";
			break;
		case 3:
			this.message = "function '" + args
					+ "' was not declared in the scope at line " + line + "\r\n";
			break;
		case 4:
			this.message = "variable couldn't be declared 'void'"
					+ "syntax to use near '" + args + "' at line " + line + "\r\n";
			break;
		case 5:
			this.message = "size of array '" + args + "' has non-integral type 'double' at line " + line;
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