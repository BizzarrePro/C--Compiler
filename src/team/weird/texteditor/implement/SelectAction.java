package team.weird.texteditor.implement;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTabbedPane;

import team.weird.texteditor.function.SelectionMenuItemFunc;

public class SelectAction extends AbstractAction implements SelectionMenuItemFunc{
	
	private JTabbedPane tab ;
	public SelectAction(String name, JTabbedPane tab){
		super(name);
		tab = this.tab;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand() == "split Lines")
			splitIntoLinesAction();
	}

	@Override
	public void splitIntoLinesAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPreviousLineAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNextLineAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void singleSelectionAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectAllAction() {
		// TODO Auto-generated method stub
		
	}

}
