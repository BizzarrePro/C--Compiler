package team.weird.texteditor.implement;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FileTextAction implements DocumentListener{

	public int cnt = 0;
	DefaultListModel<Integer> model;
	JTextArea text;
	public FileTextAction(DefaultListModel<Integer> model, JTextArea text){
		this.model = model;
		this.text = text;
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(model.getSize() < text.getLineCount())
			model.addElement(cnt++);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(model.getSize() > text.getLineCount())
			model.remove(--cnt);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
	}

}
