package team.weird.texteditor.implement;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import team.weird.texteditor.function.ToolMenuItemFunc;

public class ToolsAction extends AbstractAction implements ToolMenuItemFunc{
	private JTabbedPane tab;
	private JFrame frame;
	public ToolsAction(String name, JTabbedPane tab){
		super(name);
		tab = this.tab;
	}
	public ToolsAction(String name, JTabbedPane tab, JFrame frame) {
		// TODO Auto-generated constructor stub
		super(name);
		this.tab = tab;
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String event = e.getActionCommand();
		switch(event){
		case "Translation":
			translation();
			break;
		case "Commitment":
			commitToGithub();
			break;
		}
	}
	@Override
	public void translation() {
		// TODO Auto-generated method stub
		final String url = "http://translate.google.cn/";
    	UIUtils.setPreferredLookAndFeel();
    	NativeInterface.open();
    	SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Browser fm = new Browser(url);
			}
    	});
    	NativeInterface.runEventPump();
	}
	
	@Override
	public void commitToGithub() {
		// TODO Auto-generated method stub
		
	}
	
	class Browser extends JFrame{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JPanel panel;
		private String url; 
		public Browser(String url){
			this.url = url;
			final JWebBrowser web = new JWebBrowser();
			web.navigate(url);
			web.setButtonBarVisible(false);
			web.setLocationBarVisible(false);
	    	add(web);
	    	setSize(new Dimension(800, 1200));
	    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	setLocationByPlatform(true);
	    	setVisible(true);
		}
	}
}
