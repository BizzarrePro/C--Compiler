package team.weird.texteditor.implement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import team.weird.texteditor.function.EditMenuItemFunc;
public class EditAction extends AbstractAction implements EditMenuItemFunc{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tab = null;
	private JFrame frame = null;
	public EditAction(String name, JTabbedPane tab, JFrame frame){
		super(name);
		this.tab = tab;
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Cut")){
			cutTextAction();	
		}
		if(e.getActionCommand().equals("Copy")){
			copyTextAction();
		}
		if(e.getActionCommand().equals("Paste")){
			pasteTextAction();
		}
		if(e.getActionCommand().equals("Undo")){
			 undoTextAction();
		}
		if(e.getActionCommand().equals("Redo")){
			 redoTextAction();
		}
		if(e.getActionCommand().equals("Select All")){
			selectAllAction();
		}
		if(e.getActionCommand().equals("Delete")){
			deleteAction();
		}
		if(e.getActionCommand().equals("Find")){
			findAction();
		}
		if(e.getActionCommand().equals("Replace")){
			replaceAction();
		}
	}
	@Override
	public void cutTextAction() {
		// TODO Auto-generated method stub
		JTextArea temp = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		temp.cut();
	}

	@Override
	public void copyTextAction() {
		// TODO Auto-generated method stub
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		JTextArea temp = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		String text=temp.getSelectedText();
		if(text == null)
			text=temp.getText();
		StringSelection selection = new StringSelection(text);
		clipboard.setContents(selection, null);
	}

	@Override
	public void pasteTextAction() {
		// TODO Auto-generated method stub
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		JTextArea temp = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		DataFlavor flavor = DataFlavor.stringFlavor;
		if(clipboard.isDataFlavorAvailable(flavor)){
			try{
				String text = (String) clipboard.getData(flavor);
				temp.replaceSelection(text);
			}
			catch (UnsupportedFlavorException e) {
				// TODO: handle exception
				JOptionPane.showInputDialog(this, e);
			}
			catch (IOException e) {
				// TODO: handle exception
				JOptionPane.showInputDialog(this, e);
			}	
		}
	}

	@Override
	public void undoTextAction() {
		// TODO Auto-generated method stub
		JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		LinkedList<UndoManager> umList = FileAction.getUndoManager();
		if(umList.get(tab.getSelectedIndex()) != null){
			text.getDocument().addUndoableEditListener(umList.get(tab.getSelectedIndex()));
			try{
				if(umList.get(tab.getSelectedIndex()).canUndo()){
					umList.get(tab.getSelectedIndex()).undo();
				}
			}
			catch(CannotUndoException e){
				e.printStackTrace();
			}
		}
	}
	@Override
	public void redoTextAction() {
		// TODO Auto-generated method stub
		JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		LinkedList<UndoManager> umList = FileAction.getUndoManager();
		if(umList.get(tab.getSelectedIndex()) != null){
			text.getDocument().addUndoableEditListener(umList.get(tab.getSelectedIndex()));
			try{
				if(umList.get(tab.getSelectedIndex()).canRedo()){
					umList.get(tab.getSelectedIndex()).redo();
				}
			}
			catch(CannotUndoException e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void selectAllAction() {
		// TODO Auto-generated method stub
		JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		text.selectAll();
	}

	@Override
	public void deleteAction() {
		// TODO Auto-generated method stub
		JTextArea text = (JTextArea) ((JScrollPane) tab.getSelectedComponent()).getViewport().getView();
		StringBuffer tmp = new StringBuffer(text.getText());
		int start = text.getSelectionStart();
		int len = text.getSelectedText().length();
		tmp.delete(start, start+len);
		text.setText(tmp.toString());
	}

	@Override
	public void findAction() {
		// TODO Auto-generated method stub
		Find find =new Find(frame, tab);
		find.setVisible(true);
		
	}

	

	@Override
	public void replaceAction() {
		// TODO Auto-generated method stub
		Find find =new Find(frame, tab);
		find.setVisible(true);
	}
}