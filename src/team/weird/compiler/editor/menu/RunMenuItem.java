package team.weird.compiler.editor.menu;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import team.weird.compiler.editor.implement.RunAction;

public class RunMenuItem {
	private JMenuBar menuBar = null; 
	private JTabbedPane contentPane = null;
	private JFrame frame = null;
	public RunMenuItem(JMenuBar menuBar, JTabbedPane contentPane, JFrame frame){
		this.menuBar = menuBar;
		this.contentPane = contentPane;
		this.frame = frame;
	}
	public void initRunMenuItem(){
		JMenu runMenu = new JMenu("Run");
		menuBar.add(runMenu);
		RunAction runCode =new RunAction("Run",contentPane, frame);
		RunAction showSyntax = new RunAction("Original Syntax", contentPane, frame);
		RunAction showLexer = new RunAction("Print Lexer Result", contentPane, frame);
		RunAction showParser = new RunAction("Print Parser Procedure", contentPane, frame);
		RunAction showIntermediateCode = new RunAction("Print Intermediate Code", contentPane, frame);
		RunAction showASM = new RunAction("Print Assembler", contentPane, frame);
		JMenuItem runItem = new JMenuItem(runCode);
		JMenuItem syntaxItem = new JMenuItem(showSyntax);
		JMenuItem lexerItem = new JMenuItem(showLexer);
		JMenuItem parserItem = new JMenuItem(showParser);
		JMenuItem semanticItem = new JMenuItem(showIntermediateCode);
		JMenuItem targetItem = new JMenuItem(showASM);
		runItem.setActionCommand("run");
		syntaxItem.setActionCommand("syntax");
		lexerItem.setActionCommand("lexer");
		parserItem.setActionCommand("parser");
		semanticItem.setActionCommand("semantic");
		targetItem.setActionCommand("target");
		runItem.setAccelerator(KeyStroke.getKeyStroke("F11"));
		JSeparator sep = new JSeparator();
		runMenu.add(runItem);
		runMenu.add(sep);
		runMenu.add(syntaxItem);
		runMenu.add(lexerItem);
		runMenu.add(parserItem);
		runMenu.add(semanticItem);	
		runMenu.add(targetItem);	
	}
}