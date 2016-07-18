package team.weird.texteditor.menu;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import team.weird.texteditor.implement.SelectAction;

public class SelectMenuItem {
	private JMenuBar menuBar = null; 
	private JTabbedPane contentPane = null;
	private JFrame frame = null;
	public SelectMenuItem(JMenuBar menuBar, JTabbedPane contentPane, JFrame frame){
		this.menuBar = menuBar;
		this.contentPane = contentPane;
		this.frame = frame;
	}
	public void initSelectionMenuItem(){
		JMenu selectMenu = new JMenu("Selection");
		menuBar.add(selectMenu);
		SelectAction selAllTxt =new SelectAction("Select All",contentPane, frame);
		SelectAction lineTxt = new SelectAction("Expanding Selection to Line", contentPane, frame);
		SelectAction wordTxt = new SelectAction("Expanding Selection to Word", contentPane, frame);
		SelectAction scopeTxt = new SelectAction("Expanding Selection to Scope", contentPane, frame);
		SelectAction moveupTxt = new SelectAction("Enclosing Element", contentPane, frame);
		SelectAction movedownTxt = new SelectAction("Restore Last Selection", contentPane, frame);
		JMenuItem selAllItem = new JMenuItem(selAllTxt);
		JMenuItem lineItem = new JMenuItem(lineTxt);
		JMenuItem wordItem = new JMenuItem(wordTxt);
		JMenuItem scopeItem = new JMenuItem(scopeTxt);
		JMenuItem moveupItem = new JMenuItem(moveupTxt);
		JMenuItem movedownItem = new JMenuItem(movedownTxt);
		selAllItem.setActionCommand("All");
		lineItem.setActionCommand("Line");
		wordItem.setActionCommand("Word");
		scopeItem.setActionCommand("Scope");
		moveupItem.setActionCommand("Moveup");
		movedownItem.setActionCommand("Movedown");
		selAllItem.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
		lineItem.setAccelerator(KeyStroke.getKeyStroke("ctrl L"));
		wordItem.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
		scopeItem.setAccelerator(KeyStroke.getKeyStroke("ctrl shift space"));
		moveupItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
		movedownItem.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));
		selectMenu.add(selAllItem);
//		selectMenu.add(invertOpe);
		selectMenu.addSeparator();
		selectMenu.add(lineItem);
		selectMenu.add(wordItem);
		selectMenu.add(scopeItem);
		selectMenu.addSeparator();
		selectMenu.add(moveupItem);
		selectMenu.add(movedownItem);
	}
}
