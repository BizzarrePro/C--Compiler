package team.weird.texteditor.implement;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import team.weird.texteditor.function.EditMenuItemFunc;
public class EditAction extends AbstractAction implements EditMenuItemFunc{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tab;
	public EditAction(String name, JTabbedPane tab){
		super(name);
		this.tab = tab;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Cut")){
			System.out.println(getCurrentText());
		}
		if(e.getActionCommand().equals("Copy")){
			
		}
		if(e.getActionCommand().equals("Paste")){
	
		}
		
	}
	public String getCurrentText(){
		JTextArea temp = (JTextArea) ((JScrollPane) tab
				.getSelectedComponent()).getViewport().getView();
		return temp.getText();
	}
	
	@Override
	public void cutTextAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copyTextAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pasteTextAction() {
		// TODO Auto-generated method stub
		
	}
}