package team.weird.texteditor.UIConfigure;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import team.weird.texteditor.menu.EditMenuItem;
import team.weird.texteditor.menu.FileMenuItem;
/**
 * @author Siyuan_Liu 
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initTabbedPane() {
		initMenuItem();
		add(contentPane);
		setVisible(true);
	}
	public void initMenuItem() {
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		FileMenuItem fileMenu = new FileMenuItem(bar, contentPane);
		EditMenuItem editMenu = new EditMenuItem(bar, contentPane);
		fileMenu.initFileMenuItem();
		editMenu.initEditMenuItem();
	}
}
