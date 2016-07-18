package team.weird.texteditor.UIConfigure;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import team.weird.texteditor.menu.EditMenuItem;
import team.weird.texteditor.menu.FileMenuItem;
import team.weird.texteditor.menu.SelectMenuItem;
import team.weird.texteditor.menu.ToolMenuItem;
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
		fileMenu.initFileMenuItem();
		editMenu.initEditMenuItem();
		selectMenu.initSelectionMenuItem();
		toolMenu.initToolMenuItem();
	}
}
