package team.weird.texteditor.implement;

import java.awt.Color;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;

import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import team.weird.texteditor.UIConfigure.TabbedPanel;
import team.weird.texteditor.function.FileMenuItemFunc;

class FileAttribute {
	public String path;
	public String name;

	public FileAttribute(String path, String name) {
		this.path = path;
		this.name = name;
	}
}

public class FileAction extends AbstractAction implements FileMenuItemFunc{
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private Map<String, FileAttribute> fileMap = new HashMap<String, FileAttribute>();
	private JTabbedPane tab;
	private int id;

	public FileAction(String name, JTabbedPane tab) {
		super(name);
		this.tab = tab;
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("New")) {
			newFileAction("Unname" + id);
		} else if (event.getActionCommand().equals("Open")) {
			openFileAction();
		} else if (event.getActionCommand().equals("Save")) {
			saveFileAction();
		} else if (event.getActionCommand().equals("Save as")) {
			saveAsFileAction();
		}

	}
	public void writeToFile(File fr, FileWriter fw) {
		try {
			fr.createNewFile();
			fw = new FileWriter(fr);
			JTextArea temp = (JTextArea) ((JScrollPane) tab
					.getSelectedComponent()).getViewport().getView();
			String str = temp.getText();
			System.out.println(str);
			System.out.println("pupu");
			fw.write(str);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public JTextArea newFileAction(String name) {
		JTextArea text = new JTextArea();
		text.setLineWrap(true);
		text.setFont(new Font("Consolas", Font.PLAIN, 15));
		JScrollPane scrollPane = new JScrollPane(text);
		text.setBackground(new Color(40, 40, 40));
		text.setForeground(new Color(248, 248, 242));
		tab.addTab(name, scrollPane);
		final int EIndex = tab.indexOfTab(name);
		tab.setTabComponentAt(EIndex, new TabbedPanel(tab));
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
			System.out.println(fileMap.size());
			System.out.println(filename.getName().hashCode());
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
		System.out.println(fileMap.containsKey(tab.getTitleAt(index)));
		System.out.println(tab.getTitleAt(index).hashCode());
		System.out.println(fileMap.keySet().size());
		if (fileMap.containsKey(tab.getTitleAt(index))) {
			File file = new File(fileMap.get(tab.getTitleAt(index)).path);
			System.out.println(file);
			FileWriter fw = null;
			file.delete();
			writeToFile(file, fw);
		}
		else{
			System.out.println("Error");
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
		}
	}
	public void putToMap(File fr){
		tab.setTitleAt(tab.getSelectedIndex(), fr.getName());
		FileAttribute fa = new FileAttribute(fr.toString(),
				fr.getName());
		fileMap.put(fr.getName(), fa);
	}

}
