package team.weird.texteditor.menu;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import team.weird.texteditor.attribute.FileAttribute;
import team.weird.texteditor.implement.FileAction;
import team.weird.texteditor.util.FileActionUtil;
/**
 * @author Siyuan_Liu
 */
public class FileMenuItem {
	private JMenuBar menuBar; 
	private JTabbedPane contentPane;
	private ArrayList<String> container;
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
		FileAction ExitFile = new FileAction("Exit");
		JMenuItem newItem = new JMenuItem(newTxt);
		JMenuItem openItem = new JMenuItem(OpenFile);
		/**
		 * @author Siyuan_Liu
		 */
		JMenuItem openRecentItem = new JMenuItem(OpenRecentFile);
/*		FileActionUtil util = new FileActionUtil();
		FileAction OpenDirectly = new FileAction("Open Dire");
		container = util.ExtractRecentPath("./recent/recentFile.txt");
		JMenu recentFile = new JMenu();
		System.out.println(container.size());
		for(int i = 0; i < container.size(); i++){
			JMenuItem TextItem = new JMenuItem(OpenFile);
			recentFile.add(TextItem);
		}
		openRecentItem.add(recentFile);
 * 
 */
		JMenuItem saveasItem = new JMenuItem(SaveasFile);
		JMenuItem saveItem = new JMenuItem(SaveFile);
		JMenuItem exitItem = new JMenuItem(ExitFile);
		newItem.setActionCommand("New");
		openItem.setActionCommand("Open");
		openRecentItem.setActionCommand("Open Re");
		saveasItem.setActionCommand("Save as");
		saveItem.setActionCommand("Save");
		exitItem.setActionCommand("Exit");
		newItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(openRecentItem);
		fileMenu.addSeparator();
		fileMenu.add(saveItem);
		fileMenu.add(saveasItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
	}
}
