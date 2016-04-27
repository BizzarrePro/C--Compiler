package team.weird.texteditor.implement;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
public class EditAction extends AbstractAction{
	public EditAction(String name){
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Cut")){
			
		}
		
	}
}