package team.weird.compiler.editor.implement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import team.weird.compiler.editor.function.ToolMenuItemFunc;

public class ToolsAction extends AbstractAction implements ToolMenuItemFunc {
	private JTabbedPane tab;
	private JFrame frame;
	private JTextField pathField;
	private JTextField commentField;
	private Thread t ;

	public ToolsAction(String name, JTabbedPane tab) {
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
		switch (event) {
		case "Translation":
			t = new Thread(new Runnable() {
				public void run() {
					translation();
				}
			});
			t.start();
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
		SwingUtilities.invokeLater(new Runnable() {
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
		Commit commitToGithub = new Commit();
	}

	class Browser extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Browser(String url) {
			JWebBrowser web = new JWebBrowser();
			web.navigate(url);
			web.setButtonBarVisible(false);
			web.setLocationBarVisible(false);
			add(web);
			setSize(new Dimension(400, 600));
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationByPlatform(true);
			setVisible(true);
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					dispose();
				}
			});
		}
	}

	class Commit extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JPanel contentPane;

		public Commit() {
			setSize(new Dimension(300, 150));
			setResizable(false);
			setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			Point location = frame.getLocation();
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
			JPanel panel = new JPanel();
			contentPane.add(panel, BorderLayout.NORTH);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
			gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
			gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
			gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
			panel.setLayout(gbl_panel);

			JLabel nativeResponsibility = new JLabel("Native Path: ");
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.WEST;
			gbc_label.insets = new Insets(0, 0, 5, 5);
			gbc_label.gridx = 0;
			gbc_label.gridy = 1;
			panel.add(nativeResponsibility, gbc_label);

			pathField = new JTextField();
			GridBagConstraints gbc_pathTextField = new GridBagConstraints();
			gbc_pathTextField.gridwidth = 3;
			gbc_pathTextField.insets = new Insets(0, 0, 5, 0);
			gbc_pathTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_pathTextField.gridx = 1;
			gbc_pathTextField.gridy = 1;
			reloadPath();
			panel.add(pathField, gbc_pathTextField);
			pathField.setColumns(10);

			JLabel comment = new JLabel("Comment: ");
			GridBagConstraints gbc_label1 = new GridBagConstraints();
			gbc_label1.anchor = GridBagConstraints.WEST;
			gbc_label1.insets = new Insets(0, 0, 5, 5);
			gbc_label1.gridx = 0;
			gbc_label1.gridy = 2;
			panel.add(comment, gbc_label1);

			commentField = new JTextField();
			GridBagConstraints gbc_commentTextField = new GridBagConstraints();
			gbc_commentTextField.gridwidth = 3;
			gbc_commentTextField.insets = new Insets(0, 0, 5, 0);
			gbc_commentTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_commentTextField.gridx = 1;
			gbc_commentTextField.gridy = 2;
			panel.add(commentField, gbc_commentTextField);
			commentField.setColumns(10);

			JPanel panel_1 = new JPanel();
			GridBagConstraints gbc_panel_1 = new GridBagConstraints();
			gbc_panel_1.gridwidth = 4;
			gbc_panel_1.fill = GridBagConstraints.BOTH;
			gbc_panel_1.gridx = 0;
			gbc_panel_1.gridy = 3;
			panel.add(panel_1, gbc_panel_1);
			JButton commitNativeButton = new JButton("Commit");
			JButton pushGithubButton = new JButton("Push");
			panel_1.add(commitNativeButton);
			panel_1.add(pushGithubButton);
			commitNativeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int type = recordResponsibilityAddress();
					doCommitment(e, type);
				}
			});
			pushGithubButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int type = recordResponsibilityAddress();
					doPushUp(e, type);
				}
			});
			setLocationByPlatform(true);
			setVisible(true);
		}

		private void reloadPath() {
			File content = new File("./recent/record.txt");
			if(content.exists()){
				BufferedReader br = null;
				try{
					br = new BufferedReader(new FileReader(content));
					String path = br.readLine();
					if(path != null)
						pathField.setText(path);
				} catch(IOException ioe) {
					ioe.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			
		}

		private void doCommitment(ActionEvent e, int type) {
			pushOrCommit("", type);
		}

		private void doPushUp(ActionEvent e, int type) {
			pushOrCommit(" & git push origin master", type);
		}
		private void pushOrCommit(String command, int type){
			try {
				Runtime rt = Runtime.getRuntime();
				System.out.println(pathField.getText());
				StringBuffer buffer = new StringBuffer("");
				reloadPath();
				switch (type) {
				case 1:
					Process pr = rt.exec("cmd /c cd " + pathField.getText()
							+ " & dir & git add --all & git commit -m \""+commentField.getText()+"\""+command);
					BufferedReader input = new BufferedReader(
							new InputStreamReader(pr.getInputStream(), "GBK"));
					String line = null;
					while ((line = input.readLine()) != null)
						buffer.append("" + line + "\r\n");
					buffer.append("You have commit successfully~~ \r\n");
					File recordDir = new File("./recent");
					File record = new File("./recent/record.txt");
					BufferedWriter bw = null;
					if(!recordDir.exists()){
						recordDir.mkdir();
						String attr = "attrib +H " + recordDir.getAbsolutePath();
						Process pro = Runtime.getRuntime().exec(attr);
						pr.waitFor();
					} else {
						try{
							bw = new BufferedWriter(new FileWriter(record));
							bw.write(pathField.getText());
						} catch(IOException ioe){
							ioe.printStackTrace();
						} finally{
							bw.close();
						}
						
					}
					break;
				case 2:
					buffer.append("This directory is not git responsibility!");
					break;
				case 3:
					buffer.append("This Path is not a directory!");
					break;
				case 4:
					buffer.append("Text in Path is not a valid path!");
					break;
				default:
					buffer.append("This directory is not git responsibility!");
					break;
				}
				Result result = new Result(buffer.toString());
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		private int recordResponsibilityAddress() {
			String path = pathField.getText();
			if (path != null) {
				File directory = new File(path);
				if (directory.isDirectory()) {
					String[] list = directory.list();
					if (list.length != 0) {
						for (int i = 0; i < list.length; i++)
							if (list[i].equals(".git"))
								return 1;
					} else {
						return 2;
					}
				} else {
					return 3;
				}
			} else {
				return 4;
			}
			return 0;
		}
	}

	class Result extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextArea text;

		public Result(String result) {
			text = new JTextArea();
			JScrollPane scroll = new JScrollPane(text);
			text.setLineWrap(true);
			text.setFont(new Font("Consolas", Font.PLAIN, 14));
			setSize(new Dimension(800, 150));
			setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			text.setText(result);
			text.setEditable(false);
			add(scroll);
			setVisible(true);
		}
	}
}
