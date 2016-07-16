package team.weird.texteditor.implement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import team.weird.texteditor.UIConfigure.FrameDesign;
import team.weird.texteditor.UIConfigure.TabbedPanel;
import team.weird.texteditor.attribute.FileAttribute;
import team.weird.texteditor.function.FileMenuItemFunc;
import team.weird.texteditor.util.FileActionUtil;

/**
 * @author Siyuan_Liu
 */
public class FileAction extends AbstractAction implements FileMenuItemFunc {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	protected HashMap<String, FileAttribute> fileMap;
	private JTabbedPane tab;
	private JFrame pan;
	private int id;
	private FileActionUtil util = new FileActionUtil();
	private JLabel line;
	private JLabel column;
	public FileAction(String name, JTabbedPane tab,
			HashMap<String, FileAttribute> fileMap) {
		super(name);
		this.tab = tab;
		this.fileMap = fileMap;
	}
	private static final LinkedList<UndoManager> umList = new LinkedList<UndoManager>();
	public FileAction(String name, JTabbedPane tab,
			HashMap<String, FileAttribute> fileMap, JLabel line, JLabel column) {
		super(name);
		this.tab = tab;
		this.fileMap = fileMap;
		this.line = line;
		this.column = column;
	}
	
	public FileAction(String name, JTabbedPane tab) {
		super(name);
		this.tab = tab;
	}

