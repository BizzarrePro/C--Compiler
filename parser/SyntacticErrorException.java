package team.weird.texteditor.parser;

public class SyntacticErrorException extends Exception {
	/**
	 * Report Syntactic Error
	 */
	private static final long serialVersionUID = 1L;

	public SyntacticErrorException() {

	}

	public SyntacticErrorException(String str) {
		super(str);
	}
}