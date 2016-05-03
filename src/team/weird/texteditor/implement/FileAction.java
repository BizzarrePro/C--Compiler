package team.weird.texteditor.implement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractAction;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import team.weird.texteditor.UIConfigure.FrameDesign;
import team.weird.texteditor.UIConfigure.TabbedPanel;
import team.weird.texteditor.attribute.FileAttribute;
import team.weird.texteditor.function.FileMenuItemFunc;
import team.weird.texteditor.util.FileActionUtil;
/**
 * @author Siyuan_Liu
 */
public class FileAction extends AbstractAction implements FileMenuItemFunc{
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	protected HashMap<String, FileAttribute> fileMap;
	private JTabbedPane tab;
	private int id;
	private FileActionUtil util = new FileActionUtil();
	
	public FileAction(String name, JTabbedPane tab, HashMap<String, FileAttribute> fileMap) {
		super(name);
		this.tab = tab;
		this.fileMap = fileMap;
	}
	
	public FileAction(String name){
		super(name);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("New")) {
			newFileAction("Untitle" + id);
		} else if (event.getActionCommand().equals("New Windows")){
			newWindowsAction();
		} else if (event.getActionCommand().equals("Open")) {
			openFileAction();
		} else if (event.getActionCommand().equals("Save")) {
			saveFileAction();
		} else if (event.getActionCommand().equals("Save as")) {
			saveAsFileAction();
		} else if (event.getActionCommand().equals("Exit")) {
			exitFileAction();
		}

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
	@Override
	public JTextArea newFileAction(String name) {
		final JTextArea text = new JTextArea();
		JTextArea row = new JTextArea();
		text.setLineWrap(true);
		text.setFont(new Font("Consolas", Font.PLAIN, 15));
		JPanel rowNum = new JPanel();
		rowNum.add(row);
		rowNum.setLayout(new GridLayout());
		JScrollPane scrollPane = new JScrollPane(text);
		final DefaultListModel<Integer> model = new DefaultListModel<>();
		scrollPane.setRowHeaderView(util.initRowList(model));
		text.setBackground(new Color(40, 40, 40));
		text.setForeground(new Color(248, 248, 242));
		tab.addTab(name, scrollPane);
		final int EIndex = tab.indexOfTab(name);
		tab.setTabComponentAt(EIndex, new TabbedPanel(tab));
		DocumentListener textAction = new FileTextAction(model, text);
		text.getDocument().addDocumentListener(textAction);
		tab.setSelectedIndex(id);
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
		}
		else{
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

	public void putToMap(File fr){
		tab.setTitleAt(tab.getSelectedIndex(), fr.getName());
		FileAttribute fa = new FileAttribute(fr.toString(),
				fr.getName());
		fileMap.put(fr.getName(), fa);
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
		System.exit(0);
	}

}
