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
			this.message = "Variable '"+ args +"': '"+ args + "' was not declared in the scope at line " + line;
			break;
		case 1:
			this.message = "An error appears in syntax"+ "near '" + args + "' at line " + line;
			break;
		case 2:
			this.message ="Variable '"+ args +"': Conflicting declaration '" + args + "' at line " + line;
			break;
		case 3:
			this.message = "Variable '" + args + "': was not declared in the scope at line " + line;
			break;
		case 4:
			this.message = "Variable '"+ args +"': Variable couldn't be declared 'void'"
					+ "syntax to use near '" + args + "' at line " + line;
			break;
		case 5:
			this.message = "Array '"+ args +"': Size of array '" + args + "' has non-integral type 'double' at line " + line;
			break;
		case 6:
			this.message = "Method '"+ args +"': 'void' methods can not return a value at line " + line ;
			break;
		case 7:
			this.message = "Method '"+ args +"': return value is not match with method declaration at line " + line ;
			break;
		case 8:
			this.message = "Method '" + args + "': was not declared in the scope at line " + line;
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