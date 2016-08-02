package team.weird.compiler.editor.function;

public interface SelectionMenuItemFunc {

	void selectAllAction();
	void expandingSelectionToLine();
	void expandingSelectionToWord();
	void expandingSelectionToScope();
	void moveUpSelectionLines();
	void moveDownSelectionLines();
}
