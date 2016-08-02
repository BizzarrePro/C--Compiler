package team.weird.compiler.editor.implement;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import team.weird.compiler.editor.function.RunMenuItemFunc;


public class RunAction extends AbstractAction implements RunMenuItemFunc{
	private JTabbedPane tab;
	private JFrame frame;
	public RunAction(String name, JTabbedPane tab){
		super(name);
		tab = this.tab;
	}
	public RunAction(String name, JTabbedPane tab, JFrame frame) {
		// TODO Auto-generated constructor stub
		super(name);
		this.tab = tab;
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		switch(command){
		case "run":	
			runCode();
			break;
		case "syntax":
			showLexerAnalysis();
		case "lexer":
			showParserAnalysis();
		case "parser":
			showIntermediateCode();
		case "semantic":
			showIntermediateCode();
		case "target":
			showAsmCode();
		}
	}

	@Override
	public void runCode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showLexerAnalysis() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showParserAnalysis() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showIntermediateCode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAsmCode() {
		// TODO Auto-generated method stub
		
	}

}
