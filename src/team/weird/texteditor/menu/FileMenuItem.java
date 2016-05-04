package team.weird.texteditor.menu;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import team.weird.texteditor.implement.FileAction;
import team.weird.texteditor.attribute.FileAttribute;
import team.weird.texteditor.implement.FileOpenRecAction;
import team.weird.texteditor.util.FileActionUtil;
/**
 * @author Siyuan_Liu
 */
public class FileMenuItem{
	private JMenuBar menuBar; 
	private JTabbedPane contentPane;
	private HashMap<String, FileAttribute> fileMap;
	private JFrame pan;
	public FileMenuItem(JMenuBar menuBar, JTabbedPane contentPane, JFrame pan){
		this.menuBar = menuBar;
		this.contentPane = contentPane;
		this.fileMap = new HashMap<String, FileAttribute>();
		this.pan = pan;
	}
	public void initFileMenuItem(){
		JMenu fileMenu = new JMenu("File");
		FileAction newTxt = new FileAction("New File", contentPane, fileMap);
		FileAction newWin = new FileAction("New Windows", contentPane, fileMap);
		FileAction OpenFile = new FileAction("Open File..", contentPane, fileMap);
		FileAction SaveasFile = new FileAction("Save as", contentPane, fileMap);
		FileAction CloseFile = new FileAction("Close File", contentPane);
		FileAction CloseAllFile = new FileAction("Close All File", contentPane);
		FileAction SaveFile = new FileAction("Save", contentPane, fileMap);
		FileAction ExitFile = new FileAction("Exit", pan);
		JMenuItem newItem = new JMenuItem(newTxt);
		JMenuItem newWinItem = new JMenuItem(newWin);
		JMenuItem openItem = new JMenuItem(OpenFile);

		
		JMenu openRecentItem = new JMenu("Open Recent");
		FileActionUtil util = new FileActionUtil();
		util.putToTwoLevelMenu(contentPane, fileMap, openRecentItem);
		JMenuItem closeFileItem = new JMenuItem(CloseFile);
		JMenuItem closeAllFileItem = new JMenuItem(CloseAllFile);
		JMenuItem saveasItem = new JMenuItem(SaveasFile);
		JMenuItem saveItem = new JMenuItem(SaveFile);
		JMenuItem exitItem = new JMenuItem(ExitFile);
		newItem.setActionCommand("New");
		newWinItem.setActionCommand("New Windows");
		openItem.setActionCommand("Open");
		openRecentItem.setActionCommand("Open Re");
		closeFileItem.setActionCommand("Close File");
		closeAllFileItem.setActionCommand("Close All File");
		saveasItem.setActionCommand("Save as");
		saveItem.setActionCommand("Save");
		exitItem.setActionCommand("Exit");
		newItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		newWinItem.setAccelerator(KeyStroke.getKeyStroke("ctrl shift N"));
		openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		closeFileItem.setAccelerator(KeyStroke.getKeyStroke("ctrl W"));
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(openRecentItem);
		fileMenu.addSeparator();
		fileMenu.add(saveItem);
		fileMenu.add(saveasItem);
		fileMenu.addSeparator();
		fileMenu.add(newWinItem);
		fileMenu.add(closeFileItem);
		fileMenu.add(closeAllFileItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
	}
}
