package team.weird.compiler.editor.configure;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import team.weird.compiler.editor.implement.FileAction;
import team.weird.compiler.editor.menu.EditMenuItem;
import team.weird.compiler.editor.menu.FileMenuItem;
import team.weird.compiler.editor.menu.HelpMenuItem;
import team.weird.compiler.editor.menu.RunMenuItem;
import team.weird.compiler.editor.menu.SelectMenuItem;
import team.weird.compiler.editor.menu.ToolMenuItem;
/**
 * @filename FrameDesign.java
 * @author Siyuan_Liu 
 * @description Configure core frame and add all of menu to menu bar. 
 * @version 1.0
 */
public class FrameDesign extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTabbedPane contentPane = new JTabbedPane();
	protected static final int DEAFAULT_WIDTH = 800;
	protected static final int DEAFAULT_HEIGHT = 500;

	public FrameDesign() {
		setSize(DEAFAULT_WIDTH, DEAFAULT_HEIGHT);
		setTitle("Weird Text");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void initTabbedPane() {
		initMenuItem();
		add(contentPane);
		setVisible(true);
	}
	
/**
 * @description Initialize menu.
 */
	public void initMenuItem() {
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		FileMenuItem fileMenu = new FileMenuItem(bar, contentPane, this);
		EditMenuItem editMenu = new EditMenuItem(bar, contentPane, this);
		SelectMenuItem selectMenu = new SelectMenuItem(bar, contentPane, this);
		ToolMenuItem toolMenu = new ToolMenuItem(bar, contentPane, this);
		RunMenuItem runMenu = new RunMenuItem(bar, contentPane, this);
		HelpMenuItem helpMenu = new HelpMenuItem(bar, contentPane, this);
		fileMenu.initFileMenuItem();
		editMenu.initEditMenuItem();
		selectMenu.initSelectionMenuItem();
		toolMenu.initToolMenuItem();
		runMenu.initRunMenuItem();
		helpMenu.initHelpMenuItem();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				if(contentPane.getTabCount() > 0){
					File dir = new File(".\\storage");
					FileWriter fw = null;
					if(!dir.exists()){
						dir.mkdir();
						String attr = "attrib +H " + dir.getAbsolutePath();
						try {
							Process pro = Runtime.getRuntime().exec(attr);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					for(int i = 0; i < contentPane.getTabCount(); i++){
						JTextArea text = (JTextArea) ((JScrollPane) contentPane.getComponentAt(i)).getViewport().getView();
						File file = new File(".\\storage\\"+i+".txt");
						try {
							file.createNewFile();
						} catch (FileAlreadyExistsException e2) {
							e2.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						try{
							fw = new FileWriter(file);
							fw.write(contentPane.getTitleAt(i));
							fw.write("\r\n");
							String content = text.getText();
							fw.write(content);
						} catch(IOException ioe) {
							ioe.printStackTrace();
						}
						finally {
							try {
								fw.flush();
								fw.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
				System.exit(0);
			}
		});
	}
}
