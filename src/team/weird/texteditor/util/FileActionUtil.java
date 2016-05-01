package team.weird.texteditor.util;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
/**
 * @author Siyuan_Liu
 */
public class FileActionUtil {
	public FileActionUtil() {
	}
	
	public ArrayList<String> ExtractRecentPath(String filename){
		ArrayList<String> container = new ArrayList<String>();
		FileReader fr = null;
		BufferedReader br = null;
		try{
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			String path = br.readLine();
			while(path != null){
				container.add(path);
				path = br.readLine();
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				fr.close();
				br.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		return container;
		
	}
	public void putToRecentDirec(String file) {
		File dir = new File("./recent");
		File recentFile = new File("./recent/recentFile.txt");
		FileWriter fw = null;
		try {
			if (!dir.exists()) {
				dir.mkdir();
				String attr = "attrib +H " + dir.getAbsolutePath();
				Process pro = Runtime.getRuntime().exec(attr);
			}
			try {
				fw = new FileWriter(recentFile, true);
				fw.append(file);
				fw.append("\r\n");
				fw.flush();
				fw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if (getTotalLines(recentFile) > 100) {
			
				File garbageFile = new File("./recent/recentOldFile.txt");
				recentFile.renameTo(garbageFile);
				File newRecentFile = new File("./recent/recentFile.txt");
				reverseOrderRead(garbageFile, newRecentFile);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void reverseOrderRead(File readFile, File writeFile) {
		// FileReader fr = null;
		// BufferedReader br = null;
		FileWriter fw = null;
		RandomAccessFile raf = null;
		String str = null;
		int totalLine = 0;
		int tag = -1;
		Stack<String> pathStack = new Stack<String>();
		try {
			fw = new FileWriter(writeFile);
			raf = new RandomAccessFile(readFile, "r");
			long length = raf.length();
			long start = raf.getFilePointer();
			long nextPoint = start + length - 1;
			raf.seek(nextPoint);
			while (nextPoint > start && totalLine < 5) {
				tag = raf.read();
				if (tag == '\n' || tag == '\r') {
					str = raf.readLine();
					if (str != null) {
						//  for debugging
						//	System.out.print(str);
						pathStack.push(str + "\n\r");
						totalLine ++;
					}
				}
				nextPoint --;;
				raf.seek(nextPoint);
			}
			while(!pathStack.isEmpty())
				fw.write(pathStack.pop());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				raf.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private int getTotalLines(File fileName) {
		FileReader fr = null;
		try {
			fr = new FileReader(fileName);
			BufferedReader lnr = new BufferedReader(fr);
			String str = lnr.readLine();
			int count = 0;
			while (str != null) {
				count++;
				str = lnr.readLine();
			}
			fr.close();
			lnr.close();
			return count;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public JList<Integer> initRowList(DefaultListModel<Integer> model) {
		final JList<Integer> rowList = new JList<Integer>(model);
		rowList.setFixedCellWidth(30);
		rowList.setBackground(new Color(80, 80, 80));
		rowList.setFont(new Font("Consolas", Font.PLAIN, 15));
		rowList.setForeground(new Color(248, 248, 242));
		rowList.setAlignmentX(5);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) rowList
				.getCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		return rowList;
	}
}
