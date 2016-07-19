package team.weird.texteditor.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import team.weird.texteditor.implement.FileAction;
import team.weird.texteditor.attribute.FileAttribute;
import team.weird.texteditor.implement.FileOpenRecAction;
import team.weird.texteditor.util.FileActionUtil;
/**
 * @filename FileMenuItem.java
 * @author Siyuan_Liu
 * @description Assign action of file menu items and add items to menu. 
 */
public class FileMenuItem{
	/** 
	 * @param contentPane: Tabbed pane for displaying multiple documents
	 * @param fileMap: This hash map is defined for storing structure of FileAttribute {file name, file absolute path}
	 * @see FileAttribute: team.weird.texteditor.attribute.FileAttribute.java
	 * @param pan: The reference of prime frame
	 * 
	 */
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
		
		JPanel statePanel = new JPanel();
		statePanel.setPreferredSize(new Dimension(pan.getWidth(), 20));
		statePanel.setBackground(pan.getBackground());
		pan.add(statePanel, BorderLayout.SOUTH);
		statePanel.setLayout(new FlowLayout(pan.getWidth()));
		JLabel Line = new JLabel("Line ");
		JLabel lineValue = new JLabel("0");
		JLabel Column = new JLabel(" Column ");
		JLabel columnValue = new JLabel("0");
//		Line.setBackground(statePanel.getBackground());
//		Column.setBackground(statePanel.getBackground());
		statePanel.add(Line);
		statePanel.add(lineValue);
		statePanel.add(Column);
		statePanel.add(columnValue);
		/**
		 * @description Create action for every menu item
		 * @see FileAction: team.weird.texteditor.implement.FileAction.java
		 */
		FileAction newTxt = new FileAction("New File", contentPane, fileMap, lineValue, columnValue);
		FileAction newWin = new FileAction("New Windows", contentPane, fileMap);
		FileAction closeWin = new FileAction("Close Windows", pan);
		FileAction OpenFile = new FileAction("Open File..", contentPane, fileMap);
		FileAction SaveasFile = new FileAction("Save as", contentPane, fileMap);
		FileAction CloseFile = new FileAction("Close File", contentPane);
		FileAction CloseAllFile = new FileAction("Close All File", contentPane);
		FileAction SaveFile = new FileAction("Save", contentPane, fileMap);
		FileAction ExitFile = new FileAction("Exit", pan);
		newTxt.loadLastText();
		/**
		 * @description Create menu items
		 */
		JMenuItem newItem = new JMenuItem(newTxt);
		JMenuItem newWinItem = new JMenuItem(newWin);
		JMenuItem openItem = new JMenuItem(OpenFile);
		JMenu openRecentItem = new JMenu("Open Recent");
		
		/**
		 * Invoke function of FileActionUtil.class to put child menu items to father menu
		 */
		FileActionUtil util = new FileActionUtil();
		util.putToTwoLevelMenu(contentPane, fileMap, openRecentItem);
		
		JMenuItem closeWinItem = new JMenuItem(closeWin);
		JMenuItem closeFileItem = new JMenuItem(CloseFile);
		JMenuItem closeAllFileItem = new JMenuItem(CloseAllFile);
		JMenuItem saveasItem = new JMenuItem(SaveasFile);
		JMenuItem saveItem = new JMenuItem(SaveFile);
		JMenuItem exitItem = new JMenuItem(ExitFile);
		
		/**
		 * Set action command for menu item so that action listener can choose corresponding action
		 */
		newItem.setActionCommand("New");
		newWinItem.setActionCommand("New Windows");
		closeFileItem.setActionCommand("Close Windows");
		openItem.setActionCommand("Open");
		openRecentItem.setActionCommand("Open Re");
		closeFileItem.setActionCommand("Close File");
		closeAllFileItem.setActionCommand("Close All File");
		saveasItem.setActionCommand("Save as");
		saveItem.setActionCommand("Save");
		exitItem.setActionCommand("Exit");
		
		/**
		 * Set keyboard shortcuts for menu items
		 */
		newItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		newWinItem.setAccelerator(KeyStroke.getKeyStroke("ctrl shift N"));
		openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		closeFileItem.setAccelerator(KeyStroke.getKeyStroke("ctrl W"));
		
		/**
		 * Add menu items to menu
		 */
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(openRecentItem);
		fileMenu.addSeparator();
		fileMenu.add(saveItem);
		fileMenu.add(saveasItem);
		fileMenu.addSeparator();
		fileMenu.add(newWinItem);
		fileMenu.add(closeWinItem);
		fileMenu.addSeparator();
		fileMenu.add(closeFileItem);
		fileMenu.add(closeAllFileItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
	}
}
