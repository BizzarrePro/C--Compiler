package team.weird.texteditor.UIConfigure;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

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