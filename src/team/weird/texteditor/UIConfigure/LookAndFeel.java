package team.weird.texteditor.UIConfigure;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import team.weird.texteditor.function.FileMenuItemFunc;
import team.weird.texteditor.implement.EditAction;
import team.weird.texteditor.implement.FileAction;
import team.weird.texteditor.menu.EditMenuItem;
import team.weird.texteditor.menu.FileMenuItem;

/**
 * @author Siyuan_Liu
 * @version 1.0 
 * @look Nimbus
 */
public class LookAndFeel extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			    if ("Nimbus".equals(info.getName())) {
			      UIManager.setLookAndFeel(info.getClassName());
			      break;
			    }
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initFrame() {
		FrameDesign frame = new FrameDesign();
		frame.initTabbedPane();
	}

}