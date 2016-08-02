package team.weird.compiler.editor.menu;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

import team.weird.compiler.editor.implement.HelpAction;
import team.weird.compiler.editor.implement.RunAction;

public class HelpMenuItem {
	private JMenuBar menuBar = null; 
	private JTabbedPane contentPane = null;
	private JFrame frame = null;
	public HelpMenuItem(JMenuBar menuBar, JTabbedPane contentPane, JFrame frame){
		this.menuBar = menuBar;
		this.contentPane = contentPane;
		this.frame = frame;
	}
	public void initHelpMenuItem(){
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		HelpAction documentation =new HelpAction("Documentation",contentPane, frame);
		HelpAction aboutWeirdText = new HelpAction("About Weird Text", contentPane, frame);
		JMenuItem docItem = new JMenuItem(documentation);
		JMenuItem aboutItem = new JMenuItem(aboutWeirdText);
		docItem.setActionCommand("documentation");
		aboutItem.setActionCommand("aboutText");
		JSeparator sep = new JSeparator();
		helpMenu.add(docItem);
		helpMenu.add(sep);
		helpMenu.add(aboutItem);
	}
}
