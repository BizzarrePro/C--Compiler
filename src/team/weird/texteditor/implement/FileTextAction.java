package team.weird.texteditor.implement;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/**
 * @author Siyuan_Liu
 */
public class FileTextAction implements DocumentListener{

	public int cnt = 0;
	private DefaultListModel<Integer> model;
	private JTextArea text;
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
