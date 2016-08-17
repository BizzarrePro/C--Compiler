package team.weird.compiler.editor.implement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;

import team.weird.compiler.editor.function.HelpMenuItemFunc;

public class HelpAction extends AbstractAction implements HelpMenuItemFunc  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tab;
	private JFrame frame;

	public HelpAction(String name, JTabbedPane tab) {
		super(name);
		tab = this.tab;
	}

	public HelpAction(String name, JTabbedPane tab, JFrame frame) {
		// TODO Auto-generated constructor stub
		super(name);
		this.tab = tab;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		switch(command){
		case "documentation":
			documentation();
			break;
		case "aboutText":
			aboutWeridText();
			break;
		}
	}

	@Override
	public void documentation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aboutWeridText() {
		// TODO Auto-generated method stub
		AboutText text = new AboutText();
	}

	class AboutText extends JFrame{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTextArea text; 
		public AboutText(){
			setResizable(false);
			setAlwaysOnTop(true);
			text = new JTextArea();
			text.setEditable(false);
			text.setForeground(new Color(242, 242, 242));
			text.setBackground(new Color(40, 40, 40));
			text.setBorder(BorderFactory.createEmptyBorder());
			text.setFont(new Font("Consolas", Font.PLAIN, 15));
			add(text);
			String line = "\r\n            C- Compiler & Weird Editor\r\n\r\n" +
					"          Code by Siyuan Liu & Qian Yang \r\n" +
					"    Copyright (c) Weird Compiler Stable Channel";
			text.setText(line);
			setSize(new Dimension(400, 150));
			setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			setVisible(true);
		}
	}
}
