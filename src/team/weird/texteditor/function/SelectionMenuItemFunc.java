package team.weird.texteditor.function;

public interface SelectionMenuItemFunc {

	void selectAllAction();
	void expandingSelectionToLine();
	void expandingSelectionToWord();
	void expandingSelectionToScope();
	void moveUpSelectionLines();
	void moveDownSelectionLines();
}
