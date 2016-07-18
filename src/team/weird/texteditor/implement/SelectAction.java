package team.weird.texteditor.implement;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import team.weird.texteditor.function.SelectionMenuItemFunc;

public class SelectAction extends AbstractAction implements SelectionMenuItemFunc{
	
	private JTabbedPane tab;
	private JFrame frame;
	public SelectAction(String name, JTabbedPane tab){
		super(name);
		tab = this.tab;
	}
	public SelectAction(String name, JTabbedPane tab, JFrame frame) {
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
		case "All":
			selectAllAction();
			break;
		case "Line":
			expandingSelectionToLine();
			break;
		case "Word":
			expandingSelectionToWord();
			break;
		case "Scope":
			expandingSelectionToScope();
			break;
		case "Moveup":
			moveUpSelectionLines();
			break;
		default:
			moveDownSelectionLines();
		}
		
	}
//	@Override
//	public void invertSelectionAction() {
//		// TODO Auto-generated method stub
//		JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
//		int start = text.getSelectionStart();
//		int end = text.getSelectionEnd();
//		text.select(0, start);
//		text.select(end, text.getText().length());
//	}
	@Override
	public void selectAllAction() {
		// TODO Auto-generated method stub
		JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		text.selectAll();
	}
	@Override
	public void expandingSelectionToLine() {
		// TODO Auto-generated method stub
		JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		int start = text.getCaretPosition();
		int end = start;
		while(start > 0 && (text.getText().charAt(--start))!='\n');
		while(end < text.getText().length() && (text.getText().charAt(end++))!='\n');
		text.select(start == 0 ? start : start+1, end == text.getText().length()-1 ? end : end - 1);
	}
	@Override
	public void expandingSelectionToWord() {
		// TODO Auto-generated method stub
		JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		int start = text.getCaretPosition();
		int end = start;
		while(start > 0 && (Character.isDigit(text.getText().charAt(--start)) || Character.isLetter(text.getText().charAt(start))));
		while(end < text.getText().length() && (Character.isDigit(text.getText().charAt(end++)) || Character.isLetter(text.getText().charAt(end))));
		text.select(start == 0 ? start : start+1, end == text.getText().length()-1 ? end : end - 1);
	}
	@Override
	public void expandingSelectionToScope() {
		// TODO Auto-generated method stub
		JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		int start = text.getCaretPosition();
		int end = start;
		boolean hasScope = false;
		while(start > 0 && ((text.getText().charAt(start--))!='{') || hasScope){
			
			if(text.getText().charAt(start+1) == '}')
				hasScope = true;
			if(text.getText().charAt(start+1) == '{')
				hasScope = false;
		}
		hasScope = false;
		while(end < text.getText().length() && ((text.getText().charAt(end++))!='}') || hasScope){
			if(text.getText().charAt(end-1) == '{')
				hasScope = true;
			if(text.getText().charAt(end-1) == '}')
				hasScope = false;
		}
		text.select(start+1, end);
	}
	@Override
	public void moveUpSelectionLines() {
		// TODO Auto-generated method stub
		JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		if(text != null){
			int start = text.getSelectionStart();
			int end = text.getSelectionEnd();
			while(start > 0 && text.getText().charAt(start)!='\n')
				start--;
			while(end < text.getText().length()-1 && text.getText().charAt(end)!='\n')
				end++;
			text.select(start, end);
			String sentences = text.getSelectedText();
			text.cut();
			start--;
			while(start > 0 && text.getText().charAt(start)!='\n')
				start--;
			text.insert(sentences, start);	
		}
	}
	@Override
	public void moveDownSelectionLines() {
		// TODO Auto-generated method stub
		JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		if(text != null){
			int start = text.getSelectionStart();
			int end = text.getSelectionEnd();
			while(start > 0 && text.getText().charAt(start)!='\n')
				start--;
			while(end < text.getText().length()-1 && text.getText().charAt(end)!='\n')
				end++;
			text.select(start, end);
			String sentences = text.getSelectedText();
			text.cut();
			end++;
			while(end < text.getText().length()-1 && text.getText().charAt(end)!='\n')
				end++;
			text.insert(sentences, end);	
		}
	}


}
