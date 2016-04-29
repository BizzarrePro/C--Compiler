package team.weird.texteditor.menu;

import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import team.weird.texteditor.attribute.FileAttribute;
import team.weird.texteditor.implement.FileAction;

public class FileMenuItem {
	private JMenuBar menuBar; 
	private JTabbedPane contentPane;
	private HashMap<String, FileAttribute> fileMap;
	public FileMenuItem(JMenuBar menuBar, JTabbedPane contentPane){
		this.menuBar = menuBar;
		this.contentPane = contentPane;
		this.fileMap = new HashMap<String, FileAttribute>();
	}
	public void initFileMenuItem(){
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		FileAction newTxt = new FileAction("New File", contentPane, fileMap);
		FileAction OpenFile = new FileAction("Open File..", contentPane, fileMap);
		FileAction OpenRecentFile = new FileAction("Open Recent", contentPane, fileMap);
		FileAction SaveasFile = new FileAction("Save as", contentPane, fileMap);
		FileAction SaveFile = new FileAction("Save", contentPane, fileMap);
		JMenuItem newItem = new JMenuItem(newTxt);
		JMenuItem openItem = new JMenuItem(OpenFile);
		JMenuItem openRecentItem = new JMenuItem(OpenRecentFile);
		JMenuItem saveasItem = new JMenuItem(SaveasFile);
		JMenuItem saveItem = new JMenuItem(SaveFile);
		newItem.setActionCommand("New");
		openItem.setActionCommand("Open");
		openRecentItem.setActionCommand("Open Re");
		saveasItem.setActionCommand("Save as");
		saveItem.setActionCommand("Save");
		newItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(openRecentItem);
		fileMenu.addSeparator();
		fileMenu.add(saveItem);
		fileMenu.add(saveasItem);
	}
}