	public FileAction(String name, JFrame pan) {
		super(name);
		this.pan = pan;
	}
	public static LinkedList<UndoManager> getUndoManager(){
		return umList;
	}
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("New")) {
			newFileAction("Untitle" + id);
		} else if (event.getActionCommand().equals("New Windows")) {
			newWindowsAction();
		} else if (event.getActionCommand().equals("Close Windows")){
			closeWindowsAction();
		} else if (event.getActionCommand().equals("Open")) {
			openFileAction();
		} else if (event.getActionCommand().equals("Save")) {
			saveFileAction();
		} else if (event.getActionCommand().equals("Save as")) {
			saveAsFileAction();
		} else if (event.getActionCommand().equals("Close File")) {
			closeFileAction();
		} else if (event.getActionCommand().equals("Close All File")) {
			closeAllFileAction();
		} else if (event.getActionCommand().equals("Exit")) {
			exitFileAction();
		}

	}

	@Override
	public JTextArea newFileAction(String name) {
		final JTextArea text = new JTextArea();
		if (tab.getSelectedIndex() == -1)
			id = 0;
		JTextArea row = new JTextArea();
		text.setLineWrap(true);
		text.setFont(new Font("Consolas", Font.PLAIN, 15));
		JPanel rowNum = new JPanel();
		rowNum.add(row);
		rowNum.setLayout(new GridLayout());
		
		JScrollPane scrollPane = new JScrollPane(text);
		final DefaultListModel<Integer> model = new DefaultListModel<>();
		final JList<Integer> list = util.initRowList(model);
		scrollPane.setRowHeaderView(list);
		text.setBackground(new Color(40, 40, 40));
		text.setForeground(new Color(248, 248, 242));
		//scrollPane.setOpaque(true);
		scrollPane.setBorder(null);
		
		
		tab.addTab(name, scrollPane);
		final int EIndex = tab.indexOfTab(name);
		tab.setTabComponentAt(EIndex, new TabbedPanel(tab));
		DocumentListener textAction = new FileTextAction(model, text);
		text.getDocument().addDocumentListener(textAction);
		text.addCaretListener(new CaretListener() {
		JTextField stateField = new JTextField();	
			@Override
			public void caretUpdate(CaretEvent e) {
				// TODO Auto-generated method stub
				try {
					int lineNum = text.getLineOfOffset(e.getDot());
					list.setSelectedIndex(lineNum);
					line.setText(""+lineNum);
					column.setText(""+text.getCaretPosition());
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		UndoManager um= new UndoManager();
		umList.add(um);
		Document doc = text.getDocument();
		doc.addUndoableEditListener(new UndoableEditListener() {
		    @Override
		    public void undoableEditHappened(UndoableEditEvent e) {
		        umList.get(tab.getSelectedIndex()).addEdit(e.getEdit());
		    }
		});
		InputMap im = text.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = text.getActionMap();
		
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");
		am.put("Undo", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (umList.get(tab.getSelectedIndex()).canUndo()) {
		            	umList.get(tab.getSelectedIndex()).undo();
		            }
		        } catch (CannotUndoException exp) {
		            exp.printStackTrace();
		        }
		    }
		});
		am.put("Redo", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (umList.get(tab.getSelectedIndex()).canRedo()) {
		            	umList.get(tab.getSelectedIndex()).redo();
		            }
		        } catch (CannotUndoException exp) {
		            exp.printStackTrace();
		        }
		    }
		});
		tab.setSelectedIndex(tab.getComponentCount() - 2);
		id++;
		return text;
	}

	@Override
	public void openFileAction() {
		// TODO Auto-generated method stub
		JFileChooser dialog = new JFileChooser();
		dialog.setDialogTitle("Please choose file");
		int val = dialog.showOpenDialog(null);
		if (JFileChooser.APPROVE_OPTION == val) {
			File filename = dialog.getSelectedFile();
			FileAttribute fa = new FileAttribute(filename.toString(),
					filename.getName());
			fileMap.put(filename.getName(), fa);
			JTextArea temp = newFileAction(filename.getName());
			StringBuffer sb = new StringBuffer();
			int tag = 0;
			try {
				InputStream input = new FileInputStream(filename);
				while ((tag = input.read()) != -1) {
					sb.append((char) tag);
				}
				temp.setText(sb.toString());
				input.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void saveFileAction() {
		// TODO Auto-generated method stub
		int index = tab.getSelectedIndex();
		if (fileMap.containsKey(tab.getTitleAt(index))) {
			File file = new File(fileMap.get(tab.getTitleAt(index)).getPath());
			System.out.println(file);
			FileWriter fw = null;
			file.delete();
			writeToFile(file, fw);
		} else {
			saveAsFileAction();
		}
	}

	@Override
	public void saveAsFileAction() {
		// TODO Auto-generated method stub
		JFileChooser dialog = new JFileChooser();
		dialog.setDialogTitle("Save as");
		int val = dialog.showSaveDialog(null);
		String path = null;
		if (JFileChooser.APPROVE_OPTION == val) {
			path = dialog.getSelectedFile().toString();
			File fr = new File(path);
			FileWriter fw = null;
			if (!fr.exists()) {
				writeToFile(fr, fw);
				putToMap(fr);

			} else {
				fr.delete();
				writeToFile(fr, fw);
				putToMap(fr);
			}
			util.putToRecentDirec(path);
		}
	}

	@Override
	public void closeFileAction() {
		// TODO Auto-generated method stub
		int i = tab.getSelectedIndex();
		if (i != -1)
			tab.remove(i);
	}

	@Override
	public void closeAllFileAction() {
		// TODO Auto-generated method stub
		int i = tab.getSelectedIndex();
		while (i != -1) {
			tab.remove(i);
			i = tab.getSelectedIndex();
		}
		id = 0;
	}

	@Override
	public void newWindowsAction() {
		// TODO Auto-generated method stub
		FrameDesign frame = new FrameDesign();
		frame.initTabbedPane();
	}

	@Override
	public void exitFileAction() {
		// TODO Auto-generated method stub
		pan.dispose();
	}
	
	@Override
	public void closeWindowsAction() {
		// TODO Auto-generated method stub
		pan.dispose();
	}
	
	public void putToMap(File fr) {
		tab.setTitleAt(tab.getSelectedIndex(), fr.getName());
		FileAttribute fa = new FileAttribute(fr.toString(), fr.getName());
		fileMap.put(fr.getName(), fa);
	}
	
	public void writeToFile(File fr, FileWriter fw) {
		try {
			fr.createNewFile();
			fw = new FileWriter(fr);
			JTextArea temp = (JTextArea) ((JScrollPane) tab
					.getSelectedComponent()).getViewport().getView();
			String str = temp.getText();
			fw.write(str);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
