package team.weird.texteditor.implement;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import team.weird.texteditor.attribute.FileAttribute;

public class FileOpenRecAction extends FileAction{
	private String path = null;
	public FileOpenRecAction(String path, JTabbedPane tab, HashMap<String, FileAttribute> fileMap) {
		super(path, tab, fileMap);
		this.path = path;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		File filename = new File(path);
		if(!filename.exists()){		
			JOptionPane.showMessageDialog(null, "File probably has been deleted or moved", "Warning",JOptionPane.WARNING_MESSAGE); 
			return;
		}
		FileAttribute fa = new FileAttribute(filename.toString(),
				filename.getName());
		System.out.println(fa+" "+filename.getName());
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
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}
