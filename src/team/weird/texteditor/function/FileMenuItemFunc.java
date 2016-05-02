package team.weird.texteditor.function;

import javax.swing.JTextArea;
/**
 * @author Siyuan_Liu
 */
public interface FileMenuItemFunc {
	JTextArea newFileAction(String name);
	void openFileAction();
	void saveFileAction();
	void saveAsFileAction();
	void exitFileAction();
}
