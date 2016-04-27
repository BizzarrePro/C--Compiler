package team.weird.texteditor.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import team.weird.texteditor.implement.EditAction;

public class EditMenuItem {
	private JMenuBar menuBar; 
	private JTabbedPane contentPane;
	public EditMenuItem(JMenuBar menuBar, JTabbedPane contentPane){
		this.menuBar = menuBar;
		this.contentPane = contentPane;
	}
	public void initEditMenuItem(){
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		EditAction cutTxt = new EditAction("Cut");
		EditAction copyTxt = new EditAction("Copy");
		EditAction pasteTxt = new EditAction("Paste");
		JMenuItem copyItem = new JMenuItem(copyTxt);	
		JMenuItem cutItem = new JMenuItem(cutTxt);
		JMenuItem pasteItem = new JMenuItem(pasteTxt);
		cutItem.setActionCommand("Cut");
		copyItem.setActionCommand("Copy");
		pasteItem.setActionCommand("Paste");
		cutItem.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		copyItem.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
		pasteItem.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.addSeparator();
	}
}
