package team.weird.compiler.editor.function;

public interface RunMenuItemFunc {
	void runCode();
	void showOriginalSyntax();
	void showLexerAnalysis();
	void showParserAnalysis();
	void showIntermediateCode();
	void showAsmCode();
}
