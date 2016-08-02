package team.weird.compiler.editor.configure;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * @author Siyuan_Liu
 * @version 1.0 
 * @skin Nimbus
 */
public class LookAndFeel extends JFrame {
	/**
	 * @version 1.0
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
	/**
	 * @see team.weird.texteditor.UIConfigure.FrameDesign#get
	 */
	public void initFrame() {
		FrameDesign frame = new FrameDesign();
		frame.initTabbedPane();
	}

}