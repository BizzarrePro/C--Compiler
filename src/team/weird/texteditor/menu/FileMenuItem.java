package team.weird.texteditor.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import team.weird.texteditor.implement.FileAction;

public class FileMenuItem {
	private JMenuBar menuBar; 
	private JTabbedPane contentPane;
	public FileMenuItem(JMenuBar menuBar, JTabbedPane contentPane){
		this.menuBar = menuBar;
		this.contentPane = contentPane;
	}
	public void initFileMenuItem(){
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		FileAction newTxt = new FileAction("New File", contentPane);
		FileAction OpenFile = new FileAction("Open File..", contentPane);
		FileAction SaveasFile = new FileAction("Save as", contentPane);
		FileAction SaveFile = new FileAction("Save", contentPane);
		JMenuItem newItem = new JMenuItem(newTxt);
		JMenuItem openItem = new JMenuItem(OpenFile);
		JMenuItem saveasItem = new JMenuItem(SaveasFile);
		JMenuItem saveItem = new JMenuItem(SaveFile);
		newItem.setActionCommand("New");
		openItem.setActionCommand("Open");
		saveasItem.setActionCommand("Save as");
		saveItem.setActionCommand("Save");
		newItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		fileMenu.add(saveItem);
		fileMenu.add(saveasItem);
	}
}
