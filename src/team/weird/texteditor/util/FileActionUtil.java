package team.weird.texteditor.util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

public class FileActionUtil {
	public FileActionUtil(){}
	public void putToRecentDirec(String file){
		File dir = new File("./recent");
		File recentFile = new File("./recent/recentFile.txt");
		FileWriter fw = null;
		try{
			if(!dir.exists()){
				dir.mkdir();
				String attr = "attrib +H "+dir.getAbsolutePath();
				Process pro = Runtime.getRuntime().exec(attr);
			}
			try{
				fw = new FileWriter(recentFile);
				fw.append(file);
				fw.append("\r\n");
				fw.flush();
				fw.close();
			}
			catch(FileNotFoundException e ){
				e.printStackTrace();
			}
			
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public JList<Integer> initRowList(DefaultListModel<Integer> model){
		final JList<Integer> rowList = new JList<Integer>(model);
		rowList.setFixedCellWidth(30);
		rowList.setBackground(new Color(80, 80, 80));
		rowList.setFont(new Font("Consolas", Font.PLAIN, 15));
		rowList.setForeground(new Color(248, 248, 242));
		rowList.setAlignmentX(5);
		DefaultListCellRenderer renderer =  (DefaultListCellRenderer)rowList.getCellRenderer();  
		renderer.setHorizontalAlignment(JLabel.CENTER);
		return rowList;
	}
}
