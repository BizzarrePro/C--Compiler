package team.weird.texteditor.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import team.weird.texteditor.implement.EditAction;
/**
 * 
 * @author Qian_Yang
 * @copyright All rights reserved by Qian_Yang.
 */
public class EditMenuItem {
	JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		EditAction undoTxt =new EditAction("Undo",contentPane);
		EditAction redoTxt =new EditAction("Redo",contentPane);
		EditAction cutTxt = new EditAction("Cut", contentPane);
		EditAction copyTxt = new EditAction("Copy", contentPane);
		EditAction pasteTxt = new EditAction("Paste", contentPane);
		EditAction selectAll =new EditAction("Select All",contentPane);
		JMenuItem undoItem = new JMenuItem(undoTxt);
		JMenuItem redoItem = new JMenuItem(redoTxt);
		JMenuItem cutItem = new JMenuItem(cutTxt);
		JMenuItem copyItem = new JMenuItem(copyTxt);
		JMenuItem pasteItem = new JMenuItem(pasteTxt);
		JMenuItem selectItem =new JMenuItem(selectAll);
		undoItem.setActionCommand("Undo");
		redoItem.setActionCommand("Redo");
		cutItem.setActionCommand("Cut");
		copyItem.setActionCommand("Copy");
		pasteItem.setActionCommand("Paste");
		selectItem.setActionCommand("Select All");
		undoItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		redoItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Y"));
		cutItem.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		copyItem.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
		pasteItem.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
		selectItem.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
		editMenu.add(undoItem);
		editMenu.add(redoItem);
		editMenu.addSeparator();
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.addSeparator();
		editMenu.add(selectItem);
	}
}