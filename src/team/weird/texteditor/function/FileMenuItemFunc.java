package team.weird.texteditor.function;

import javax.swing.JTextArea;
/**
 * @author Siyuan_Liu
 */
public interface FileMenuItemFunc {
	JTextArea newFileAction(String name);
	void openFileAction();
	void openFileAction(String path);
	void saveFileAction();
	void saveAsFileAction();
	void openRecentFileAction();
	void exitFileAction();
}
