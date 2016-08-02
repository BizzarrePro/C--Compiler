package team.weird.compiler.editor.menu;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import team.weird.compiler.editor.implement.SelectAction;
import team.weird.compiler.editor.implement.ToolsAction;

public class ToolMenuItem {
	private JMenuBar menuBar = null; 
	private JTabbedPane contentPane = null;
	private JFrame frame = null;
	public ToolMenuItem(JMenuBar menuBar, JTabbedPane contentPane, JFrame frame){
		this.menuBar = menuBar;
		this.contentPane = contentPane;
		this.frame = frame;
	}
	public void initToolMenuItem(){
		JMenu toolMenu = new JMenu("Tools");
		menuBar.add(toolMenu);
		ToolsAction translationTxt =new ToolsAction("Translation",contentPane, frame);
		ToolsAction commitToGithub = new ToolsAction("Commit to GitHub", contentPane, frame);
		JMenuItem transItem = new JMenuItem(translationTxt);
		JMenuItem commitItem = new JMenuItem(commitToGithub);
		transItem.setActionCommand("Translation");
		commitItem.setActionCommand("Commitment");
		transItem.setAccelerator(KeyStroke.getKeyStroke("ctrl shift T"));
		toolMenu.add(transItem);
		toolMenu.add(commitItem);
		
	}
}
